package com.example.funapp.login.registrarse;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

import com.example.funapp.R;

public class RegistrarseFragment extends Fragment {

    ImageButton retroceder;

    public RegistrarseFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_registrarse, container, false);



        return view;
    }
}
