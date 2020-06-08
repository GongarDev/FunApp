package com.example.funapp.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.funapp.R;
import com.example.funapp.activities.ValoracionesActivity;
import com.example.funapp.adapters.ValoracionAdapter;
import com.example.funapp.models.Evento;
import com.example.funapp.models.Usuario;
import com.example.funapp.models.Valoracion;
import com.example.funapp.util.Protocolo;
import com.example.funapp.viewModels.ValoracionesViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class ValoracionesFragment extends Fragment implements Protocolo {

    private ValoracionesViewModel valoracionesViewModel;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<Valoracion> valoracionesList = new ArrayList<>();
    private ValoracionAdapter adapter;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private Usuario usuario;
    private Integer tipoUsuario;
    private Evento evento;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("RestrictedApi")
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_valoraciones, container, false);

        valoracionesViewModel = ViewModelProviders.of(this).get(ValoracionesViewModel.class);
        this.usuario = ((ValoracionesActivity) getActivity()).getUsuario();
        this.tipoUsuario = ((ValoracionesActivity) getActivity()).getTipoUsuario();
        this.evento = ((ValoracionesActivity) getActivity()).getEvento();
        recyclerView = view.findViewById(R.id.rvValoraciones);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fbValoraciones);
        if (this.tipoUsuario == SESION_ABIERTA_ESTANDAR) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean existe = false;
                    for (Valoracion v : valoracionesList) {
                        if (v.getUsuario().getId_usuario() == usuario.getId_usuario()) {
                            Snackbar.make(view, "Ya valoraste este evento", Snackbar.LENGTH_LONG)
                                    .setAction("Cerrar", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                        }
                                    })
                                    .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                    .show();
                            existe = true;
                            break;
                        }
                    }
                    if (!existe) {
                        createPopUp();
                    }
                }

            });
        } else if (this.tipoUsuario == SESION_ABIERTA_RESPONSABLE) {
            fab.setVisibility(View.GONE);
        }

        ConnectivityManager connectivityManager = (ConnectivityManager) this.getContext().getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = info != null && info.isConnected();
        if (isConnected) {
            valoracionesViewModel.getValoracionesList(this.evento.getId_evento()).observe(this, new Observer<List<Valoracion>>() {
                @Override
                public void onChanged(List<Valoracion> valoraciones) {

                    if (valoraciones != null) {
                        valoracionesList = valoraciones;
                        adapter = new ValoracionAdapter(valoracionesList, R.layout.list_item_valoraciones, getActivity());
                        recyclerView.setAdapter(adapter);
                    }
                }
            });
        }
        return view;
    }

    private void createPopUp() {

        builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.popup_valoraciones, null);
        builder.setView(view);
        dialog = builder.create();
        dialog.show();
        final EditText etMensajePublicacion = view.findViewById(R.id.etMensajeValoracion);
        final RatingBar ratingBarValoracion = view.findViewById(R.id.ratingBarValoracionesInsertar);

        Button bAceptar = view.findViewById(R.id.bAceptarValoracion);
        bAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!TextUtils.isEmpty(etMensajePublicacion.getText())) {
                    Usuario usuario = new Usuario();
                    usuario.setId_usuario(getId_usuario());
                    Evento evento = new Evento();
                    evento.setId_evento(getId_evento());
                    Valoracion valoracion = new Valoracion(etMensajePublicacion.getText().toString(),
                            ratingBarValoracion.getNumStars(), usuario, evento);
                    valoracionesViewModel.insertarValoracion(valoracion);
                    dialog.dismiss();
                    valoracionesViewModel.cargarValoraciones(getId_evento());
                }
            }
        });
    }

    public int getId_usuario() {
        return usuario.getId_usuario();
    }

    public int getId_evento() {
        return evento.getId_evento();
    }
}
