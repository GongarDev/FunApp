package com.example.funapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.funapp.R;
import com.example.funapp.activities.PublicacionesActivity;
import com.example.funapp.activities.ValoracionesActivity;
import com.example.funapp.models.Entidad;
import com.example.funapp.models.Evento;
import com.example.funapp.models.Publicacion;
import com.example.funapp.models.Usuario;
import com.example.funapp.util.Protocolo;
import com.example.funapp.util.SocketHandler;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class EventoDetallesFragment extends Fragment implements Protocolo {

    ImageView imgvLogoEntidad;
    TextView tvPublicado;
    TextView tvEntidad;
    TextView tvNombreEvento;
    TextView tvCreado;
    TextView tvDescripcion;
    TextView tvSuscritos;
    TextView tvFechaHora;
    TextView tvDuracion;
    CheckBox cbCodigoQR;
    ImageButton imgbPublicaciones;
    ImageButton imgbValoraciones;
    Button bSuscribirse;
    Button bVerCodigoQR;
    private OnPublicacionSelected callback;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private String mensaje;
    private Gson gson;
    private Integer estadoSesion;

    public EventoDetallesFragment() {
        this.gson = new GsonBuilder().setDateFormat("dd-MM-yyyy").create();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_evento_detalles, container, false);

        imgvLogoEntidad = view.findViewById(R.id.imgvEventoSelecionadoLogo);
        tvNombreEvento = view.findViewById(R.id.tvEventoSeleccionadoNombre);
        tvDescripcion = view.findViewById(R.id.tvEventoSeleccionadoDescripcion);
        tvSuscritos = view.findViewById(R.id.tvEventoSeleccionadoSuscritos);
        tvFechaHora = view.findViewById(R.id.tvEventoSeleccionadoFecha);
        tvCreado = view.findViewById(R.id.tvEventoSeleccionadoCreado);
        tvDuracion = view.findViewById(R.id.tvEventoDetallesDuracion);
        tvPublicado = view.findViewById(R.id.tvEventoSeleccionadoCreado);
        tvEntidad = view.findViewById(R.id.tvEventoSeleccionadoEntidad);
        cbCodigoQR = view.findViewById(R.id.cbBonificaciones);
        imgbPublicaciones = view.findViewById(R.id.imgbEventoSeleccionadoPublicaciones);
        imgbValoraciones = view.findViewById(R.id.imgBEventoSeleccionadoValoraciones);
        bSuscribirse = view.findViewById(R.id.bEventoSeleccionadoSuscribirse);
        bVerCodigoQR = view.findViewById(R.id.bVerCodigoQR);
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void mostrarEvento(final Evento evento, Usuario usuario, Integer tipoUsuario, Integer seccion) {

        int suscritos = obtenerSuscritos(evento.getId_evento());
        tvNombreEvento.setText(evento.getNombre());
        tvDescripcion.setText(evento.getDescripcion());
        tvSuscritos.setText(suscritos + " usuarios suscritos");
        tvCreado.setText("Creado por "+evento.getUsuario().getSeudonimo()+ " el "
                +evento.getFecha_publicacion_LocalDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        Entidad entidad = cargarEntidad(evento.getUsuario().getId_usuario());
        tvEntidad.setText("Pertenece a la entidad \""+entidad.getNombre()+"\"");

        switch (evento.getTematica().getNombre()) {
            case "Cultura local":
                imgvLogoEntidad.setImageResource(R.drawable.cultura);
                break;
            case "Espectáculos":
                imgvLogoEntidad.setImageResource(R.drawable.espectaculos);
                break;
            case "Gastronomía":
                imgvLogoEntidad.setImageResource(R.drawable.gastronomia);
                break;
            case "Entretenimiento":
                imgvLogoEntidad.setImageResource(R.drawable.entretenimiento);
                break;
            case "Deporte":
                imgvLogoEntidad.setImageResource(R.drawable.deporte);
                break;
            case "Tecnología":
                imgvLogoEntidad.setImageResource(R.drawable.tecnologia);
                break;
            case "Benéficos":
                imgvLogoEntidad.setImageResource(R.drawable.beneficos);
                break;
            case "Ponencias":
                imgvLogoEntidad.setImageResource(R.drawable.ponencias);
                break;
        }

        int minutos = evento.getHora_inicio().getMinute();
        if (minutos < 10)
            tvFechaHora.setText("Empieza el día " + evento.getFecha_evento_LocalDate().format(
                    DateTimeFormatter.ofPattern("dd-MM-yyyy")) + " a las " + evento.getHora_inicio().getHour() + ":0" + evento.getHora_inicio().getMinute());
        else
            tvFechaHora.setText("Empieza el día " + evento.getFecha_evento_LocalDate().format(
                    DateTimeFormatter.ofPattern("dd-MM-yyyy")) + " a las " + evento.getHora_inicio().getHour() + ":" + evento.getHora_inicio().getMinute());

        minutos = evento.getHora_fin().getMinute();
        if (minutos < 10)
            tvDuracion.setText("Finaliza sobre las " + evento.getHora_fin().getHour() + ":0" + evento.getHora_fin().getMinute());
        else
            tvDuracion.setText("Finaliza sobre las " + evento.getHora_fin().getHour() + ":" + evento.getHora_fin().getMinute());

        tvPublicado.setText("Creado por " + evento.getUsuario().getNombre() + " el " +
                evento.getFecha_publicacion_LocalDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));

        cbCodigoQR.setEnabled(false);

        imgbPublicaciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.OnPublicacionSelected(evento);
            }
        });

        imgbValoraciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(evento.getFecha_evento_LocalDate().isAfter(LocalDate.now())){
                    Snackbar.make(v, "Podrás ver las valoraciones cuando haya finalizado.", Snackbar.LENGTH_LONG)
                            .setAction("Cerrar", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                }
                            })
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();
                }else {
                    Intent intent = new Intent(getActivity(), ValoracionesActivity.class);
                    intent.putExtra("evento", evento);
                    intent.putExtra("usuario", usuario);
                    intent.putExtra("tipoUsuario", tipoUsuario);
                    startActivity(intent);
                }
            }
        });

        if (seccion == HISTORIAL_EVENTOS) {
            bSuscribirse.setVisibility(View.GONE);
            bVerCodigoQR.setVisibility(View.GONE);
        } else if (seccion == MIS_EVENTOS) {
            bSuscribirse.setVisibility(View.GONE);
            bVerCodigoQR.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    verCodigoQR(evento);
                }
            });
        } else if (tipoUsuario == SESION_ABIERTA_RESPONSABLE && seccion != MIS_EVENTOS) {
            bSuscribirse.setVisibility(View.GONE);
            bVerCodigoQR.setVisibility(View.GONE);
        } else if (tipoUsuario == SESION_ABIERTA_ESTANDAR && seccion != MIS_EVENTOS) {
            bVerCodigoQR.setVisibility(View.GONE);
            bSuscribirse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(existeSuscrito(evento.getId_evento(), usuario.getId_usuario())==EXISTE){
                        Snackbar.make(getView(), "Ya te suscribiste a este evento.", Snackbar.LENGTH_LONG)
                                .setAction("Cerrar", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                    }
                                })
                                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                .show();
                    }else {
                        suscribirse(evento.getId_evento(), usuario.getId_usuario());
                        Snackbar.make(getView(), "Te has suscrito a este evento.", Snackbar.LENGTH_LONG)
                                .setAction("Cerrar", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                    }
                                })
                                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                .show();
                    }
                }
            });
        }
    }

    public Entidad cargarEntidad(int id_usuario) {
        try {
            this.mensaje = this.gson.toJson(CONSULTAR_ENTIDAD);
            SocketHandler.getSalida().writeUTF(this.mensaje);
            this.mensaje = this.gson.toJson(id_usuario);
            SocketHandler.getSalida().writeUTF(this.mensaje);
            this.mensaje = (String) SocketHandler.getEntrada().readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ((Entidad) this.gson.fromJson(this.mensaje, Entidad.class));
    }

    public int obtenerSuscritos(int id_evento) {

        int suscritos = 0;
        try {
            this.mensaje = this.gson.toJson(SUSCRITOS_EVENTO);
            SocketHandler.getSalida().writeUTF(this.mensaje);
            this.mensaje = this.gson.toJson(id_evento);
            SocketHandler.getSalida().writeUTF(this.mensaje);
            this.mensaje = (String) SocketHandler.getEntrada().readUTF();
            suscritos = (Integer) this.gson.fromJson(this.mensaje, Integer.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return suscritos;
    }

    public Integer existeSuscrito(int id_evento, int id_usuario) {
        try {
            this.mensaje = this.gson.toJson(COMPROBAR_SUSCRITO);
            SocketHandler.getSalida().writeUTF(this.mensaje);
            this.mensaje = this.gson.toJson(id_evento);
            SocketHandler.getSalida().writeUTF(this.mensaje);
            this.mensaje = this.gson.toJson(id_usuario);
            SocketHandler.getSalida().writeUTF(this.mensaje);
            this.mensaje = (String) SocketHandler.getEntrada().readUTF();
            this.estadoSesion = (Integer) this.gson.fromJson(this.mensaje, Integer.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this.estadoSesion;
    }

    public Integer suscribirse(int id_evento, int id_usuario) {
        try {
            this.mensaje = this.gson.toJson(SUSCRIPCIONES_SUSCRIBIRSE);
            SocketHandler.getSalida().writeUTF(this.mensaje);
            this.mensaje = this.gson.toJson(id_evento);
            SocketHandler.getSalida().writeUTF(this.mensaje);
            this.mensaje = this.gson.toJson(id_usuario);
            SocketHandler.getSalida().writeUTF(this.mensaje);
            this.mensaje = (String) SocketHandler.getEntrada().readUTF();
            this.estadoSesion = (Integer) this.gson.fromJson(this.mensaje, Integer.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this.estadoSesion;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void verCodigoQR(Evento evento) {

        builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.popup_vercodigoqr_evento, null);
        builder.setView(view);
        final ImageView imgVCodigoQR = view.findViewById(R.id.imgVVerCodigoQR);
        try {
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.encodeBitmap("evento/"+
                    evento.getId_evento() + "/" + evento.getNombre() + "/" + evento.getFecha_publicacion_LocalDate().toString(),
                    BarcodeFormat.QR_CODE, 400, 400);
            imgVCodigoQR.setImageBitmap(bitmap);
        } catch (Exception e) {
        }
        dialog = builder.create();
        dialog.show();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            callback = (OnPublicacionSelected) context;

        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "Debería implementar el interfaz OnEventoSelected");
        }
    }

    public interface OnPublicacionSelected {
        public void OnPublicacionSelected(Evento evento);
    }
}
