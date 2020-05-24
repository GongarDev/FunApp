package com.example.funapp.fragments;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import com.example.funapp.R;
import com.example.funapp.models.Evento;
import com.example.funapp.models.Usuario;
import com.example.funapp.util.Protocolo;
import com.example.funapp.util.SocketHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.time.format.DateTimeFormatter;


public class EventoDetallesFragment extends Fragment implements Protocolo {

    ImageView imgvLogoEntidad;
    TextView tvPublicado;
    TextView tvEntidad;
    TextView tvNombreEvento;
    TextView tvDescripcion;
    TextView tvSuscritos;
    TextView tvFechaHora;
    TextView tvDuracion;
    CheckBox cbCodigoQR;
    ImageButton imgbPublicaciones;
    ImageButton imgbValoraciones;
    Button bSuscribirse;

    private String mensaje;
    private Gson gson;

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
        tvDuracion = view.findViewById(R.id.tvEventoDetallesDuracion);
        tvPublicado = view.findViewById(R.id.tvEventoSeleccionadoCreado);
        tvEntidad = view.findViewById(R.id.tvEventoSeleccionadoEntidad);
        cbCodigoQR = view.findViewById(R.id.cbBonificaciones);
        imgbPublicaciones = view.findViewById(R.id.imgbEventoSeleccionadoPublicaciones);
        imgbValoraciones = view.findViewById(R.id.imgBEventoSeleccionadoValoraciones);
        bSuscribirse = view.findViewById(R.id.bEventoSeleccionadoSuscribirse);

        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void mostrarEvento(final Evento evento, Usuario usuario, Integer tipoUsuario, Integer seccion) {

        int suscritos = obtenerSuscritos(evento.getId_evento());
        tvNombreEvento.setText(evento.getNombre());
        tvDescripcion.setText(evento.getDescripcion());
        tvSuscritos.setText(suscritos + " usuarios suscritos");

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

        tvPublicado.setText("Creado por "+evento.getUsuario().getNombre() + " el " +
                evento.getFecha_publicacion_LocalDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));

        //tvEntidad.setText(sendero.getPermiso());

        cbCodigoQR.setEnabled(false);

        /*
        imgbPublicaciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getActivity(), publicacionesEventoActivity.class);
                intent.putExtra("evento", evento);
                intent.putExtra("usuario", usuario);
                intent.putExtra("tipoUsuario", tipoUsuario);
                startActivity(intent);
            }
        });

        imgbValoraciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getActivity(), valoracionesActivity.class);
                intent.putExtra("evento", evento);
                intent.putExtra("usuario", usuario);
                intent.putExtra("tipoUsuario", tipoUsuario);
                startActivity(intent);
            }
        });
         */

        if(tipoUsuario==SESION_ABIERTA_RESPONSABLE || seccion==HISTORIAL_EVENTOS){
            bSuscribirse.setVisibility(View.GONE);
        } else {
            bSuscribirse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }

    public int obtenerSuscritos(int id_evento){

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
}
