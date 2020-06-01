package com.example.funapp.ui.inicio;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.funapp.R;
import com.example.funapp.activities.MainActivity;
import com.example.funapp.adapters.EventoActivoAdapter;
import com.example.funapp.adapters.EventoInicioAdapter;
import com.example.funapp.adapters.EventoSuspendidoAdapter;
import com.example.funapp.adapters.EventoTematicaAdapter;
import com.example.funapp.models.Evento;
import com.example.funapp.models.Ubicacion;
import com.example.funapp.models.Usuario;
import com.example.funapp.ui.explorar.EventosTematica.EventosTematicaActivity;
import com.example.funapp.ui.miseventos.MisEventosFragment;
import com.example.funapp.ui.miseventos.crear_editar_evento.CrearEditarEventoActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class InicioFragment extends Fragment {

    private InicioViewModel inicioViewModel;
    private RecyclerView recyclerViewEventosProximos;
    private RecyclerView recyclerViewEventosRecomendados;
    private RecyclerView.LayoutManager layoutManagerProximos;
    private RecyclerView.LayoutManager layoutManagerRecomendados;
    private EventoInicioAdapter adapterProximos;
    private EventoInicioAdapter adapterRecomendados;
    private List<Evento> eventosProximosList = new ArrayList<>();
    private List<Evento> eventosRecomendadosList = new ArrayList<>();
    private OnEventoSelected callback;
    private Usuario usuario;
    private Integer estadoSesion;
    private ProgressBar progressBar;

    GeoDataClient mGeoDataClient;
    PlaceDetectionClient mPlaceDetectionClient;
    FusedLocationProviderClient mFusedLocationProviderClient;
    boolean mLocationPermissionGranted;
    Location mLastKnownLocation;
    LatLng mDefaultLocation;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        this.usuario = ((MainActivity) getActivity()).getUsuario();

        inicioViewModel =
                ViewModelProviders.of(this).get(InicioViewModel.class);
        View root = inflater.inflate(R.layout.fragment_inicio, container, false);

        mDefaultLocation = new LatLng(40.4165001, -3.7025599); //Madrid
        mGeoDataClient = Places.getGeoDataClient(getActivity(), null);
        mPlaceDetectionClient = Places.getPlaceDetectionClient(getActivity(), null);
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());

        recyclerViewEventosProximos = root.findViewById(R.id.rvInicio);
        recyclerViewEventosRecomendados = root.findViewById(R.id.rvInicio2);
        layoutManagerProximos = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        layoutManagerRecomendados = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewEventosProximos.setLayoutManager(layoutManagerProximos);
        recyclerViewEventosProximos.setHasFixedSize(true);

        recyclerViewEventosProximos.setItemViewCacheSize(20);
        recyclerViewEventosProximos.setDrawingCacheEnabled(true);
        recyclerViewEventosProximos.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        recyclerViewEventosRecomendados.setLayoutManager(layoutManagerRecomendados);
        recyclerViewEventosRecomendados.setHasFixedSize(true);

        recyclerViewEventosRecomendados.setItemViewCacheSize(20);
        recyclerViewEventosRecomendados.setDrawingCacheEnabled(true);
        recyclerViewEventosRecomendados.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        progressBar = root.findViewById(R.id.pbInicio);
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getContext().getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = info != null && info.isConnected();

        if (isConnected) {
            getLocationPermission();
            getDeviceLocation();
        }
        return root;
    }

    private void getLocationPermission() {

        if (ContextCompat.checkSelfPermission(getActivity().getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
    }

    private void getDeviceLocation() {

        try {
            if (mLocationPermissionGranted) {
                Task locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(getActivity(), new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            mLastKnownLocation = (Location) task.getResult();
                            Ubicacion ubicacion = getUbicacion();
                            inicioViewModel.getEventosProximos(ubicacion.getCodigo_postal()).observe(getViewLifecycleOwner(), new Observer<List<Evento>>() {
                                @Override
                                public void onChanged(List<Evento> eventos) {
                                    progressBar.setVisibility(View.GONE);
                                    if (eventos != null) {
                                        eventosProximosList = eventos;
                                        adapterProximos = new EventoInicioAdapter(eventosProximosList, R.layout.list_item_eventos_inicio, getActivity(), new EventoInicioAdapter.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(Evento evento, int position) {
                                                callback.OnEventoSelected(evento);
                                            }
                                        });
                                        recyclerViewEventosProximos.setAdapter(adapterProximos);
                                    }
                                }
                            });

                            inicioViewModel.getEventosRecomendados(ubicacion.getCodigo_postal()).observe(getViewLifecycleOwner(), new Observer<List<Evento>>() {
                                @Override
                                public void onChanged(List<Evento> eventos) {
                                    progressBar.setVisibility(View.GONE);
                                    if (eventos != null) {
                                        eventosRecomendadosList = eventos;
                                        adapterRecomendados = new EventoInicioAdapter(eventosRecomendadosList, R.layout.list_item_eventos_inicio, getActivity(), new EventoInicioAdapter.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(Evento evento, int position) {
                                                callback.OnEventoSelected(evento);
                                            }
                                        });
                                        recyclerViewEventosRecomendados.setAdapter(adapterRecomendados);
                                    }
                                }
                            });
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    public Ubicacion getUbicacion() {
        Ubicacion ubicacion = null;
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
        try {
            List<Address> addressList = geocoder.getFromLocation(
                    mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude(), 1);
            ubicacion = new Ubicacion(0,
                    addressList.get(0).getAddressLine(0), addressList.get(0).getPostalCode(),
                    mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude()
            );
            for (Address address : addressList) {
                Log.d("TAG", address.getAddressLine(0));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ubicacion;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            callback = (OnEventoSelected) context;

        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "Debería implementar el interfaz OnEventoSelected");
        }
    }

    public interface OnEventoSelected {
        public void OnEventoSelected(Evento evento);
    }
}
