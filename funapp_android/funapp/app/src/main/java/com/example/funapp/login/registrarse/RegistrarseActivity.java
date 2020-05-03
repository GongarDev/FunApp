package com.example.funapp.login.registrarse;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.funapp.R;

public class RegistrarseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);

        Intent intent = getIntent();

        if (intent != null) {

            RegistrarseFragment registrarseFragment = (RegistrarseFragment)
                    getSupportFragmentManager().findFragmentById(R.id.fragmentMobileRegistrarse);

        }
    }
}
