package com.example.funapp.ui.suscripciones;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.funapp.R;
import com.example.funapp.activities.MainActivity;
import com.example.funapp.adapters.EventoActivoAdapter;
import com.example.funapp.adapters.EventoSuscritoAdapter;
import com.example.funapp.adapters.EventoSuspendidoAdapter;
import com.example.funapp.models.Evento;
import com.example.funapp.models.Incidencia;
import com.example.funapp.models.Usuario;
import com.example.funapp.ui.miseventos.MisEventosFragment;
import com.example.funapp.ui.miseventos.MisEventosViewModel;
import com.example.funapp.ui.miseventos.crear_editar_evento.CrearEditarEventoActivity;
import com.example.funapp.util.Protocolo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.CONNECTIVITY_SERVICE;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class SuscripcionesFragment extends Fragment implements Protocolo {

    private SuscripcionesViewModel suscripcionesViewModel;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private EventoSuscritoAdapter adapter;
    private List<Evento> eventosList = new ArrayList<>();
    private OnEventoSelected callback;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private Usuario usuario;
    private Integer estadoSesion;
    private ProgressBar progressBar;

    public SuscripcionesFragment() {
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        this.usuario = ((MainActivity) getActivity()).getUsuario();

        suscripcionesViewModel =
                ViewModelProviders.of(this).get(SuscripcionesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_suscripciones, container, false);

        recyclerView = root.findViewById(R.id.rvSuscripciones);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        progressBar = root.findViewById(R.id.pbSuscripciones);

        FloatingActionButton fab = (FloatingActionButton) root.findViewById(R.id.fbSuscritosQR);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator integrator = new IntentIntegrator(getActivity());
                integrator.initiateScan();
            }
        });

        ConnectivityManager connectivityManager = (ConnectivityManager) this.getContext().getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = info != null && info.isConnected();

        if (isConnected) {

            suscripcionesViewModel.getEventosSuscritos(usuario.getId_usuario()).observe(this, new Observer<List<Evento>>() {
                @Override
                public void onChanged(List<Evento> eventos) {

                    progressBar.setVisibility(View.GONE);

                    if (eventos != null) {
                        eventosList = eventos;

                        adapter = new EventoSuscritoAdapter(eventosList, R.layout.list_item_eventos_suscritos, getActivity(), new EventoSuscritoAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(Evento evento, int position) {
                                callback.OnEventoSelected(evento);
                            }
                        }, new EventoSuscritoAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(Evento evento, int position) {
                                popupDesuscribirse(evento.getId_evento());
                            }
                        });
                        recyclerView.setAdapter(adapter);
                    }
                }
            });
        }
        return root;
    }

    private void popupDesuscribirse(int id_evento) {

        builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.popup_confirmacion, null);
        builder.setView(view);
        dialog = builder.create();
        dialog.show();
        TextView tvDesuscribirse = view.findViewById(R.id.tvPopupConfirmacion);
        tvDesuscribirse.setText("¿Estás seguro que quieres desuscribirte?");
        Button bEnviar = view.findViewById(R.id.bPopupConfirmar);
        Button bCancelar = view.findViewById(R.id.bPopupCancelar);

        bEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    estadoSesion = suscripcionesViewModel.desuscribirseEvento(id_evento, usuario.getId_usuario());
                    if (estadoSesion == ACTUALIZAR_EXITO) {
                        suscripcionesViewModel.cargarEventosSuscritos(usuario.getId_usuario());
                        Snackbar.make(view, "Se ha desuscrito del evento.", Snackbar.LENGTH_LONG)
                                .setAction("Cerrar", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                    }
                                })
                                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                .show();
                        dialog.dismiss();
                    }

            }
        });
        bCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            callback = (OnEventoSelected) context;

        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "Debería implementar el interfaz OnEventoSelected");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public interface OnEventoSelected {
        public void OnEventoSelected(Evento evento);
    }

    public Usuario getUsuario() {
        return this.usuario;
    }
}