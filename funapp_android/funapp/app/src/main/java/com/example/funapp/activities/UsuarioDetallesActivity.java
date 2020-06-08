package com.example.funapp.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import com.example.funapp.R;
import com.example.funapp.fragments.UsuarioDetallesFragment;
import com.example.funapp.models.Evento;
import com.example.funapp.models.Usuario;
import com.example.funapp.util.Protocolo;

public class UsuarioDetallesActivity extends AppCompatActivity implements Protocolo,
        UsuarioDetallesFragment.OnEventoSelected {

    private Usuario usuario;
    private Usuario usuarioAmigo;
    private Integer tipoUsuario;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_detalles);

        Intent intent = getIntent();

        if (intent != null) {

            usuario = (Usuario) intent.getSerializableExtra("usuario");
            usuarioAmigo = (Usuario) intent.getSerializableExtra("usuarioAmigo");
            tipoUsuario = (Integer) intent.getSerializableExtra("tipoUsuario");

            UsuarioDetallesFragment usuarioDetallesFragment = (UsuarioDetallesFragment)
                    getSupportFragmentManager().findFragmentById(R.id.fragmentMobileUsuarioDetalles);

            usuarioDetallesFragment.mostrarUsuario(usuarioAmigo, usuario);
        }
    }

    @Override
    public void OnEventoSelected(Evento evento) {
        Intent intent = new Intent(this, EventoDetallesActivity.class);
        intent.putExtra("evento", evento);
        intent.putExtra("usuario", usuario);
        intent.putExtra("tipoUsuario", tipoUsuario);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        finish();
    }
}