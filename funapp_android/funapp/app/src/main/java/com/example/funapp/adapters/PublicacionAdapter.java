package com.example.funapp.adapters;

import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;
import com.example.funapp.R;
import com.example.funapp.models.Evento;
import com.example.funapp.models.Publicacion;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PublicacionAdapter extends RecyclerView.Adapter<PublicacionAdapter.ViewHolder> {

    private List<Publicacion> publicaciones;
    private int layout;
    private Activity activity;

    public PublicacionAdapter(List<Publicacion> publicaciones, int layout, Activity activity) {
        this.publicaciones = publicaciones;
        this.layout = layout;
        this.activity = activity;
    }

    @NonNull
    @Override
    public PublicacionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(activity).inflate(layout, viewGroup, false);
        PublicacionAdapter.ViewHolder viewHolder = new PublicacionAdapter.ViewHolder(v);
        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull PublicacionAdapter.ViewHolder viewHolder, final int i) {

        final Publicacion publicacion = publicaciones.get(i);

        viewHolder.tvMensaje.setText(publicacion.getMensaje());
        viewHolder.tvFecha.setText("Publicado el " + publicacion.getFecha_publicacion_LocalDate().
                format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + " a las " +
                publicacion.getHora().getHour() +":"+ publicacion.getHora().getMinute());
    }

    @Override
    public int getItemCount() {
        return publicaciones.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvMensaje;
        TextView tvFecha;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMensaje = itemView.findViewById(R.id.tvPublicacionesMensaje);
            tvFecha = itemView.findViewById(R.id.tvPublicacionesFecha);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Evento evento, int position);
    }
}