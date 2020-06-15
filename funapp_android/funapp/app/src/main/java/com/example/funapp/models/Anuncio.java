package com.example.funapp.models;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class Anuncio implements Serializable {

    private int id_anuncio;
    private String nombre;
    private String mensaje;
    private Date fecha;
    private int id_usuario;

    public Anuncio(String mensaje) {
        this.mensaje = mensaje;
    }

    public Anuncio(int id_anuncio, String nombre, String mensaje, Date fecha, int id_usuario) {
        this.id_anuncio = id_anuncio;
        this.nombre = nombre;
        this.mensaje = mensaje;
        this.fecha = fecha;
        this.id_usuario = id_usuario;
    }

    public int getId_anuncio() {
        return id_anuncio;
    }

    public void setId_anuncio(int id_anuncio) {
        this.id_anuncio = id_anuncio;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public LocalDate getFecha_publicacion_LocalDate() {
        return getFecha().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
