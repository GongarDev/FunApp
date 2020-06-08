package com.example.funapp.adapters;

import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;
import com.example.funapp.R;
import com.example.funapp.models.Evento;
import com.example.funapp.models.Valoracion;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ValoracionAdapter extends RecyclerView.Adapter<ValoracionAdapter.ViewHolder> {

    private List<Valoracion> valoraciones;
    private int layout;
    private Activity activity;

    public ValoracionAdapter(List<Valoracion> valoraciones, int layout, Activity activity) {
        this.valoraciones = valoraciones;
        this.layout = layout;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ValoracionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(activity).inflate(layout, viewGroup, false);
        ValoracionAdapter.ViewHolder viewHolder = new ValoracionAdapter.ViewHolder(v);
        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ValoracionAdapter.ViewHolder viewHolder, final int i) {

        final Valoracion valoracion = valoraciones.get(i);

        viewHolder.tvMensaje.setText(valoracion.getComentario());
        viewHolder.ratingBarValoraciones.setRating(valoracion.getPuntaje());
        viewHolder.ratingBarValoraciones.setIsIndicator(true);
        viewHolder.tvUsuario.setText("Publicado por "+ valoracion.getUsuario().getSeudonimo());
        viewHolder.tvFecha.setText(valoracion.getFecha_publicacion_LocalDate().
                format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + " a las " +
                valoracion.getHora().getHour() +":"+ valoracion.getHora().getMinute());
    }

    @Override
    public int getItemCount() {
        return valoraciones.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvMensaje;
        TextView tvUsuario;
        TextView tvFecha;
        RatingBar ratingBarValoraciones;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMensaje = itemView.findViewById(R.id.tvValoracionesMensaje);
            tvUsuario = itemView.findViewById(R.id.tvValoracionesUsuario);
            tvFecha = itemView.findViewById(R.id.tvValoracionesFecha);
            ratingBarValoraciones = itemView.findViewById(R.id.ratingBarValoraciones);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Evento evento, int position);
    }
}
