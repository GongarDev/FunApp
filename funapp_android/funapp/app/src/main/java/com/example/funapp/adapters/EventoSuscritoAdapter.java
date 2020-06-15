package com.example.funapp.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.funapp.R;
import com.example.funapp.models.Evento;
import com.example.funapp.util.Protocolo;
import com.example.funapp.util.SocketHandler;
import com.google.gson.Gson;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class EventoSuscritoAdapter extends RecyclerView.Adapter<EventoSuscritoAdapter.ViewHolder> implements Protocolo {

    private List<Evento> eventosList;
    private int layout;
    private Activity activity;
    private OnItemClickListener listenerItem;
    private OnItemClickListener listenerDesuscribirse;

    private String mensaje;
    private Gson gson;

    public EventoSuscritoAdapter(List<Evento> eventosList, int layout, Activity activity, OnItemClickListener listenerItem,
                                 OnItemClickListener listenerDesuscribirse) {
        this.eventosList = eventosList;
        this.layout = layout;
        this.activity = activity;
        this.listenerItem = listenerItem;
        this.listenerDesuscribirse = listenerDesuscribirse;
        this.gson = new Gson();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(activity).inflate(layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

        final Evento evento = eventosList.get(i);

        viewHolder.containerEventoSuscritos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listenerItem.onItemClick(evento, i);
            }
        });

        switch (evento.getTematica().getNombre()) {
            case "Cultura local":
                viewHolder.imgvSuscritos.setImageResource(R.drawable.cultura);
                break;
            case "Espectáculos":
                viewHolder.imgvSuscritos.setImageResource(R.drawable.espectaculos);
                break;
            case "Gastronomía":
                viewHolder.imgvSuscritos.setImageResource(R.drawable.gastronomia);
                break;
            case "Entretenimiento":
                viewHolder.imgvSuscritos.setImageResource(R.drawable.entretenimiento);
                break;
            case "Deporte":
                viewHolder.imgvSuscritos.setImageResource(R.drawable.deporte);
                break;
            case "Tecnología":
                viewHolder.imgvSuscritos.setImageResource(R.drawable.tecnologia);
                break;
            case "Benéficos":
                viewHolder.imgvSuscritos.setImageResource(R.drawable.beneficos);
                break;
            case "Ponencias":
                viewHolder.imgvSuscritos.setImageResource(R.drawable.ponencias);
                break;
        }

        viewHolder.tvEventoSuscritosNombre.setText(evento.getNombre());
        viewHolder.tvEventoSuscritosFechaHora.setText(
                "Empieza el día "+ evento.getFecha_evento_LocalDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))+
                        " a las " + evento.getHora_inicio());
        if(evento.isActivo()) {
            viewHolder.tvEventoSuscritosSuscritos.setText(obtenerSuscritos(evento.getId_evento()) + " suscritos");
        }else {
            viewHolder.tvEventoSuscritosSuscritos.setText("El evento ha sido suspendido por el momento.");
            viewHolder.tvEventoSuscritosSuscritos.setTextColor(ContextCompat.getColor(activity.getBaseContext(), R.color.colorAccent));
        }
        viewHolder.imgbSuscritosDesuscribirse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listenerDesuscribirse.onItemClick(evento, i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return eventosList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout containerEventoSuscritos;
        ImageView imgvSuscritos;
        TextView tvEventoSuscritosNombre;
        TextView tvEventoSuscritosFechaHora;
        TextView tvEventoSuscritosSuscritos;
        ImageButton imgbSuscritosDesuscribirse;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            containerEventoSuscritos = itemView.findViewById(R.id.containerEventoSuscritos);
            imgvSuscritos = itemView.findViewById(R.id.imgvEventoSuscritos);
            tvEventoSuscritosNombre = itemView.findViewById(R.id.tvEventoSuscritosNombre);
            tvEventoSuscritosFechaHora = itemView.findViewById(R.id.tvEventoSuscritosFechaHora);
            tvEventoSuscritosSuscritos = itemView.findViewById(R.id.tvEventoSuscritosSuscritos);
            imgbSuscritosDesuscribirse = itemView.findViewById(R.id.imgbSuscritosDesuscribirse);
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

    public interface OnItemClickListener {
        void onItemClick(Evento evento, int position);
    }
}
