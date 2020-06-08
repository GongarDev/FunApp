package com.example.funapp.adapters;

import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.example.funapp.R;
import com.example.funapp.models.Evento;
import com.example.funapp.util.SocketHandler;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

public class AnuncioAdapter extends RecyclerView.Adapter<AnuncioAdapter.ViewHolder> {

    private List<Evento> eventos;
    private int layout;
    private Activity activity;
    private OnItemClickListener listenerItem;
    private String mensaje;
    private Gson gson;

    public AnuncioAdapter(List<Evento> eventos, int layout, Activity activity, OnItemClickListener listenerItem) {

        this.eventos = eventos;
        this.layout = layout;
        this.activity = activity;
        this.listenerItem = listenerItem;
        this.gson = new Gson();
    }

    @NonNull
    @Override
    public AnuncioAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(activity).inflate(layout, viewGroup, false);
        AnuncioAdapter.ViewHolder viewHolder = new AnuncioAdapter.ViewHolder(v);
        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull AnuncioAdapter.ViewHolder viewHolder, final int i) {

        final Evento evento = eventos.get(i);
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

        ConstraintLayout container;
        ImageView imgvEvento;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.containerPublicaciones);
            //imgvEvento = itemView.findViewById(R.id.imgvPublicaciones);
        }
    }

    public int obtenerImagen(int id_evento){

        int suscritos = 0;
        try {
            //this.mensaje = this.gson.toJson(DESCARGAR_IMAGEN);
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

