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

public class EventoHistorialAdapter extends RecyclerView.Adapter<EventoHistorialAdapter.ViewHolder> {

    private List<Evento> eventos;
    private int layout;
    private Activity activity;
    private OnItemClickListener listenerItem;

    public EventoHistorialAdapter(List<Evento> eventos, int layout, Activity activity, OnItemClickListener listenerItem) {

        this.eventos = eventos;
        this.layout = layout;
        this.activity = activity;
        this.listenerItem = listenerItem;
    }

    @NonNull
    @Override
    public EventoHistorialAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(activity).inflate(layout, viewGroup, false);
        EventoHistorialAdapter.ViewHolder viewHolder = new EventoHistorialAdapter.ViewHolder(v);
        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull EventoHistorialAdapter.ViewHolder viewHolder, final int i) {

        final Evento evento = eventos.get(i);

        viewHolder.tvNombreEvento.setText(evento.getNombre());
        viewHolder.tvFecha.setText(evento.getFecha_evento_LocalDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
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

            imgvEvento = itemView.findViewById(R.id.imgvHistorial);
            tvNombreEvento = itemView.findViewById(R.id.tvNombreHistorial);
            tvFecha = itemView.findViewById(R.id.tvFechaHistorial);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Evento evento, int position);
    }
}
