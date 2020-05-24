package com.example.funapp.ui.miseventos.crear_editar_evento;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.funapp.R;
import com.example.funapp.models.Evento;
import com.example.funapp.models.Tematica;
import com.example.funapp.models.Ubicacion;
import com.example.funapp.models.Usuario;
import com.example.funapp.models.UsuarioResponsable;
import com.example.funapp.ui.miseventos.MisEventosFragment;
import com.example.funapp.util.Protocolo;
import com.google.android.gms.maps.model.LatLng;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

public class CrearEditarEventoFragment extends Fragment implements Protocolo {

    private CrearEditarEventoViewModel crearEditarEventoViewModel;
    private List<Tematica> tematicas = new ArrayList<>();
    private Usuario usuario;
    private Evento evento;
    private Integer accion;
    private Integer estadoSesion;
    private CrearEditarEventoActivity parentActivity;

    private static final String CERO = "0";
    private static final String BARRA = "-";
    private static final String DOS_PUNTOS = ":";

    //Calendario para obtener fecha & hora
    public final Calendar c = Calendar.getInstance();

    //Variables para obtener la fecha y hora
    final int mes = c.get(Calendar.MONTH);
    final int dia = c.get(Calendar.DAY_OF_MONTH);
    final int anio = c.get(Calendar.YEAR);
    final int hora = c.get(Calendar.HOUR_OF_DAY);
    final int minuto = c.get(Calendar.MINUTE);

    //Widgets
    EditText crearEventoFecha;
    EditText crearEventoHoraInicio;
    EditText crearEventoHoraFin;
    ProgressBar loadingProgressBar;
    ImageButton ibObtenerFecha;

