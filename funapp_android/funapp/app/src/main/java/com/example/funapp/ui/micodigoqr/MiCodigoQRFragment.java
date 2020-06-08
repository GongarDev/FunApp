package com.example.funapp.ui.micodigoqr;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.funapp.R;
import com.example.funapp.activities.MainActivity;
import com.example.funapp.models.Usuario;

public class MiCodigoQRFragment extends Fragment {

    private MiCodigoQRViewModel miCodigoQRViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        miCodigoQRViewModel =
                ViewModelProviders.of(this).get(MiCodigoQRViewModel.class);
        View root = inflater.inflate(R.layout.fragment_micodigoqr, container, false);
        final TextView textView = root.findViewById(R.id.tvMiCodigoQR);
        miCodigoQRViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        final ImageView imageView = root.findViewById(R.id.ivMiCodigoQR);
        Usuario usuario = ((MainActivity) getActivity()).getUsuario();
        miCodigoQRViewModel.getCodigoQR(usuario).observe(getViewLifecycleOwner(), new Observer<Bitmap>() {
            @Override
            public void onChanged(@Nullable Bitmap codeqr) {
                imageView.setImageBitmap(codeqr);
            }
        });
        return root;
    }
}