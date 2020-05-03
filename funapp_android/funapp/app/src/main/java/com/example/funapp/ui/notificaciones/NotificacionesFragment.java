package com.example.funapp.ui.notificaciones;

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

public class NotificacionesFragment extends Fragment {

    private NotificacionesViewModel notificacionesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificacionesViewModel =
                ViewModelProviders.of(this).get(NotificacionesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notificaciones, container, false);
        final TextView textView = root.findViewById(R.id.text_notificaciones);
        notificacionesViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
