package com.example.funapp.ui.suscripciones;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

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
import com.example.funapp.models.Usuario;
import com.example.funapp.ui.miseventos.MisEventosFragment;
import com.example.funapp.ui.miseventos.MisEventosViewModel;
import com.example.funapp.ui.miseventos.crear_editar_evento.CrearEditarEventoActivity;
import com.example.funapp.util.Protocolo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
                /*Intent intent = new Intent(getContext(), CrearEditarEventoActivity.class);
                intent.putExtra("usuario", usuario);
                intent.putExtra("accion", INSERTAR_EVENTO);
                if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(intent);
                }*/
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
                                suscripcionesViewModel.desuscribirseEvento(evento.getId_evento(), usuario.getId_usuario());
                            }
                        });
                        recyclerView.setAdapter(adapter);
                    }
                }
            });
        }
        return root;
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