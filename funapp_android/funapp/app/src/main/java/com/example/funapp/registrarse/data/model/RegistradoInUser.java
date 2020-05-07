package com.example.funapp.registrarse.data.model;

import com.example.funapp.models.Usuario;

import java.net.Socket;

public class RegistradoInUser {

    private String userId;
    private String display;
    private Integer estadoSesion;

    public RegistradoInUser(String userId, String display, Integer estadoSesion) {
        this.userId = userId;
        this.display = display;
        this.estadoSesion = estadoSesion;
    }

    public String getUserId() {
        return userId;
    }

    public String getDisplay() {
        return display;
    }

    public Integer getEstadoSesion(){
        return estadoSesion;
    }
}
