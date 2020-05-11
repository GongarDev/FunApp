package com.example.funapp.models;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashSet;

public class Evento implements Serializable {

    private int id_evento;
    private String nombre;
    private Date fecha_publicacion;
    private Date fecha_evento;
    private LocalTime hora_inicio;
    private LocalTime hora_fin;
    private HashSet<Ubicacion> ubicaciones;
    private Tematica tematica;
    private UsuarioResponsable usuario;

    public Evento(int id_evento, String nombre, Date fecha_publicacion, Date fecha_evento, LocalTime hora_inicio, LocalTime hora_fin, HashSet<Ubicacion> ubicaciones, Tematica tematica, UsuarioResponsable usuario) {
        this.id_evento = id_evento;
        this.nombre = nombre;
        this.fecha_publicacion = fecha_publicacion;
        this.fecha_evento = fecha_evento;
        this.hora_inicio = hora_inicio;
        this.hora_fin = hora_fin;
        this.ubicaciones = ubicaciones;
        this.tematica = tematica;
        this.usuario = usuario;
    }

    public int getId_evento() {
        return id_evento;
    }

    public void setId_evento(int id_evento) {
        this.id_evento = id_evento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFecha_publicacion() {
        return fecha_publicacion;
    }

    public void setFecha_publicacion(Date fecha_publicacion) {
        this.fecha_publicacion = fecha_publicacion;
    }

    public Date getFecha_evento() {
        return fecha_evento;
    }

    public void setFecha_evento(Date fecha_evento) {
        this.fecha_evento = fecha_evento;
    }

    public LocalTime getHora_inicio() {
        return hora_inicio;
    }

    public void setHora_inicio(LocalTime hora_inicio) {
        this.hora_inicio = hora_inicio;
    }

    public LocalTime getHora_fin() {
        return hora_fin;
    }

    public void setHora_fin(LocalTime hora_fin) {
        this.hora_fin = hora_fin;
    }

    public HashSet<Ubicacion> getUbicaciones() {
        return ubicaciones;
    }

    public void setUbicaciones(HashSet<Ubicacion> ubicaciones) {
        this.ubicaciones = ubicaciones;
    }

    public Tematica getTematica() {
        return tematica;
    }

    public void setTematica(Tematica tematica) {
        this.tematica = tematica;
    }

    public UsuarioResponsable getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioResponsable usuario) {
        this.usuario = usuario;
    }

}
