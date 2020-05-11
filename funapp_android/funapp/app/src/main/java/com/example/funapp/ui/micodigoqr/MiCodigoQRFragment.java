package com.example.funapp.ui.micodigoqr;

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

public class MiCodigoQRFragment extends Fragment {

    private MiCodigoQRViewModel miCodigoQRViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        miCodigoQRViewModel =
                ViewModelProviders.of(this).get(MiCodigoQRViewModel.class);
        View root = inflater.inflate(R.layout.fragment_micodigoqr, container, false);
        final TextView textView = root.findViewById(R.id.text_micodigoqr);
        miCodigoQRViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}