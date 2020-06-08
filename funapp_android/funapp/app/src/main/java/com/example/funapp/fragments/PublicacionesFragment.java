package com.example.funapp.fragments;

import android.annotation.SuppressLint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.funapp.R;
import com.example.funapp.activities.PublicacionesActivity;
import com.example.funapp.adapters.PublicacionAdapter;
import com.example.funapp.models.Evento;
import com.example.funapp.models.Publicacion;
import com.example.funapp.models.Usuario;
import com.example.funapp.util.Protocolo;
import com.example.funapp.viewModels.PublicacionesViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class PublicacionesFragment extends Fragment implements Protocolo {

    private PublicacionesViewModel publicacionesViewModel;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<Publicacion> publicacionesList = new ArrayList<>();
    private PublicacionAdapter adapter;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private Usuario usuario;
    private Integer tipoUsuario;
    private Evento evento;

    @SuppressLint("RestrictedApi")
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_publicaciones, container, false);

        publicacionesViewModel = ViewModelProviders.of(this).get(PublicacionesViewModel.class);
        this.usuario = ((PublicacionesActivity) getActivity()).getUsuario();
        this.tipoUsuario = ((PublicacionesActivity) getActivity()).getTipoUsuario();
        this.evento = ((PublicacionesActivity) getActivity()).getEvento();
        recyclerView = view.findViewById(R.id.rvPublicaciones);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fbPublicaciones);
        if(this.tipoUsuario == SESION_ABIERTA_RESPONSABLE &&
                evento.getUsuario().getId_usuario()==usuario.getId_usuario()) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    createPopUp();
                }
            });
        }else if(this.tipoUsuario == SESION_ABIERTA_ESTANDAR){
            fab.setVisibility(View.GONE);
        }else if(this.tipoUsuario == SESION_ABIERTA_RESPONSABLE){
            fab.setVisibility(View.GONE);
        }

        ConnectivityManager connectivityManager = (ConnectivityManager) this.getContext().getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = info != null && info.isConnected();
        if (isConnected) {
            publicacionesViewModel.getPublicacionesList(this.evento.getId_evento()).observe(this, new Observer<List<Publicacion>>() {
                @Override
                public void onChanged(List<Publicacion> publicaciones) {

                    if (publicaciones != null) {
                        publicacionesList = publicaciones;
                        adapter = new PublicacionAdapter(publicacionesList, R.layout.list_item_publicaciones, getActivity());
                        recyclerView.setAdapter(adapter);
                    }
                }
            });
        }
        return view;
    }

    private void createPopUp() {

        builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.popup_publicaciones, null);
        builder.setView(view);
        dialog = builder.create();
        dialog.show();
        final EditText etMensajePublicacion = view.findViewById(R.id.etMensajePublicacion);
        Button bAceptar = view.findViewById(R.id.bAceptarPublicacion);
        bAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!TextUtils.isEmpty(etMensajePublicacion.getText())) {
                    Publicacion publicacion = new Publicacion(etMensajePublicacion.getText().toString());
                    publicacionesViewModel.insertarPublicacion(publicacion, evento.getId_evento(), usuario.getId_usuario());
                    dialog.dismiss();
                    publicacionesViewModel.cargarPublicaciones(getId_evento());
                }
            }
        });
    }

    public int getId_evento(){
        return evento.getId_evento();
    }
}
