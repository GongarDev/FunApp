package com.example.funapp.ui.historial;

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

public class HistorialFragment extends Fragment {

    private HistorialViewModel historialViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        historialViewModel =
                ViewModelProviders.of(this).get(HistorialViewModel.class);
        View root = inflater.inflate(R.layout.fragment_historial, container, false);
        final TextView textView = root.findViewById(R.id.text_historial);
        historialViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
