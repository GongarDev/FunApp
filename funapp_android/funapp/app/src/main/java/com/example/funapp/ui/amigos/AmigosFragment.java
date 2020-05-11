package com.example.funapp.ui.amigos;

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

public class AmigosFragment extends Fragment {

    private AmigosViewModel amigosViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        amigosViewModel =
                ViewModelProviders.of(this).get(AmigosViewModel.class);
        View root = inflater.inflate(R.layout.fragment_amigos, container, false);
        final TextView textView = root.findViewById(R.id.text_amigos);
        amigosViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
