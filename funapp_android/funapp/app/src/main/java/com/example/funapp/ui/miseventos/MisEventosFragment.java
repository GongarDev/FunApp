package com.example.funapp.ui.miseventos;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.funapp.R;
import com.example.funapp.activities.MainActivity;
import com.example.funapp.adapters.EventoActivoAdapter;
import com.example.funapp.adapters.EventoSuspendidoAdapter;
import com.example.funapp.models.Evento;
import com.example.funapp.models.Usuario;
import com.example.funapp.ui.miseventos.crear_editar_evento.CrearEditarEventoActivity;
import com.example.funapp.util.Protocolo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class MisEventosFragment extends Fragment implements Protocolo {

    private MisEventosViewModel misEventosViewModel;
    private RecyclerView recyclerViewActivos;
    private RecyclerView recyclerViewSuspendidos;
    private RecyclerView.LayoutManager layoutManagerActivos;
    private RecyclerView.LayoutManager layoutManagerSuspendidos;
    private EventoActivoAdapter eventoActivoAdapter;
    private EventoSuspendidoAdapter eventoSuspendidoAdapter;
    private List<Evento> eventosList = new ArrayList<>();
    private List<Evento> eventosActivosList = new ArrayList<>();
    private List<Evento> eventosSuspedidosList = new ArrayList<>();
    private OnItemMisEventosSelected callback;
    private Usuario usuario;
    private ProgressBar progressBar;

    public MisEventosFragment() {
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        this.usuario = ((MainActivity) getActivity()).getUsuario();

        misEventosViewModel =
                ViewModelProviders.of(this).get(MisEventosViewModel.class);
        View root = inflater.inflate(R.layout.fragment_mis_eventos, container, false);

        recyclerViewActivos = root.findViewById(R.id.rvEventosActivos);
        recyclerViewSuspendidos = root.findViewById(R.id.rvEventosSuspendidos);
        layoutManagerActivos = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        layoutManagerSuspendidos = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        //layoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerViewActivos.setLayoutManager(layoutManagerActivos);
        recyclerViewActivos.setHasFixedSize(true);

        recyclerViewActivos.setItemViewCacheSize(20);
        recyclerViewActivos.setDrawingCacheEnabled(true);
        recyclerViewActivos.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        recyclerViewSuspendidos.setLayoutManager(layoutManagerSuspendidos);
        recyclerViewSuspendidos.setHasFixedSize(true);

        recyclerViewSuspendidos.setItemViewCacheSize(20);
        recyclerViewSuspendidos.setDrawingCacheEnabled(true);
        recyclerViewSuspendidos.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        progressBar = root.findViewById(R.id.pbMisEventos);

        FloatingActionButton fab = (FloatingActionButton) root.findViewById(R.id.fbAddEvento);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer estadoSesion = misEventosViewModel.existeEntidadUsuario(usuario.getId_usuario());
                if (estadoSesion == NO_EXISTE) {
                    Snackbar.make(view, "Tienes que completar los datos de perfil para poder crear eventos.", Snackbar.LENGTH_LONG)
                            .setAction("Cerrar", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                }
                            })
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();
                } else if (estadoSesion == EXISTE) {
                    Intent intent = new Intent(getContext(), CrearEditarEventoActivity.class);
                    intent.putExtra("usuario", usuario);
                    intent.putExtra("accion", INSERTAR_EVENTO);
                    if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                        startActivity(intent);
                    }
                }
            }
        });

        ConnectivityManager connectivityManager = (ConnectivityManager) this.getContext().getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = info != null && info.isConnected();

        if (isConnected) {

            misEventosViewModel.getEventos(usuario.getId_usuario()).observe(this, new Observer<List<Evento>>() {
                @Override
                public void onChanged(List<Evento> eventos) {

                    progressBar.setVisibility(View.GONE);

                    if (eventos != null) {
                        eventosList = eventos;
                        eventosActivosList = new ArrayList<>();
                        eventosSuspedidosList = new ArrayList<>();

                        for (Evento e : eventosList) {
                            if (e.isActivo()) {
                                eventosActivosList.add(e);
                            } else
                                eventosSuspedidosList.add(e);
                        }

                        eventoActivoAdapter = new EventoActivoAdapter(eventosActivosList, R.layout.list_item_eventos_activos, getActivity(), new EventoActivoAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(Evento evento, int position) {
                                callback.OnItemMisEventosSelected(evento);
                            }
                        }, new EventoActivoAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(Evento evento, int position) {
                                Intent intent = new Intent(getContext(), CrearEditarEventoActivity.class);
                                intent.putExtra("usuario", usuario);
                                intent.putExtra("evento", evento);
                                intent.putExtra("accion", ACTUALIZAR_EVENTO);

                                if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                                    startActivity(intent);
                                }
                            }
                        }, new EventoActivoAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(Evento evento, int position) {
                                if (evento.isActivo()) {
                                    evento.setActivo(false);
                                } else {
                                    evento.setActivo(true);
                                }
                                misEventosViewModel.activarEvento(evento);
                                misEventosViewModel.cargarEventos(usuario.getId_usuario());
                            }
                        });

                        recyclerViewActivos.setAdapter(eventoActivoAdapter);

                        eventoSuspendidoAdapter = new EventoSuspendidoAdapter(eventosSuspedidosList, R.layout.list_item_eventos_suspendido, getActivity(), new EventoSuspendidoAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(Evento evento, int position) {
                                callback.OnItemMisEventosSelected(evento);
                            }
                        }, new EventoSuspendidoAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(Evento evento, int position) {
                                Intent intent = new Intent(getContext(), CrearEditarEventoActivity.class);
                                intent.putExtra("usuario", usuario);
                                intent.putExtra("evento", evento);
                                intent.putExtra("accion", ACTUALIZAR_EVENTO);
                                if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                                    startActivity(intent);
                                }
                            }
                        }, new EventoSuspendidoAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(Evento evento, int position) {
                                if (evento.isActivo()) {
                                    evento.setActivo(false);
                                } else {
                                    evento.setActivo(true);
                                }
                                misEventosViewModel.activarEvento(evento);
                                misEventosViewModel.cargarEventos(usuario.getId_usuario());
                            }
                        });
                        recyclerViewSuspendidos.setAdapter(eventoSuspendidoAdapter);
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
            callback = (OnItemMisEventosSelected) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "Debería implementar el interfaz OnEventoSelected");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        misEventosViewModel.cargarEventos(usuario.getId_usuario());
    }

    public interface OnItemMisEventosSelected {
        public void OnItemMisEventosSelected(Evento evento);
    }

    public Usuario getUsuario() {
        return this.usuario;
    }
}
