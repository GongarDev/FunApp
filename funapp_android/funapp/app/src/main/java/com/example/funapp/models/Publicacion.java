package com.example.funapp.models;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

public class Publicacion implements Serializable {

    private int id_publicacion;
    private String mensaje;
    Date fecha;
    LocalTime hora;

    public Publicacion(String mensaje) {
        this.mensaje = mensaje;
    }

    public Publicacion(String mensaje, Date fecha, LocalTime hora) {
        this.mensaje = mensaje;
        this.fecha = fecha;
        this.hora = hora;
    }

    public int getId_publicacion() {
        return id_publicacion;
    }

    public void setId_publicacion(int id_publicacion) {
        this.id_publicacion = id_publicacion;
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

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public LocalDate getFecha_publicacion_LocalDate() {
        return getFecha().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
