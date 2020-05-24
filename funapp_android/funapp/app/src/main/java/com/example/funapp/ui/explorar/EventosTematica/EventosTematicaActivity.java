package com.example.funapp.ui.explorar.EventosTematica;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.funapp.R;
import com.example.funapp.activities.EventoDetallesActivity;
import com.example.funapp.models.Evento;
import com.example.funapp.models.Tematica;
import com.example.funapp.models.Usuario;
import com.example.funapp.util.Protocolo;

public class EventosTematicaActivity  extends AppCompatActivity implements  Protocolo,
        EventosTematicaFragment.OnEventoExplorarSelected {

    private Usuario usuario;
    private Tematica tematica;
    private Integer tipoUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();

        if (intent != null) {
            usuario = (Usuario) intent.getSerializableExtra("usuario");
            tematica = (Tematica) intent.getSerializableExtra("tematica");
            tipoUsuario = (Integer) intent.getSerializableExtra("tipoUsuario");

        }
        setContentView(R.layout.activity_eventos_tematica);
    }

    @Override
    public void OnEventoExplorarSelected(Evento evento) {
        Intent intent = new Intent(this, EventoDetallesActivity.class);
        intent.putExtra("evento", evento);
        intent.putExtra("usuario", usuario);
        intent.putExtra("tipoUsuario", tipoUsuario);
        intent.putExtra("seccion", EXPLORAR_EVENTOS);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public Usuario getUsuario() {
        return usuario;
    }
    public Tematica getTematica() {
        return tematica;
    }
    public Integer getTipoUsuario() { return tipoUsuario; }
}
