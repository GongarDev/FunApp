package com.example.funapp.adapters;

import android.app.Activity;
import android.os.Build;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.example.funapp.R;
import com.example.funapp.models.Evento;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class NotificacionesAdapter extends RecyclerView.Adapter<NotificacionesAdapter.ViewHolder> {

    private List<Evento> eventos;
    private int layout;
    private Activity activity;
    private OnItemClickListener listenerItem;

    public NotificacionesAdapter(List<Evento> eventos, int layout, Activity activity, OnItemClickListener listenerItem) {
        this.eventos = eventos;
        this.layout = layout;
        this.activity = activity;
        this.listenerItem = listenerItem;
    }

    @NonNull
    @Override
    public NotificacionesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(activity).inflate(layout, viewGroup, false);
        NotificacionesAdapter.ViewHolder viewHolder = new NotificacionesAdapter.ViewHolder(v);
        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull NotificacionesAdapter.ViewHolder viewHolder, final int i) {

        final Evento evento = eventos.get(i);

        if(!evento.isActivo()){
            viewHolder.tvMensaje.setText("El evento \""+evento.getNombre()+"\" ha sido cancelado, " +
                    "si se reanudase y empezara en los próximos días, se te avisará.");
        }else{
            int minutos = evento.getHora_fin().getMinute();
            String minutosAdapter ="";
            if (minutos < 10)
                minutosAdapter =":0"+minutos;
            else
                minutosAdapter =":"+minutos;

            String fechaEvento = evento.getFecha_evento_LocalDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            if(fechaEvento.equals(LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))){
                viewHolder.tvMensaje.setText("¡Hoy empieza el evento \""+evento.getNombre()+"\" a las " +
                        evento.getHora_inicio().getHour()+minutosAdapter+"!¡Te esperamos!");
            }else if(fechaEvento.equals(LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))){
                viewHolder.tvMensaje.setText("Mañana tienes el evento \""+evento.getNombre()+"\" a las " +
                        evento.getHora_inicio().getHour()+minutosAdapter+", no faltes =D.");
            }else if(fechaEvento.equals(LocalDate.now().plusDays(2).format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))){
                viewHolder.tvMensaje.setText("Tienes el evento \""+evento.getNombre()+"\" dentro de dos días a las " +
                        evento.getHora_inicio().getHour()+minutosAdapter+".");
            }else if(fechaEvento.equals(LocalDate.now().plusDays(7).format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))){
                viewHolder.tvMensaje.setText("Tienes el evento \""+evento.getNombre()+"\" dentro de una semana.");
            }
        }
        viewHolder.container.setOnClickListener(new View.OnClickListener() {
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

        TextView tvMensaje;
        ConstraintLayout container;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMensaje = itemView.findViewById(R.id.tvNotificacionesMensaje);
            container = itemView.findViewById(R.id.containerNotificaciones);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Evento evento, int position);
    }
}