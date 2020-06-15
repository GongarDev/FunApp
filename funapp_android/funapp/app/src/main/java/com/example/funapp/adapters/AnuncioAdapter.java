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
import com.example.funapp.models.Anuncio;
import com.example.funapp.models.Evento;
import com.example.funapp.util.SocketHandler;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

public class AnuncioAdapter extends RecyclerView.Adapter<AnuncioAdapter.ViewHolder> {

    private List<Anuncio> anunciosList;
    private int layout;
    private Activity activity;
    private OnItemClickListener listenerItem;

    public AnuncioAdapter(List<Anuncio> anunciosList, int layout, Activity activity) {
        this.anunciosList = anunciosList;
        this.layout = layout;
        this.activity = activity;
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

        final Anuncio anuncios = anunciosList.get(i);
        viewHolder.tvTitulo.setText(anuncios.getNombre());
        viewHolder.tvDescripcion.setText(anuncios.getMensaje());
    }

    @Override
    public int getItemCount() {
        return anunciosList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvTitulo;
        TextView tvDescripcion;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitulo = itemView.findViewById(R.id.tvAnunciosTitulo);
            tvDescripcion = itemView.findViewById(R.id.tvAnunciosDescripcion);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Anuncio anuncio, int position);
    }
}

