package com.example.funapp.activities;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.funapp.R;
import com.example.funapp.fragments.EventoDetallesFragment;
import com.example.funapp.models.Evento;
import com.example.funapp.models.Ubicacion;
import com.example.funapp.models.Usuario;
import com.example.funapp.util.Protocolo;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static com.example.funapp.util.SocketHandler.getContext;

public class EventoDetallesActivity extends AppCompatActivity implements OnMapReadyCallback, Protocolo,
        EventoDetallesFragment.OnPublicacionSelected{

    private Evento evento;
    private Usuario usuario;
    private Integer tipoUsuario;
    private Integer seccion;
    Location mLastKnownLocation;
    GoogleMap mMap;

    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evento_detalles);

        Intent intent = getIntent();

        if(intent != null) {

            evento = (Evento) intent.getSerializableExtra("evento");
            usuario = (Usuario) intent.getSerializableExtra("usuario");
            tipoUsuario = (Integer) intent.getSerializableExtra("tipoUsuario");
            seccion = (Integer) intent.getSerializableExtra("seccion");
            EventoDetallesFragment eventoDetallesFragment = (EventoDetallesFragment)
                    getSupportFragmentManager().findFragmentById(R.id.fragmentMobileEventoDetalles);

            eventoDetallesFragment.mostrarEvento(evento, usuario, tipoUsuario, seccion);
        }

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapa);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap map) {

        mMap = map;

        MarkerOptions markerOptions = new MarkerOptions();
        if(evento.getUbicacionesList()!=null && !evento.getUbicacionesList().isEmpty()) {
            Ubicacion ubicacion = evento.getUbicacionesList().get(0);
            if(ubicacion.getLatitud()==0.0 || ubicacion.getLatitud()==0){
                List<Address> addresses = null;
                Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
                try {
                    addresses = geocoder.getFromLocationName(ubicacion.getCalle(), 20);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                ubicacion.setLatitud(addresses.get(0).getLatitude());
                ubicacion.setLongitud(addresses.get(0).getLongitude());
            }
            markerOptions.position(new LatLng(ubicacion.getLatitud(), ubicacion.getLongitud()));
            markerOptions.title(ubicacion.getCalle());
            mMap.animateCamera(CameraUpdateFactory.newLatLng(
                    new LatLng(ubicacion.getLatitud(), ubicacion.getLongitud())));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(ubicacion.getLatitud(), ubicacion.getLongitud()), 15));
            mMap.addMarker(markerOptions);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (mMap != null) {
            outState.putParcelable(KEY_CAMERA_POSITION, mMap.getCameraPosition());
            outState.putParcelable(KEY_LOCATION, mLastKnownLocation);
            super.onSaveInstanceState(outState);
        }
    }

    @Override
    public void OnPublicacionSelected(Evento evento) {
        Intent intent= new Intent(this, PublicacionesActivity.class);
        intent.putExtra("evento", this.evento);
        intent.putExtra("usuario", usuario);
        intent.putExtra("tipoUsuario", tipoUsuario);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        finish();
    }
}