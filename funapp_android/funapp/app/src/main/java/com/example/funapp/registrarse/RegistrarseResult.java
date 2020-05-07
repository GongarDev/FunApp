package com.example.funapp.registrarse;

import androidx.annotation.Nullable;

import java.net.Socket;

public class RegistrarseResult {
    @Nullable
    private RegistradoInUserView success;
    @Nullable
    private Integer error;
    @Nullable
    private Integer estadoRegistro;

    RegistrarseResult(@Nullable Integer error) {
        this.error = error;
    }

    RegistrarseResult(@Nullable RegistradoInUserView success, Integer estadoRegistro) {
        this.success = success;
        this.estadoRegistro = estadoRegistro;
    }

    @Nullable
    RegistradoInUserView getSuccess() {
        return success;
    }

    @Nullable
    Integer getError() {
        return error;
    }

    @Nullable
    Integer getEstadoRegistro() { return estadoRegistro; }
}
