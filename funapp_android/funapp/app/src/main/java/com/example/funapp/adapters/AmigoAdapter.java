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
import com.example.funapp.models.Usuario;
import com.example.funapp.util.Protocolo;
import com.example.funapp.util.SocketHandler;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.List;

public class AmigoAdapter extends RecyclerView.Adapter<AmigoAdapter.ViewHolder> implements Protocolo {

    private List<Usuario> usuariosList;
    private int layout;
    private Activity activity;
    private OnItemClickListener listenerItem;

    private String mensaje;
    private Gson gson;

    public AmigoAdapter(List<Usuario> usuariosList, int layout, Activity activity, OnItemClickListener listenerItem) {
        this.usuariosList = usuariosList;
        this.layout = layout;
        this.activity = activity;
        this.listenerItem = listenerItem;
        this.gson = new Gson();
    }

    @NonNull
    @Override
    public AmigoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(activity).inflate(layout, viewGroup, false);
        AmigoAdapter.ViewHolder viewHolder = new AmigoAdapter.ViewHolder(v);
        return viewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull AmigoAdapter.ViewHolder viewHolder, final int i) {

        final Usuario usuario = usuariosList.get(i);

        viewHolder.containerAmigos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listenerItem.onItemClick(usuario, i);
            }
        });

        switch (usuario.getImagen()) {
            case "avatar1":
                viewHolder.imgVAmigos.setImageResource(R.drawable.avatar1);
                break;
            case "avatar2":
                viewHolder.imgVAmigos.setImageResource(R.drawable.avatar2);
                break;
            case "avatar3":
                viewHolder.imgVAmigos.setImageResource(R.drawable.avatar3);
                break;
            case "avatar4":
                viewHolder.imgVAmigos.setImageResource(R.drawable.avatar4);
                break;
            case "avatar5":
                viewHolder.imgVAmigos.setImageResource(R.drawable.avatar5);
                break;
            case "avatar6":
                viewHolder.imgVAmigos.setImageResource(R.drawable.avatar6);
                break;
            case "avatar7":
                viewHolder.imgVAmigos.setImageResource(R.drawable.avatar7);
                break;
            case "avatar8":
                viewHolder.imgVAmigos.setImageResource(R.drawable.avatar8);
                break;
            case "avatar9":
                viewHolder.imgVAmigos.setImageResource(R.drawable.avatar9);
                break;
            case "avatar10":
                viewHolder.imgVAmigos.setImageResource(R.drawable.avatar10);
                break;
            case "avatar11":
                viewHolder.imgVAmigos.setImageResource(R.drawable.avatar11);
                break;
            case "avatar12":
                viewHolder.imgVAmigos.setImageResource(R.drawable.avatar12);
                break;
            case "avatar13":
                viewHolder.imgVAmigos.setImageResource(R.drawable.avatar13);
                break;
            case "avatar14":
                viewHolder.imgVAmigos.setImageResource(R.drawable.avatar14);
                break;
            case "avatar15":
                viewHolder.imgVAmigos.setImageResource(R.drawable.avatar15);
                break;
            case "avatar16":
                viewHolder.imgVAmigos.setImageResource(R.drawable.avatar16);
                break;
            case "avatar17":
                viewHolder.imgVAmigos.setImageResource(R.drawable.avatar17);
                break;
            case "avatar18":
                viewHolder.imgVAmigos.setImageResource(R.drawable.avatar18);
                break;
            case "avatar19":
                viewHolder.imgVAmigos.setImageResource(R.drawable.avatar19);
                break;
            case "avatar20":
                viewHolder.imgVAmigos.setImageResource(R.drawable.avatar20);
                break;
            case "avatar21":
                viewHolder.imgVAmigos.setImageResource(R.drawable.avatar21);
                break;
            case "avatar22":
                viewHolder.imgVAmigos.setImageResource(R.drawable.avatar22);
                break;
            case "avatar23":
                viewHolder.imgVAmigos.setImageResource(R.drawable.avatar23);
                break;
            case "avatar24":
                viewHolder.imgVAmigos.setImageResource(R.drawable.avatar24);
                break;
            case "avatar25":
                viewHolder.imgVAmigos.setImageResource(R.drawable.avatar25);
                break;
            default:
                viewHolder.imgVAmigos.setImageResource(R.drawable.avatar0);
                break;
        }
        viewHolder.tvAmigosNombre.setText(usuario.getSeudonimo());
        viewHolder.tvAmigosSuscripciones.setText("Esta suscrito a " + obtenerSuscripciones(usuario.getId_usuario()) + " eventos");
    }

    @Override
    public int getItemCount() {
        return usuariosList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout containerAmigos;
        ImageView imgVAmigos;
        TextView tvAmigosNombre;
        TextView tvAmigosSuscripciones;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            containerAmigos = itemView.findViewById(R.id.containerAmigos);
            imgVAmigos = itemView.findViewById(R.id.imgVAmigos);
            tvAmigosNombre = itemView.findViewById(R.id.tvAmigosNombre);
            tvAmigosSuscripciones = itemView.findViewById(R.id.tvAmigosSuscripciones);
        }
    }

    public int obtenerSuscripciones(int id_usuario) {

        int suscripciones = 0;
        try {
            this.mensaje = this.gson.toJson(SUSCRIPCIONES_USUARIO);
            SocketHandler.getSalida().writeUTF(this.mensaje);
            this.mensaje = this.gson.toJson(id_usuario);
            SocketHandler.getSalida().writeUTF(this.mensaje);
            this.mensaje = (String) SocketHandler.getEntrada().readUTF();
            suscripciones = (Integer) this.gson.fromJson(this.mensaje, Integer.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return suscripciones;
    }

    public interface OnItemClickListener {
        void onItemClick(Usuario usuario, int position);
    }
}