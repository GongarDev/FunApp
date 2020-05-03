package com.example.funapp.ui.suscripciones;

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

public class SuscripcionesFragment extends Fragment {

    private SuscripcionesViewModel suscripcionesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        suscripcionesViewModel =
                ViewModelProviders.of(this).get(SuscripcionesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_suscripciones, container, false);
        final TextView textView = root.findViewById(R.id.text_suscripciones);
        suscripcionesViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
