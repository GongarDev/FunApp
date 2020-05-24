package com.example.funapp.ui.explorar.EventosTematica;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.funapp.R;
import com.example.funapp.adapters.EventoTematicaAdapter;
import com.example.funapp.models.Evento;
import com.example.funapp.models.Ubicacion;
import com.example.funapp.util.Protocolo;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class EventosTematicaFragment extends Fragment implements Protocolo {

    private EventosTematicaViewModel eventosTematicaViewModel;
    private List<Evento> listaEventos;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private EventoTematicaAdapter adapter;
    private OnEventoExplorarSelected callback;
    private TextView titulo;

    GeoDataClient mGeoDataClient;
    PlaceDetectionClient mPlaceDetectionClient;
    FusedLocationProviderClient mFusedLocationProviderClient;
    boolean mLocationPermissionGranted;
    Location mLastKnownLocation;
    LatLng mDefaultLocation;
    ProgressBar progressBar;

    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_eventos_tematica, container, false);
        eventosTematicaViewModel =
                ViewModelProviders.of(this).get(EventosTematicaViewModel.class);

        titulo = root.findViewById(R.id.tvEventosTematica);
        titulo.setText(((EventosTematicaActivity) getActivity()).getTematica().getNombre());

        mDefaultLocation = new LatLng(40.4165001, -3.7025599); //Madrid
        mGeoDataClient = Places.getGeoDataClient(getActivity(), null);
        mPlaceDetectionClient = Places.getPlaceDetectionClient(getActivity(), null);
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());
        recyclerView = root.findViewById(R.id.rvEventosTematica);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        progressBar = root.findViewById(R.id.pbEventosTematica);
        getLocationPermission();
        getDeviceLocation();

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
                            String tematica = ((EventosTematicaActivity) getActivity()).getTematica().getNombre();
                            Ubicacion ubicacion = getUbicacion();
                            eventosTematicaViewModel.getEventos(ubicacion.getCodigo_postal(), tematica).observe(getViewLifecycleOwner(), new Observer<List<Evento>>() {
                                @Override
                                public void onChanged(List<Evento> eventos) {

                                    progressBar.setVisibility(View.GONE);

                                    if (eventos != null) {
                                        listaEventos = eventos;
                                        adapter = new EventoTematicaAdapter(listaEventos, R.layout.list_item_evento_tematica, getActivity(), new EventoTematicaAdapter.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(Evento evento, int position) {
                                                callback.OnEventoExplorarSelected(evento);
                                            }
                                        });
                                        recyclerView.setAdapter(adapter);
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
            callback = (OnEventoExplorarSelected) context;

        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "Debería implementar el interfaz OnEventoExplorarSelected");
        }
    }

    public interface OnEventoExplorarSelected {
        public void OnEventoExplorarSelected(Evento evento);
    }
}