    public CrearEditarEventoFragment() {
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        parentActivity = (CrearEditarEventoActivity) getActivity();
        View view = inflater.inflate(R.layout.fragment_crear_editar_evento, container, false);

        crearEditarEventoViewModel = ViewModelProviders.of(this).get(CrearEditarEventoViewModel.class);

        this.usuario = ((CrearEditarEventoActivity) getActivity()).getUsuario();
        this.accion = ((CrearEditarEventoActivity) getActivity()).getAccion();

        final EditText crearEventoNombre = view.findViewById(R.id.etCrearEventoNombre);
        final EditText crearEventoDespricion = view.findViewById(R.id.etCrearDescripcion);
        crearEventoFecha = view.findViewById(R.id.etCrearEventoFecha);
        crearEventoHoraInicio = view.findViewById(R.id.et_crearEventoHoraInicio);
        crearEventoHoraFin = view.findViewById(R.id.et_CrearEventoHoraFin);
        final Spinner tematica = view.findViewById(R.id.spinnerTematicas);
        final Switch codigoQR = view.findViewById(R.id.swCrearEventoCodigoQR);
        final ImageButton fechaButton = view.findViewById(R.id.ib_obtener_fecha);
        final ImageButton horaInicioButton = view.findViewById(R.id.ib_obtener_horaInicio);
        final ImageButton horaFinButton = view.findViewById(R.id.ib_obtener_horaFin);
        final Button confirmarButton = view.findViewById(R.id.bConfirmarCrear);


        if (accion == ACTUALIZAR_EVENTO) {
            this.evento = ((CrearEditarEventoActivity) getActivity()).getEvento();
            crearEventoNombre.setText(evento.getNombre());
            crearEventoDespricion.setText(evento.getDescripcion());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            String fechaEvento = evento.getFecha_evento_LocalDate().format(formatter);
            crearEventoFecha.setText(fechaEvento);

            int minutos = evento.getHora_inicio().getMinute();
            if (minutos < 10)
                crearEventoHoraInicio.setText(evento.getHora_inicio().getHour() + ":0" + evento.getHora_inicio().getMinute());
            else
                crearEventoHoraInicio.setText(evento.getHora_inicio().getHour() + ":" + evento.getHora_inicio().getMinute());

            minutos = evento.getHora_fin().getMinute();
            if (minutos < 10)
                crearEventoHoraFin.setText(evento.getHora_fin().getHour() + ":0" + evento.getHora_fin().getMinute());
            else
                crearEventoHoraFin.setText(evento.getHora_fin().getHour() + ":" + evento.getHora_fin().getMinute());

            tematica.setSelection(evento.getTematica().getId_tematica());
        }

        fechaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                obtenerFecha();
            }
        });

        horaInicioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                obtenerHora("inicio");
            }
        });

        horaFinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                obtenerHora("fin");
            }
        });

        confirmarButton.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(crearEventoNombre.getText()) &&
                        !TextUtils.isEmpty(crearEventoDespricion.getText()) &&
                        !TextUtils.isEmpty(crearEventoFecha.getText()) &&
                        !TextUtils.isEmpty(crearEventoHoraInicio.getText()) &&
                        !TextUtils.isEmpty(crearEventoHoraFin.getText()) &&
                        tematica.getSelectedItemPosition() != -1) {
                    Ubicacion ubicacion = ((CrearEditarEventoActivity) getActivity()).getUbicacion();
                    HashSet<Ubicacion> ubicaciones = new HashSet<Ubicacion>();
                    ubicaciones.add(ubicacion);
                    String fechaEvento = crearEventoFecha.getText().toString();
                    Date fechaEventoDate = null;
                    try {
                        fechaEventoDate = new SimpleDateFormat("dd-MM-yyyy").parse(fechaEvento);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    Evento evento = new Evento(0, crearEventoNombre.getText().toString(),
                            crearEventoDespricion.getText().toString(),
                            null, fechaEventoDate,
                            LocalTime.parse(crearEventoHoraInicio.getText()),
                            LocalTime.parse(crearEventoHoraFin.getText()),
                            ubicaciones, (Tematica) tematica.getSelectedItem(),
                            (UsuarioResponsable) parentActivity.getUsuario(), true);

                    if (accion == INSERTAR_EVENTO) {
                        estadoSesion = crearEditarEventoViewModel.insertarEvento(evento);
                        if (estadoSesion == INSERTAR_EXITO) {
                            Toast.makeText(getActivity(), "El evento se ha añadido con éxito", Toast.LENGTH_SHORT).show();
                            getActivity().finish();
                        } else if (estadoSesion == INSERTAR_FALLIDO) {
                            Toast.makeText(getActivity(), "No se ha podido insertar el evento", Toast.LENGTH_SHORT).show();
                        }
                    } else if (accion == ACTUALIZAR_EVENTO) {
                        estadoSesion = crearEditarEventoViewModel.actualizarEvento(evento);
                        if (estadoSesion == ACTUALIZAR_EXITO) {
                            Toast.makeText(getActivity(), "El evento se ha actualizado con éxito", Toast.LENGTH_SHORT).show();
                            getActivity().finish();
                        } else if (estadoSesion == ACTUALIZAR_FALLIDO) {
                            Toast.makeText(getActivity(), "No se ha podido actualizar el evento", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        crearEditarEventoViewModel.getTematicasList().observe(getViewLifecycleOwner(), new Observer<List<Tematica>>() {
            @Override
            public void onChanged(@Nullable List<Tematica> tematicas) {
                ArrayList<Tematica> arrayList = new ArrayList<>();
                for (Tematica t : tematicas) {
                    arrayList.add(t);
                }
                ArrayAdapter<Tematica> arrayAdapter = new ArrayAdapter<Tematica>(getActivity(), android.R.layout.simple_spinner_item, arrayList);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                tematica.setAdapter(arrayAdapter);
                tematica.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String tutorialsName = parent.getItemAtPosition(position).toString();
                        Toast.makeText(parent.getContext(), "Selected: " + tutorialsName, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
            }
        });
        return view;
    }

    public void obtenerFecha() {
        DatePickerDialog recogerFecha = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //Esta variable lo que realiza es aumentar en uno el mes ya que comienza desde 0 = enero
                final int mesActual = month + 1;
                //Formateo el día obtenido: antepone el 0 si son menores de 10
                String diaFormateado = (dayOfMonth < 10) ? CERO + String.valueOf(dayOfMonth) : String.valueOf(dayOfMonth);
                //Formateo el mes obtenido: antepone el 0 si son menores de 10
                String mesFormateado = (mesActual < 10) ? CERO + String.valueOf(mesActual) : String.valueOf(mesActual);
                //Muestro la fecha con el formato deseado
                crearEventoFecha.setText(diaFormateado + BARRA + mesFormateado + BARRA + year);
            }
            //Estos valores deben ir en ese orden, de lo contrario no mostrara la fecha actual
            /**
             *También puede cargar los valores que quiera
             */
        }, anio, mes, dia);
        //Muestro el widget
        recogerFecha.show();
    }

    private void obtenerHora(String momento) {
        TimePickerDialog recogerHora = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                //Formateo el hora obtenido: antepone el 0 si son menores de 10
                String horaFormateada = (hourOfDay < 10) ? String.valueOf(CERO + hourOfDay) : String.valueOf(hourOfDay);
                //Formateo el minuto obtenido: antepone el 0 si son menores de 10
                String minutoFormateado = (minute < 10) ? String.valueOf(CERO + minute) : String.valueOf(minute);
                //Obtengo el valor a.m. o p.m., dependiendo de la selección del usuario
                String AM_PM;
                if (hourOfDay < 12) {
                    AM_PM = "a.m.";
                } else {
                    AM_PM = "p.m.";
                }
                //Muestro la hora con el formato deseado
                if (momento == "inicio") {
                    crearEventoHoraInicio.setText(horaFormateada + DOS_PUNTOS + minutoFormateado);

                } else if (momento == "fin") {
                    crearEventoHoraFin.setText(horaFormateada + DOS_PUNTOS + minutoFormateado);
                }
            }
            //Estos valores deben ir en ese orden
            //Al colocar en false se muestra en formato 12 horas y true en formato 24 horas
            //Pero el sistema devuelve la hora en formato 24 horas
        }, hora, minuto, false);
        recogerHora.show();
    }

    public Usuario getUsuario() {
        return this.usuario;
    }

    public Evento getEvento() {
        return this.evento;
    }
}
