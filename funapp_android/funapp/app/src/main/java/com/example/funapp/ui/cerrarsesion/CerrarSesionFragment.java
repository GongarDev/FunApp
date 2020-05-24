package com.example.funapp.ui.cerrarsesion;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import com.example.funapp.login.LoginActivity;

public class CerrarSesionFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intentAcceder = new Intent(this.getContext(), LoginActivity.class);
        startActivity(intentAcceder);
        getActivity().finish();
    }
}

