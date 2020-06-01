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

import java.time.format.DateTimeFormatter;
import java.util.List;

public class EventoInicioAdapter extends RecyclerView.Adapter<EventoInicioAdapter.ViewHolder> {

    private List<Evento> eventos;
    private int layout;
    private Activity activity;
    private OnItemClickListener listenerItem;

    public EventoInicioAdapter(List<Evento> eventos, int layout, Activity activity, OnItemClickListener listenerItem) {

        this.eventos = eventos;
        this.layout = layout;
        this.activity = activity;
        this.listenerItem = listenerItem;
    }

    @NonNull
    @Override
    public EventoInicioAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(activity).inflate(layout, viewGroup, false);
        EventoInicioAdapter.ViewHolder viewHolder = new EventoInicioAdapter.ViewHolder(v);
        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull EventoInicioAdapter.ViewHolder viewHolder, final int i) {

        final Evento evento = eventos.get(i);

        viewHolder.tvNombreEvento.setText(evento.getNombre());
        viewHolder.tvFecha.setText(evento.getFecha_evento_LocalDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));

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

        viewHolder.imgvEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listenerItem.onItemClick(evento, i);
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
        TextView tvFecha;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgvEvento = itemView.findViewById(R.id.imgvInicioEventos);
            tvNombreEvento = itemView.findViewById(R.id.tvNombreInicioEventos);
            tvFecha = itemView.findViewById(R.id.tvFechaInicioEventos);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Evento evento, int position);
    }
}
