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

public class EventoActivoAdapter extends RecyclerView.Adapter<EventoActivoAdapter.ViewHolder> {

    private List<Evento> eventos;
    private int layout;
    private Activity activity;
    private OnItemClickListener listenerItem;
    private OnItemClickListener listenerEditar;
    private OnItemClickListener listenerSuspender;

    public EventoActivoAdapter(List<Evento> eventos, int layout, Activity activity, OnItemClickListener listenerItem,
                               OnItemClickListener listenerEditar, OnItemClickListener listenerSuspender) {

        this.eventos = eventos;
        this.layout = layout;
        this.activity = activity;
        this.listenerItem = listenerItem;
        this.listenerEditar = listenerEditar;
        this.listenerSuspender = listenerSuspender;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(activity).inflate(layout, viewGroup, false);

        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull EventoActivoAdapter.ViewHolder viewHolder, final int i) {

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

        viewHolder.tvSuspender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listenerSuspender.onItemClick(evento, i);
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
        TextView tvSuspender;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgvEvento = itemView.findViewById(R.id.imgvEventosActivos);
            tvNombreEvento = itemView.findViewById(R.id.tvNombreEventosActivos);
            tvEditar = itemView.findViewById(R.id.tvEditarEventosActivos);
            tvSuspender = itemView.findViewById(R.id.tvSuspenderEventosActivos);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Evento evento, int position);
    }

}