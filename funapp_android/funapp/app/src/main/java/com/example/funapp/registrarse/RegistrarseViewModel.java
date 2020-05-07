package com.example.funapp.registrarse;

import android.os.Build;
import android.util.Patterns;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.funapp.R;
import com.example.funapp.registrarse.data.Result;
import com.example.funapp.registrarse.data.RegistrarseRepository;
import com.example.funapp.registrarse.data.model.RegistradoInUser;

import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import static java.time.temporal.ChronoUnit.YEARS;

public class RegistrarseViewModel extends ViewModel {

    private MutableLiveData<RegistrarseFormState> registrarseFormState = new MutableLiveData<>();
    private MutableLiveData<RegistrarseResult> registrarseResult = new MutableLiveData<>();
    private RegistrarseRepository registrarseRepository;

    RegistrarseViewModel(RegistrarseRepository registrarseRepository) {
        this.registrarseRepository = registrarseRepository;
    }

    LiveData<RegistrarseFormState> getRegistrarseFormState() {
        return registrarseFormState;
    }

    LiveData<RegistrarseResult> getRegistrarseResult() {
        return registrarseResult;
    }

    public void registrarse(String seudonimo, String contrasenia, String confirmarContrasenia,
                            String correo, String fechaNacimiento, boolean responsable) {
        // can be launched in a separate asynchronous job
        Result<RegistradoInUser> result = registrarseRepository.registrarse(seudonimo, contrasenia,
                confirmarContrasenia, correo, fechaNacimiento, responsable);

        if (result instanceof Result.Success) {
            RegistradoInUser data = ((Result.Success<RegistradoInUser>) result).getData();
            registrarseResult.setValue(new RegistrarseResult(new RegistradoInUserView(data.getDisplay()), data.getEstadoSesion()));
        } else {
            registrarseResult.setValue(new RegistrarseResult(R.string.registrarse_failed));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void registrarseDataChanged(String userName, String password, String confirmPassword, String email, String birthday) {
        if (!isUserNameValid(userName)) {
            registrarseFormState.setValue(new RegistrarseFormState(R.string.invalid_username,
                    null, null, null, null));
        } else if (!isPasswordValid(password)) {
            registrarseFormState.setValue(new RegistrarseFormState(null,
                    R.string.invalid_password, null, null, null));
        } else if(!isConfirmPasswordValid(password, confirmPassword)) {
            registrarseFormState.setValue(new RegistrarseFormState(null,
                    null, R.string.invalid_confirm_password, null, null));
        } else if (!isUserEmailValid(email)) {
            registrarseFormState.setValue(new RegistrarseFormState(null,
                    null, null, R.string.invalid_email, null));
        }else if(!isAdult(birthday)) {
            registrarseFormState.setValue(new RegistrarseFormState(null,
                    null, null, null, R.string.invalid_birthday));
        }
            else{
            registrarseFormState.setValue(new RegistrarseFormState(true));
        }
    }

    // Validaciones
    private boolean isUserNameValid(String userName) {
        return userName != null && userName.trim().length() >= 5;
    }

    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }

    private boolean isConfirmPasswordValid(String password, String confirmPassword) {
        return confirmPassword != null && confirmPassword.equals(password);
    }

    private boolean isUserEmailValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private boolean isAdult(String birthday) {

        long diferencia = 0;

        if(!birthday.isEmpty()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            String dateNow = LocalDate.now().format(formatter);
            LocalDate fechaNacimiento = LocalDate.parse(birthday, formatter);
            LocalDate fechaActual = LocalDate.parse(dateNow, formatter);

            diferencia = YEARS.between(fechaNacimiento, fechaActual);
        }

        return diferencia >= 18;
    }
}
