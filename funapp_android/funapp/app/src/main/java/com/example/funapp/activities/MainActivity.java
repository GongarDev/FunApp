package com.example.funapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.example.funapp.R;
import com.example.funapp.models.Usuario;
import com.example.funapp.ui.miseventos.MisEventosFragment;
import com.example.funapp.util.SocketHandler;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity   {

    private AppBarConfiguration appBarConfiguration;
    private NavController navController;
    private DrawerLayout drawer;
    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        drawer = findViewById(R.id.main_drawer_layout);
        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_inicio, R.id.navigation_notificaciones, R.id.navigation_explorar, R.id.navigation_suscripciones,
                R.id.navigation_mapa, R.id.navigation_micodigoqr, R.id.navigation_amigos, R.id.navigation_historial,
                R.id.navigation_cuenta, R.id.navigation_mis_eventos, R.id.navigation_cerrarsesion).setDrawerLayout(drawer).build();

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        Intent intent = getIntent();

        if(intent != null) {

            usuario = (Usuario) intent.getSerializableExtra("usuario");

        }

        Log.v("ip socket", SocketHandler.getSocket().toString());
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
}
