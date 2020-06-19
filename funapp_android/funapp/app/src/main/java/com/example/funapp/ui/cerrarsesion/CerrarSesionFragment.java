package com.example.funapp.ui.cerrarsesion;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import com.example.funapp.login.LoginActivity;
import com.example.funapp.util.SocketHandler;

public class CerrarSesionFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intentAcceder = new Intent(this.getContext(), LoginActivity.class);
        SocketHandler.cerrarSocket();
        startActivity(intentAcceder);
        getActivity().finish();
    }
}

