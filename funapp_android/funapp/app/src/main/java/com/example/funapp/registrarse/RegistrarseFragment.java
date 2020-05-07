package com.example.funapp.registrarse;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import androidx.fragment.app.Fragment;

import com.example.funapp.R;

public class RegistrarseFragment extends Fragment {

    EditText etSeudonimo;
    EditText etContrasenia;
    EditText etConfirmarContrasenia;
    EditText etFechaNacimiento;
    Switch switchResponsable;
    Button bRegistrarse;

    public RegistrarseFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_registrarse, container, false);

        etSeudonimo = view.findViewById(R.id.etSeudonimo);
        etContrasenia = view.findViewById(R.id.etContrasenia);
        etConfirmarContrasenia = view.findViewById(R.id.etConfirmarContrasenia);
        etFechaNacimiento = view.findViewById(R.id.etFechaNacimiento);
        switchResponsable = view.findViewById(R.id.switchResponsable);
        bRegistrarse = view.findViewById(R.id.bRegistrarse);

        return view;
    }
}
