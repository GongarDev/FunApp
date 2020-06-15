package com.example.funapp.ui.notificaciones;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.funapp.R;
import com.example.funapp.activities.MainActivity;
import com.example.funapp.adapters.NotificacionesAdapter;
import com.example.funapp.models.Evento;
import com.example.funapp.models.Usuario;
import com.example.funapp.util.Protocolo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class NotificacionesFragment extends Fragment implements Protocolo {

    private NotificacionesViewModel notificacionesViewModel;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<Evento> eventosList = new ArrayList<>();
    private NotificacionesAdapter adapter;
    private Usuario usuario;
    private Integer tipoUsuario;
    private OnEventoSelected callback;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_notificaciones, container, false);
        notificacionesViewModel =
                ViewModelProviders.of(this).get(NotificacionesViewModel.class);
        this.usuario = ((MainActivity) getActivity()).getUsuario();
        this.tipoUsuario = ((MainActivity) getActivity()).getTipoUsuario();
        recyclerView = root.findViewById(R.id.rvNotificaciones);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        ConnectivityManager connectivityManager = (ConnectivityManager) this.getContext().getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = info != null && info.isConnected();
        if (isConnected) {
            notificacionesViewModel.getEventosList(this.usuario.getId_usuario(),this.tipoUsuario).observe(this, new Observer<List<Evento>>() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onChanged(List<Evento> eventos) {
                    if (eventos != null) {
                        eventosList = new ArrayList<>();
                        for(Evento evento : eventos) {
                            String fechaEvento = evento.getFecha_evento_LocalDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

                            if (tipoUsuario==SESION_ABIERTA_ESTANDAR && (!evento.isActivo() ||
                                    fechaEvento.equals(LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))) ||
                                    fechaEvento.equals(LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))) ||
                                    fechaEvento.equals(LocalDate.now().plusDays(2).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))) ||
                                    fechaEvento.equals(LocalDate.now().plusDays(7).format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                            )) {
                                eventosList.add(evento);
                            }else if(tipoUsuario==SESION_ABIERTA_RESPONSABLE && (
                                    fechaEvento.equals(LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))) ||
                                    fechaEvento.equals(LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))) ||
                                    fechaEvento.equals(LocalDate.now().plusDays(2).format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))) ||
                                    fechaEvento.equals(LocalDate.now().plusDays(7).format(DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                            )){
                                eventosList.add(evento);
                            }
                        }
                        adapter = new NotificacionesAdapter(eventosList, R.layout.list_item_notificaciones, getActivity(), new NotificacionesAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(Evento evento, int position) {
                                callback.OnEventoSelected(evento);
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

    public interface OnEventoSelected {
        public void OnEventoSelected(Evento evento);
    }
}
