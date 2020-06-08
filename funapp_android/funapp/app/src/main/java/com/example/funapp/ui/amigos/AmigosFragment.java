package com.example.funapp.ui.amigos;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.funapp.R;
import com.example.funapp.activities.MainActivity;
import com.example.funapp.adapters.AmigoAdapter;
import com.example.funapp.adapters.EventoHistorialAdapter;
import com.example.funapp.models.Evento;
import com.example.funapp.models.Usuario;
import com.example.funapp.ui.historial.HistorialFragment;
import com.example.funapp.ui.historial.HistorialViewModel;
import com.example.funapp.util.Protocolo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.integration.android.IntentIntegrator;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class AmigosFragment extends Fragment implements Protocolo {

    private AmigosViewModel amigosViewModel;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private AmigoAdapter amigoAdapter;
    private List<Usuario> usuariosList = new ArrayList<>();
    private Usuario usuario;
    private Integer tipoUsuario;
    private ProgressBar progressBar;
    private OnAmigoSelected callback;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        this.usuario = ((MainActivity) getActivity()).getUsuario();
        this.tipoUsuario = ((MainActivity) getActivity()).getTipoUsuario();
        amigosViewModel =
                ViewModelProviders.of(this).get(AmigosViewModel.class);
        View root = inflater.inflate(R.layout.fragment_amigos, container, false);
        recyclerView = root.findViewById(R.id.rvAmigos);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        progressBar = root.findViewById(R.id.pbAmigos);
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getContext().getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = info != null && info.isConnected();

        FloatingActionButton fab = (FloatingActionButton) root.findViewById(R.id.fabAmigos);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator integrator = new IntentIntegrator(getActivity());
                integrator.initiateScan();
            }
        });

        if (isConnected) {
            amigosViewModel.getAmigos(usuario.getId_usuario()).observe(this, new Observer<List<Usuario>>() {
                @Override
                public void onChanged(List<Usuario> usuarios) {
                    progressBar.setVisibility(View.GONE);
                    if (usuarios != null) {
                        usuariosList = usuarios;
                        amigoAdapter = new AmigoAdapter(usuariosList, R.layout.list_item_amigos, getActivity(), new AmigoAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(Usuario usuario, int position) {
                                callback.OnAmigoSelected(usuario);
                            }
                        });
                        recyclerView.setAdapter(amigoAdapter);
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
            callback = (OnAmigoSelected) context;

        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "Debería implementar el interfaz OnAmigoSelected");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        amigosViewModel.cargarAmigos(usuario.getId_usuario());
    }

    public interface OnAmigoSelected {
        public void OnAmigoSelected(Usuario usuario);
    }
}