package com.example.funapp.login.data.model;

import com.example.funapp.models.Usuario;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class LoggedInUser {

    private String userId;
    private String displayName;
    private Usuario usuario;
    private Integer estadoSesion;

    public LoggedInUser(String userId, String displayName, Usuario usuario, Integer estadoSesion) {
        this.userId = userId;
        this.displayName = displayName;
        this.usuario = usuario;
        this.estadoSesion = estadoSesion;
    }

    public String getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Usuario getUsuario() { return usuario; }

    public Integer getEstadoSesion(){
        return estadoSesion;
    }
}
