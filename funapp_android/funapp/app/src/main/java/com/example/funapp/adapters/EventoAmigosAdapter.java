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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.example.funapp.R;
import com.example.funapp.models.Evento;
import com.example.funapp.util.Protocolo;
import com.example.funapp.util.SocketHandler;
import com.google.gson.Gson;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class EventoAmigosAdapter extends RecyclerView.Adapter<EventoAmigosAdapter.ViewHolder> implements Protocolo {

    private List<Evento> eventosList;
    private int layout;
    private Activity activity;
    private OnItemClickListener listenerItem;

    private String mensaje;
    private Gson gson;

    public EventoAmigosAdapter(List<Evento> eventosList, int layout, Activity activity, OnItemClickListener listenerItem) {
        this.eventosList = eventosList;
        this.layout = layout;
        this.activity = activity;
        this.listenerItem = listenerItem;
        this.gson = new Gson();
    }

    @NonNull
    @Override
    public EventoAmigosAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(activity).inflate(layout, viewGroup, false);
        EventoAmigosAdapter.ViewHolder viewHolder = new EventoAmigosAdapter.ViewHolder(v);
        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull EventoAmigosAdapter.ViewHolder viewHolder, final int i) {

        final Evento evento = eventosList.get(i);

        viewHolder.containerEventoAmigos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listenerItem.onItemClick(evento, i);
            }
        });

        switch (evento.getTematica().getNombre()) {
            case "Cultura local":
                viewHolder.imgvEventoAmigos.setImageResource(R.drawable.cultura);
                break;
            case "Espectáculos":
                viewHolder.imgvEventoAmigos.setImageResource(R.drawable.espectaculos);
                break;
            case "Gastronomía":
                viewHolder.imgvEventoAmigos.setImageResource(R.drawable.gastronomia);
                break;
            case "Entretenimiento":
                viewHolder.imgvEventoAmigos.setImageResource(R.drawable.entretenimiento);
                break;
            case "Deporte":
                viewHolder.imgvEventoAmigos.setImageResource(R.drawable.deporte);
                break;
            case "Tecnología":
                viewHolder.imgvEventoAmigos.setImageResource(R.drawable.tecnologia);
                break;
            case "Benéficos":
                viewHolder.imgvEventoAmigos.setImageResource(R.drawable.beneficos);
                break;
            case "Ponencias":
                viewHolder.imgvEventoAmigos.setImageResource(R.drawable.ponencias);
                break;
        }
        viewHolder.tvEventoAmigosNombre.setText(evento.getNombre());
        viewHolder.tvEventoAmigosFechaHora.setText(
                "Empieza el día " + evento.getFecha_evento_LocalDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) +
                        " a las " + evento.getHora_inicio());
        viewHolder.tvEventoAmigosSuscritos.setText(obtenerSuscritos(evento.getId_evento()) + " suscritos");
    }

    @Override
    public int getItemCount() {
        return eventosList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout containerEventoAmigos;
        ImageView imgvEventoAmigos;
        TextView tvEventoAmigosNombre;
        TextView tvEventoAmigosFechaHora;
        TextView tvEventoAmigosSuscritos;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            containerEventoAmigos = itemView.findViewById(R.id.containerEventoAmigos);
            imgvEventoAmigos = itemView.findViewById(R.id.imgvEventoAmigos);
            tvEventoAmigosNombre = itemView.findViewById(R.id.tvEventoAmigosNombre);
            tvEventoAmigosFechaHora = itemView.findViewById(R.id.tvEventoAmigosFechaHora);
            tvEventoAmigosSuscritos = itemView.findViewById(R.id.tvEventoAmigosSuscritos);
        }
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

    public interface OnItemClickListener {
        void onItemClick(Evento evento, int position);
    }
}