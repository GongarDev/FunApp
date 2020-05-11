package com.example.funapp.ui.miseventos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.funapp.R;
import com.example.funapp.models.Tematica;

import java.util.List;

public class MisEventosFragment extends Fragment {

    private MisEventosViewModel misEventosViewModel;
    private List tematicas;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        misEventosViewModel =
                ViewModelProviders.of(this).get(MisEventosViewModel.class);
        View root = inflater.inflate(R.layout.fragment_miseventos, container, false);

        final TextView textView = root.findViewById(R.id.text_miseventos);

        misEventosViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        misEventosViewModel.getTematicasList().observe(getViewLifecycleOwner(), new Observer<List<Tematica>>() {
            @Override
            public void onChanged(@Nullable List<Tematica> tematicas) {

            }
        });

        return root;
    }

}
