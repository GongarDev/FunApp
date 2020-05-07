package com.example.funapp.registrarse;

import androidx.annotation.Nullable;

/**
 * Validar datos de los campos del formulario de registrarse.
 */
class RegistrarseFormState {

    @Nullable
    private Integer usernameError;
    @Nullable
    private Integer passwordError;
    @Nullable
    private Integer confirmPasswordError;
    @Nullable
    private Integer emailError;
    @Nullable
    private Integer birthDayError;
    private boolean isDataValid;

    RegistrarseFormState(@Nullable Integer usernameError, @Nullable Integer passwordError, @Nullable Integer confirmPasswordError,
                         @Nullable Integer emailError, @Nullable Integer birthDayError) {
        this.usernameError = usernameError;
        this.passwordError = passwordError;
        this.confirmPasswordError = confirmPasswordError;
        this.emailError = emailError;
        this.birthDayError = birthDayError;
        this.isDataValid = false;
    }

    RegistrarseFormState(boolean isDataValid) {
        this.usernameError = null;
        this.passwordError = null;
        this.confirmPasswordError = null;
        this.emailError = null;
        this.birthDayError = null;
        this.isDataValid = isDataValid;
    }

    @Nullable
    Integer getUsernameError() {
        return usernameError;
    }

    @Nullable
    Integer getPasswordError() {
        return passwordError;
    }

    @Nullable
    public Integer getConfirmPasswordError() {
        return confirmPasswordError;
    }

    @Nullable
    public Integer getEmailError() {
        return emailError;
    }

    @Nullable
    public Integer getBirthDayError() {
        return birthDayError;
    }

    boolean isDataValid() {
        return isDataValid;
    }
}
