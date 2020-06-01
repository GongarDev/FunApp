package com.example.funapp.registrarse;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.funapp.R;
import com.example.funapp.login.LoginActivity;
import com.example.funapp.util.Protocolo;

import java.util.Calendar;

public class RegistrarseActivity extends AppCompatActivity implements Protocolo, View.OnClickListener {

    private static final String CERO = "0";
    private static final String BARRA = "-";

    //Calendario para obtener fecha & hora
    public final Calendar c = Calendar.getInstance();

    //Variables para obtener la fecha
    final int mes = c.get(Calendar.MONTH);
    final int dia = c.get(Calendar.DAY_OF_MONTH);
    final int anio = c.get(Calendar.YEAR);

    //Widgets
    EditText etFecha;
    ProgressBar loadingProgressBar;
    ImageButton ibObtenerFecha;

    private RegistrarseViewModel registrarseViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);

        registrarseViewModel = ViewModelProviders.of(this, new RegistrarseViewModelFactory())
                .get(RegistrarseViewModel.class);

        Intent intent = getIntent();

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .permitNetwork().build());

        etFecha = (EditText) findViewById(R.id.etFechaNacimiento);
        //Widget ImageButton del cual usaremos el evento clic para obtener la fecha
        ibObtenerFecha = findViewById(R.id.ibFecha);
        //Evento setOnClickListener - clic
        ibObtenerFecha.setOnClickListener(this);


        final EditText etSeudonimo = findViewById(R.id.etRegistrarseSeudonimo);
        final EditText etContrasenia = findViewById(R.id.etRegistrarseContrasenia);
        final EditText etConfirmarContrasenia = findViewById(R.id.etConfirmarContrasenia);
        final EditText etCorreo = findViewById(R.id.etCorreo);
        loadingProgressBar = findViewById(R.id.loadingRegistrarse);
        final Switch switchResponsable = findViewById(R.id.switchResponsable);
        final Button bRegistrarse = findViewById(R.id.bRegistrarse);
        bRegistrarse.setOnClickListener(this);


        if (intent != null) {

            RegistrarseFragment registrarseFragment = (RegistrarseFragment)
                    getSupportFragmentManager().findFragmentById(R.id.fragmentMobileRegistrarse);

        }

        registrarseViewModel.getRegistrarseFormState().observe(this, new Observer<RegistrarseFormState>() {
            @Override
            public void onChanged(@Nullable RegistrarseFormState registrarseFormState) {
                if (registrarseFormState == null) {
                    return;
                }
                bRegistrarse.setEnabled(registrarseFormState.isDataValid());
                if (registrarseFormState.getUsernameError() != null) {
                    etSeudonimo.setError(getString(registrarseFormState.getUsernameError()));
                }
                if (registrarseFormState.getPasswordError() != null) {
                    etContrasenia.setError(getString(registrarseFormState.getPasswordError()));
                }
                if (registrarseFormState.getConfirmPasswordError() != null) {
                    etConfirmarContrasenia.setError(getString(registrarseFormState.getConfirmPasswordError()));
                }
                if (registrarseFormState.getEmailError() != null) {
                    etCorreo.setError(getString(registrarseFormState.getEmailError()));
                }
                if (registrarseFormState.getBirthDayError() != null) {
                    etFecha.setError(getString(registrarseFormState.getBirthDayError()));
                }
            }
        });

        registrarseViewModel.getRegistrarseResult().observe(this, new Observer<RegistrarseResult>() {
            @Override
            public void onChanged(@Nullable RegistrarseResult registrarseResult) {
                if (registrarseResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (registrarseResult.getError() != null) {
                    showLoginFailed(registrarseResult.getError());
                }
                if (registrarseResult.getSuccess() != null) {

                    if(registrarseResult.getEstadoRegistro()== SIN_SESION){
                        Intent intentLogin = new Intent(RegistrarseActivity.this, LoginActivity.class);
                        startActivity(intentLogin);
                        updateUiWithUser(registrarseResult.getSuccess());
                        setResult(Activity.RESULT_OK);
                        finish();
                    }
                    else if(registrarseResult.getEstadoRegistro()== REGISTRARSE_EXISTE_SEUDONIMO ||
                            registrarseResult.getEstadoRegistro()== REGISTRARSE_EXISTE_USUARIO ||
                            registrarseResult.getEstadoRegistro()== REGISTRARSE_FALLIDO){
                        updateUiWithUser(registrarseResult.getSuccess());
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

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void afterTextChanged(Editable s) {
                registrarseViewModel.registrarseDataChanged(etSeudonimo.getText().toString(),
                        etContrasenia.getText().toString(),etConfirmarContrasenia.getText().toString(),
                        etCorreo.getText().toString(),etFecha.getText().toString());
            }
        };
        etSeudonimo.addTextChangedListener(afterTextChangedListener);
        etContrasenia.addTextChangedListener(afterTextChangedListener);
        etConfirmarContrasenia.addTextChangedListener(afterTextChangedListener);
        etCorreo.addTextChangedListener(afterTextChangedListener);
        etFecha.addTextChangedListener(afterTextChangedListener);

        bRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);

                confirmarRegistro(etSeudonimo.getText().toString(), etContrasenia.getText().toString(),
                        etConfirmarContrasenia.getText().toString(),etCorreo.getText().toString(),
                        etFecha.getText().toString(),switchResponsable.isChecked()

                );
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ibFecha:
                obtenerFecha();
                break;
        }
    }

    private void obtenerFecha(){
        DatePickerDialog recogerFecha = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //Esta variable lo que realiza es aumentar en uno el mes ya que comienza desde 0 = enero
                final int mesActual = month + 1;
                //Formateo el día obtenido: antepone el 0 si son menores de 10
                String diaFormateado = (dayOfMonth < 10)? CERO + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);
                //Formateo el mes obtenido: antepone el 0 si son menores de 10
                String mesFormateado = (mesActual < 10)? CERO + String.valueOf(mesActual):String.valueOf(mesActual);
                //Muestro la fecha con el formato deseado
                etFecha.setText(diaFormateado + BARRA + mesFormateado + BARRA + year);


            }
            //Estos valores deben ir en ese orden, de lo contrario no mostrara la fecha actual
            /**
             *También puede cargar los valores que quiera
             */
        },anio, mes, dia);
        //Muestro el widget
        recogerFecha.show();

    }

    private void confirmarRegistro(String seudonimo, String contrasenia, String confirmarContrasenia,
                                   String correo, String fechaNacimiento, boolean responsable){

        loadingProgressBar.setVisibility(View.VISIBLE);
        registrarseViewModel.registrarse(seudonimo, contrasenia, confirmarContrasenia, correo, fechaNacimiento, responsable);
    }

    private void updateUiWithUser(RegistradoInUserView model) {
        Toast.makeText(getApplicationContext(), model.getDisplay(), Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}
