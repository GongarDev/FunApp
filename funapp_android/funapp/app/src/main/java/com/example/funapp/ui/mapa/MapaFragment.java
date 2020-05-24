package com.example.funapp.ui.mapa;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.funapp.R;
import com.example.funapp.activities.MainActivity;
import com.example.funapp.adapters.CustomInfoWindow;
import com.example.funapp.models.Evento;
import com.example.funapp.models.Ubicacion;
import com.example.funapp.util.Protocolo;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MapaFragment extends Fragment implements Protocolo, OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    private MapaViewModel mapaViewModel;
    private GoogleMap mMap;
    private List<Evento> listaEventos;
    private OnEventoMapaSelected callback;

    GeoDataClient mGeoDataClient;
    PlaceDetectionClient mPlaceDetectionClient;
    FusedLocationProviderClient mFusedLocationProviderClient;
    boolean mLocationPermissionGranted;
    Location mLastKnownLocation;
    LatLng mDefaultLocation;

    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_mapa, container, false);
        mapaViewModel =
                ViewModelProviders.of(this).get(MapaViewModel.class);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.mapa);
        mapFragment.getMapAsync(this);

        mDefaultLocation = new LatLng(40.4165001, -3.7025599); //Madrid
        mGeoDataClient = Places.getGeoDataClient(getActivity(), null);
        mPlaceDetectionClient = Places.getPlaceDetectionClient(getActivity(), null);
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());

        return root;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.setInfoWindowAdapter(new CustomInfoWindow(getActivity().getApplicationContext()));
        mMap.setOnInfoWindowClickListener(this);
        updateLocationUI();
        getDeviceLocation();

    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        callback.OnEventoMapaSelected((Evento)marker.getTag());
    }

    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
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
        updateLocationUI();
    }

    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
            if (mLocationPermissionGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                mLastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    private void getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (mLocationPermissionGranted) {
                Task locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(getActivity(), new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            mLastKnownLocation = (Location) task.getResult();

                            Ubicacion ubicacion = getUbicacion();
                            mapaViewModel.getEventos(ubicacion.getCodigo_postal()).observe(getViewLifecycleOwner(), new Observer<List<Evento>>() {
                                @Override
                                public void onChanged(List<Evento> eventos) {

                                    if (eventos != null) {
                                        listaEventos = eventos;
                                        for (Evento e : listaEventos) {
                                            if (e.getUbicacionesList() != null && !e.getUbicacionesList().isEmpty()) {
                                                for (Ubicacion ubicacion : e.getUbicacionesList()) {
                                                    //Ubicacion ubicacion = e.getUbicacionesList().get(0);
                                                    mMap.addMarker(new MarkerOptions().
                                                            position(new LatLng(ubicacion.getLatitud(), ubicacion.getLongitud())).
                                                            title(String.valueOf(e.getNombre())).
                                                            snippet(e.getTematica().getNombre())).setTag(e);
                                                }
                                            }

                                        }
                                    }
                                }
                            });
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(mLastKnownLocation.getLatitude(),
                                            mLastKnownLocation.getLongitude()), 15));
                        } else {
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mDefaultLocation, 15));
                            mMap.getUiSettings().setMyLocationButtonEnabled(false);
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
            callback = (OnEventoMapaSelected) context;

        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "Debería implementar el interfaz OnEventoSelected");
        }
    }

    public interface OnEventoMapaSelected {
        public void OnEventoMapaSelected(Evento evento);
    }
}
