package com.example.funapp.models;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class Usuario implements Serializable {

    private int id_usuario;
    private String seudonimo;
    private String email;
    private Date fecha_nac;
    private Date fecha_ingreso;
    private String contrasenia;
    private String contrasenia_recup;
    private String imagen;

    public Usuario() {
    }

    public Usuario(String seudonimo, String email, Date fecha_nac, Date fecha_ingreso, String contrasenia) {
        this.seudonimo = seudonimo;
        this.email = email;
        this.fecha_nac = fecha_nac;
        this.fecha_ingreso = fecha_ingreso;
        this.contrasenia = contrasenia;
    }

    public Usuario(int id_usuario, String seudonimo, String email, Date fecha_nac, Date fecha_ingreso, String contrasenia, String imagen) {
        this.id_usuario = id_usuario;
        this.seudonimo = seudonimo;
        this.email = email;
        this.fecha_nac = fecha_nac;
        this.fecha_ingreso = fecha_ingreso;
        this.contrasenia = contrasenia;
        this.imagen = imagen;
    }

    public Usuario(int id_usuario, String seudonimo, Date fecha_ingreso, String imagen) {
        this.id_usuario = id_usuario;
        this.seudonimo = seudonimo;
        this.fecha_ingreso = fecha_ingreso;
        this.imagen = imagen;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getSeudonimo() {
        return seudonimo;
    }

    public void setSeudonimo(String seudonimo) {
        this.seudonimo = seudonimo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getFecha_nac() {
        return fecha_nac;
    }

    public void setFecha_nac(Date fecha_nac) {
        this.fecha_nac = fecha_nac;
    }

    public Date getFecha_ingreso() {
        return fecha_ingreso;
    }

    public void setFecha_ingreso(Date fecha_ingreso) {
        this.fecha_ingreso = fecha_ingreso;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getContrasenia_recup() {
        return contrasenia_recup;
    }

    public void setContrasenia_recup(String contrasenia_recup) {
        this.contrasenia_recup = contrasenia_recup;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public LocalDate getFecha_nac_LocalDate() {
        return getFecha_nac().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public LocalDate getFecha_ingreso_LocalDate() {
        return getFecha_ingreso().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
