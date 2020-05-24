package com.example.funapp.adapters;

import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.funapp.R;
import com.example.funapp.models.Evento;
import com.example.funapp.util.Protocolo;
import com.example.funapp.util.SocketHandler;
import com.google.gson.Gson;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class EventoTematicaAdapter extends RecyclerView.Adapter<EventoTematicaAdapter.ViewHolder> implements Protocolo {

    private List<Evento> eventosList;
    private int layout;
    private Activity activity;
    private OnItemClickListener listenerItem;

    private String mensaje;
    private Gson gson;

    public EventoTematicaAdapter(List<Evento> eventosList, int layout, Activity activity, OnItemClickListener listenerItem) {
        this.eventosList = eventosList;
        this.layout = layout;
        this.activity = activity;
        this.listenerItem = listenerItem;
        this.gson = new Gson();
    }

    @NonNull
    @Override
    public EventoTematicaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(activity).inflate(layout, viewGroup, false);
        EventoTematicaAdapter.ViewHolder viewHolder = new EventoTematicaAdapter.ViewHolder(v);
        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull EventoTematicaAdapter.ViewHolder viewHolder, final int i) {

        final Evento evento = eventosList.get(i);

        viewHolder.tvEventoTematicaNombre.setText(evento.getNombre());
        viewHolder.tvEventoTematicaFechaHora.setText(
                "Empieza el día "+ evento.getFecha_evento_LocalDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))+
                " a las " + evento.getHora_inicio());
        viewHolder.tvEventoTematicaSuscritos.setText(obtenerSuscritos(evento.getId_evento())+" suscritos");
    }

    @Override
    public int getItemCount() {
        return eventosList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgvTematica;
        TextView tvEventoTematicaNombre;
        TextView tvEventoTematicaFechaHora;
        TextView tvEventoTematicaSuscritos;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgvTematica = itemView.findViewById(R.id.imgvEventoTematica);
            tvEventoTematicaNombre = itemView.findViewById(R.id.tvEventoTematicaNombre);
            tvEventoTematicaFechaHora = itemView.findViewById(R.id.tvEventoTematicaFechaHora);
            tvEventoTematicaSuscritos = itemView.findViewById(R.id.tvEventoTematicaSuscritos);
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
