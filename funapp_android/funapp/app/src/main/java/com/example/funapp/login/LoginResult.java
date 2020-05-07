package com.example.funapp.login;

import androidx.annotation.Nullable;

import com.example.funapp.models.Usuario;

/**
 * Authentication result : success (user details) or error message.
 */
class LoginResult {
    @Nullable
    private LoggedInUserView success;
    @Nullable
    private Integer error;
    @Nullable
    private Integer estadoSesion;
    @Nullable
    private Usuario usuario;

    LoginResult(@Nullable Integer error) {
        this.error = error;
    }

    LoginResult(@Nullable LoggedInUserView success) {
        this.success = success;

    }

    LoginResult(@Nullable LoggedInUserView success, Usuario usuario,Integer estadoSesion) {
        this.success = success;
        this.usuario = usuario;
        this.estadoSesion = estadoSesion;
    }

    @Nullable
    LoggedInUserView getSuccess() { return success; }

    @Nullable
    Integer getError() {
        return error;
    }

    @Nullable
    Usuario getUsuario() { return usuario; }

    @Nullable
    Integer getEstadoSesion() { return estadoSesion; }
}
