package com.example.funapp.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.funapp.R;
import com.example.funapp.models.Evento;
import com.example.funapp.models.Usuario;
import com.example.funapp.util.Protocolo;

public class ValoracionesActivity extends AppCompatActivity implements Protocolo {

    private Evento evento;
    private Usuario usuario;
    private Integer tipoUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        if(intent != null) {
            evento = (Evento) intent.getSerializableExtra("evento");
            usuario = (Usuario) intent.getSerializableExtra("usuario");
            tipoUsuario = (Integer) intent.getSerializableExtra("tipoUsuario");
        }
        setContentView(R.layout.activity_valoraciones);
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Evento getEvento() {
        return evento;
    }

    public Integer getTipoUsuario() {
        return tipoUsuario;
    }
}
