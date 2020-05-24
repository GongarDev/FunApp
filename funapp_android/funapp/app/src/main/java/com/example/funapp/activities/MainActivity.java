package com.example.funapp.activities;

import android.content.Intent;
import android.os.Bundle;

import com.example.funapp.R;
import com.example.funapp.adapters.ExplorarAdapter;
import com.example.funapp.models.Evento;
import com.example.funapp.models.Tematica;
import com.example.funapp.models.Usuario;
import com.example.funapp.ui.explorar.EventosTematica.EventosTematicaActivity;
import com.example.funapp.ui.explorar.EventosTematica.EventosTematicaFragment;
import com.example.funapp.ui.explorar.ExplorarFragment;
import com.example.funapp.ui.historial.HistorialFragment;
import com.example.funapp.ui.mapa.MapaFragment;
import com.example.funapp.ui.miseventos.MisEventosFragment;
import com.example.funapp.util.Protocolo;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity implements Protocolo, MisEventosFragment.OnEventoSelected,
        MapaFragment.OnEventoMapaSelected, HistorialFragment.OnEventoHistorialSelected,
        ExplorarFragment.OnItemExplorarSelected {

    private AppBarConfiguration appBarConfiguration;
    private NavController navController;
    private DrawerLayout drawer;
    private Usuario usuario;
    private Integer tipoUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        if (intent != null) {
            usuario = (Usuario) intent.getSerializableExtra("usuario");
            tipoUsuario = (Integer) intent.getSerializableExtra("tipoUsuario");
        }

        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        NavigationView navView = findViewById(R.id.main_navigation_view);
        BottomNavigationView bottomNavigationView = findViewById(R.id.main_bottom_navigation_view);
        drawer = findViewById(R.id.main_drawer_layout);
        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_inicio, R.id.navigation_notificaciones, R.id.navigation_explorar, R.id.navigation_suscripciones,
                R.id.navigation_mapa, R.id.navigation_micodigoqr, R.id.navigation_amigos, R.id.navigation_historial,
                R.id.navigation_cuenta, R.id.navigation_mis_eventos, R.id.navigation_cerrarsesion).setDrawerLayout(drawer).build();

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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
    }

    @Override
    public void OnEventoMapaSelected(Evento evento) {
        Intent intent = new Intent(this, EventoDetallesActivity.class);
        intent.putExtra("evento", evento);
        intent.putExtra("usuario", usuario);
        intent.putExtra("tipoUsuario", tipoUsuario);
        intent.putExtra("seccion", MAPA_EVENTOS);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    @Override
    public void onItemExplorarSelected(Tematica tematica) {
        Intent intent = new Intent(this, EventosTematicaActivity.class);
        intent.putExtra("tematica", tematica);
        intent.putExtra("usuario", usuario);
        intent.putExtra("tipoUsuario", tipoUsuario);


        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    @Override
    public void OnEventoHistorialSelected(Evento evento) {
        Intent intent = new Intent(this, EventoDetallesActivity.class);
        intent.putExtra("evento", evento);
        intent.putExtra("usuario", usuario);
        intent.putExtra("tipoUsuario", tipoUsuario);
        intent.putExtra("seccion", HISTORIAL_EVENTOS);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public Usuario getUsuario(){
        return this.usuario;
    }

    public Integer getTipoUsuario(){
        return this.tipoUsuario;
    }
}
