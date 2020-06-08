/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

/**
 *
 * @author melkart
 */
public class Valoracion implements Serializable {

    private int id_valoracion;
    private String comentario;
    private float puntaje;
    private Date fecha;
    private LocalTime hora;
    private Usuario usuario;
    private Evento evento;

    public Valoracion(String comentario, float puntaje, Usuario usuario, Evento evento) {
        this.comentario = comentario;
        this.puntaje = puntaje;
        this.usuario = usuario;
        this.evento = evento;
    }

    public int getId_valoracion() {
        return id_valoracion;
    }

    public void setId_valoracion(int id_valoracion) {
        this.id_valoracion = id_valoracion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public float getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(float puntaje) {
        this.puntaje = puntaje;
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

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public LocalDate getFecha_publicacion_LocalDate() {
        return getFecha().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
