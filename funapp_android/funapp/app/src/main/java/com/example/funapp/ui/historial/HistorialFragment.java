package com.example.funapp.ui.historial;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.funapp.R;
import com.example.funapp.activities.MainActivity;
import com.example.funapp.adapters.EventoHistorialAdapter;
import com.example.funapp.models.Evento;
import com.example.funapp.models.Usuario;
import com.example.funapp.util.Protocolo;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class HistorialFragment extends Fragment implements Protocolo {

    private HistorialViewModel historialViewModel;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private EventoHistorialAdapter eventoHistorialAdapter;
    private List<Evento> eventosList = new ArrayList<>();
    private Usuario usuario;
    private Integer tipoUsuario;
    private ProgressBar progressBar;
    private OnEventoHistorialSelected callback;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        this.usuario = ((MainActivity) getActivity()).getUsuario();
        this.tipoUsuario = ((MainActivity) getActivity()).getTipoUsuario();
        historialViewModel =
                ViewModelProviders.of(this).get(HistorialViewModel.class);
        View root = inflater.inflate(R.layout.fragment_historial, container, false);
        recyclerView = root.findViewById(R.id.rvEventosHistorial);
        layoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        final TextView tvEventosHistorial = root.findViewById(R.id.tvEventosHistorial);
        if(tipoUsuario==SESION_ABIERTA_RESPONSABLE){
            tvEventosHistorial.setText("Eventos organizados");
        }else if(tipoUsuario==SESION_ABIERTA_ESTANDAR){
            tvEventosHistorial.setText("Eventos asistidos");
        }

        progressBar = root.findViewById(R.id.pbHistorial);

        ConnectivityManager connectivityManager = (ConnectivityManager) this.getContext().getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = info != null && info.isConnected();

        if (isConnected) {

            historialViewModel.getEventos(usuario.getId_usuario()).observe(this, new Observer<List<Evento>>() {
                @Override
                public void onChanged(List<Evento> eventos) {

                    progressBar.setVisibility(View.GONE);
                    if (eventos != null) {
                        eventosList = eventos;

                        eventoHistorialAdapter = new EventoHistorialAdapter(eventosList, R.layout.list_item_historial_eventos, getActivity(), new EventoHistorialAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(Evento evento, int position) {
                                callback.OnEventoHistorialSelected(evento);
                            }
                        });
                        recyclerView.setAdapter(eventoHistorialAdapter);
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
            callback = (OnEventoHistorialSelected) context;

        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "Debería implementar el interfaz OnEventoHistorialSelected");
        }
    }

    public interface OnEventoHistorialSelected {
        public void OnEventoHistorialSelected(Evento evento);
    }
}
