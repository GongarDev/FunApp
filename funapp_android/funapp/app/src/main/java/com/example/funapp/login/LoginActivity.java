package com.example.funapp.login;

import android.app.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.os.StrictMode;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.funapp.R;
import com.example.funapp.activities.MainActivity;
import com.example.funapp.registrarse.RegistrarseActivity;
import com.example.funapp.util.Protocolo;
import com.example.funapp.util.SocketHandler;

public class LoginActivity extends AppCompatActivity implements Protocolo {

    private LoginViewModel loginViewModel;
    private Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        context = this;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch(Exception ex) {}

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch(Exception ex) {}

        if(!gps_enabled && !network_enabled) {
            // notify user
            new AlertDialog.Builder(this)
                    .setMessage("La aplicación necesita tener activda la información de ubicación.")
                    .setPositiveButton("Abrir configuración", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                            context.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    }).setNegativeButton("Cancelar",null).show();
        }

        SocketHandler.setContext(this);

        final EditText usernameEditText = findViewById(R.id.loginCorreo);
        final EditText passwordEditText = findViewById(R.id.loginContrasenia);
        final Button loginButton = findViewById(R.id.buttonIniciarSesion);
        final ProgressBar loadingProgressBar = findViewById(R.id.loadingLogin);
        final Button registrarseButton = findViewById(R.id.buttonRegistrate);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .permitNetwork().build());

        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {

                    if (loginResult.getEstadoSesion() == SESION_ABIERTA_RESPONSABLE) {
                        updateUiWithUser(loginResult.getSuccess());
                        Intent intentAcceder = new Intent(LoginActivity.this, MainActivity.class);
                        intentAcceder.putExtra("usuario", loginResult.getUsuario());
                        intentAcceder.putExtra("tipoUsuario", SESION_ABIERTA_RESPONSABLE);
                        startActivity(intentAcceder);
                        setResult(Activity.RESULT_OK);
                        finish();
                    } else if (loginResult.getEstadoSesion() == SESION_ABIERTA_ESTANDAR) {
                        updateUiWithUser(loginResult.getSuccess());
                        Intent intentAcceder = new Intent(LoginActivity.this, MainActivity.class);
                        intentAcceder.putExtra("usuario", loginResult.getUsuario());
                        intentAcceder.putExtra("tipoUsuario", SESION_ABIERTA_ESTANDAR);
                        startActivity(intentAcceder);
                        setResult(Activity.RESULT_OK);
                        finish();
                    } else if (loginResult.getEstadoSesion() == SESION_FALLIDA) {
                        updateUiWithUser(loginResult.getSuccess());
                    }
                }
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                loginViewModel.login(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        });

        registrarseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentRegistrarse = new Intent(LoginActivity.this, RegistrarseActivity.class);
                startActivity(intentRegistrarse);
            }
        });
    }

    private void updateUiWithUser(LoggedInUserView model) {
        Toast.makeText(getApplicationContext(), model.getDisplayName(), Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}
