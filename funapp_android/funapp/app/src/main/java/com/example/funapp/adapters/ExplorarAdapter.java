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
import com.example.funapp.models.Tematica;

import java.util.List;

public class ExplorarAdapter extends RecyclerView.Adapter<ExplorarAdapter.ViewHolder> {

    private List<Tematica> tematicaList;
    private int layout;
    private Activity activity;
    private OnItemClickListener listenerItem;

    public ExplorarAdapter(List<Tematica> tematicaList, int layout, Activity activity, OnItemClickListener listenerItem) {
        this.tematicaList = tematicaList;
        this.layout = layout;
        this.activity = activity;
        this.listenerItem = listenerItem;
    }

    @NonNull
    @Override
    public ExplorarAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(activity).inflate(layout, viewGroup, false);
        ExplorarAdapter.ViewHolder viewHolder = new ExplorarAdapter.ViewHolder(v);
        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ExplorarAdapter.ViewHolder viewHolder, final int i) {

        final Tematica tematica = tematicaList.get(i);

        viewHolder.tvNombreTematica.setText(tematica.getNombre());

        switch (tematica.getDescripcion()) {
            case "cultura":
                viewHolder.imgvTematica.setImageResource(R.drawable.cultura);
                break;
            case "espectaculos":
                viewHolder.imgvTematica.setImageResource(R.drawable.espectaculos);
                break;
            case "gastronomia":
                viewHolder.imgvTematica.setImageResource(R.drawable.gastronomia);
                break;
            case "entretenimiento":
                viewHolder.imgvTematica.setImageResource(R.drawable.entretenimiento);
                break;
            case "deporte":
                viewHolder.imgvTematica.setImageResource(R.drawable.deporte);
                break;
            case "tecnologia":
                viewHolder.imgvTematica.setImageResource(R.drawable.tecnologia);
                break;
            case "beneficos":
                viewHolder.imgvTematica.setImageResource(R.drawable.beneficos);
                break;
            case "ponencias":
                viewHolder.imgvTematica.setImageResource(R.drawable.ponencias);
                break;
        }

        viewHolder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listenerItem.onItemClick(tematica, i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return tematicaList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvNombreTematica;
        ImageView imgvTematica;
        ConstraintLayout container;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombreTematica = itemView.findViewById(R.id.tvNombreExplorar);
            imgvTematica = itemView.findViewById(R.id.imgvExplorar);
            container = itemView.findViewById(R.id.constraintLayoutExplorar);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Tematica tematica, int position);
    }
}