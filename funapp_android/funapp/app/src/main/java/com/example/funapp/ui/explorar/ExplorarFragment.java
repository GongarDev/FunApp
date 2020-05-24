package com.example.funapp.ui.explorar;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.funapp.R;
import com.example.funapp.activities.MainActivity;
import com.example.funapp.adapters.EventoHistorialAdapter;
import com.example.funapp.adapters.ExplorarAdapter;
import com.example.funapp.models.Evento;
import com.example.funapp.models.Tematica;
import com.example.funapp.models.Usuario;

import java.util.ArrayList;
import java.util.List;

public class ExplorarFragment extends Fragment {

    private ExplorarViewModel explorarViewModel;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ExplorarAdapter adapter;
    private List<Tematica> tematicasList = new ArrayList<>();
    private Usuario usuario;
    private Integer tipoUsuario;
    private OnItemExplorarSelected callback;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        this.usuario = ((MainActivity) getActivity()).getUsuario();

        explorarViewModel =
                ViewModelProviders.of(this).get(ExplorarViewModel.class);
        View root = inflater.inflate(R.layout.fragment_explorar, container, false);
        recyclerView = root.findViewById(R.id.rvExplorar);
        layoutManager = new GridLayoutManager(getActivity(), 2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        recyclerView.addItemDecoration(new DividerItemDecoration(this.getContext(), DividerItemDecoration.HORIZONTAL));
        recyclerView.addItemDecoration(new DividerItemDecoration(this.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(layoutManager);

        explorarViewModel.getTematicas().observe(this, new Observer<List<Tematica>>() {
            @Override
            public void onChanged(List<Tematica> tematicas) {
                if (tematicas != null) {
                    tematicasList = tematicas;
                    adapter = new ExplorarAdapter(tematicasList, R.layout.list_item_explorar, getActivity(), new ExplorarAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(Tematica tematica, int position) {
                            callback.onItemExplorarSelected(tematica);
                        }
                    });
                    recyclerView.setAdapter(adapter);
                }
            }
        });

        return root;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            callback = (OnItemExplorarSelected) context;

        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "Debería implementar el interfaz OnItemExplorarSelected");
        }
    }

    public interface OnItemExplorarSelected {
        public void onItemExplorarSelected(Tematica tematica);
    }
}
