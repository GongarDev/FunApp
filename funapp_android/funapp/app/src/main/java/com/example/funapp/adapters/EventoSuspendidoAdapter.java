package com.example.funapp.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.funapp.R;
import com.example.funapp.models.Evento;

import java.util.List;

public class EventoSuspendidoAdapter extends RecyclerView.Adapter<EventoSuspendidoAdapter.ViewHolder> {

    private List<Evento> eventos;
    private int layout;
    private Activity activity;
    private OnItemClickListener listenerItem;
    private OnItemClickListener listenerEditar;
    private OnItemClickListener listenerActivar;

    public EventoSuspendidoAdapter(List<Evento> eventos, int layout, Activity activity, OnItemClickListener listenerItem,
                                   OnItemClickListener listenerEditar, OnItemClickListener listenerActivar) {

        this.eventos = eventos;
        this.layout = layout;
        this.activity = activity;
        this.listenerItem = listenerItem;
        this.listenerEditar = listenerEditar;
        this.listenerActivar = listenerActivar;
    }

    @NonNull
    @Override
    public EventoSuspendidoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(activity).inflate(layout, viewGroup, false);

        EventoSuspendidoAdapter.ViewHolder viewHolder = new EventoSuspendidoAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull EventoSuspendidoAdapter.ViewHolder viewHolder, final int i) {

        final Evento evento = eventos.get(i);

        viewHolder.tvNombreEvento.setText(evento.getNombre());

        viewHolder.imgvEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listenerItem.onItemClick(evento, i);
            }
        });

        switch (evento.getTematica().getNombre()) {
            case "Cultura local":
                viewHolder.imgvEvento.setImageResource(R.drawable.cultura);
                break;
            case "Espectáculos":
                viewHolder.imgvEvento.setImageResource(R.drawable.espectaculos);
                break;
            case "Gastronomía":
                viewHolder.imgvEvento.setImageResource(R.drawable.gastronomia);
                break;
            case "Entretenimiento":
                viewHolder.imgvEvento.setImageResource(R.drawable.entretenimiento);
                break;
            case "Deporte":
                viewHolder.imgvEvento.setImageResource(R.drawable.deporte);
                break;
            case "Tecnología":
                viewHolder.imgvEvento.setImageResource(R.drawable.tecnologia);
                break;
            case "Benéficos":
                viewHolder.imgvEvento.setImageResource(R.drawable.beneficos);
                break;
            case "Ponencias":
                viewHolder.imgvEvento.setImageResource(R.drawable.ponencias);
                break;
        }

        viewHolder.tvEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listenerEditar.onItemClick(evento, i);
            }
        });

        viewHolder.tvActivar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listenerActivar.onItemClick(evento, i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return eventos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgvEvento;
        TextView tvNombreEvento;
        TextView tvEditar;
        TextView tvActivar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgvEvento = itemView.findViewById(R.id.imgvEventosSuspendidos);
            tvNombreEvento = itemView.findViewById(R.id.tvNombreEventosSuspendidos);
            tvEditar = itemView.findViewById(R.id.tvEditarEventosSuspendidos);
            tvActivar = itemView.findViewById(R.id.tvSuspenderEventosSuspendidos);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Evento evento, int position);
    }

}