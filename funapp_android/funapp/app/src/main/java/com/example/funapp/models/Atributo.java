package com.example.funapp.models;

import java.io.Serializable;

public class Atributo implements Serializable {

    private int id_atributo;
    private String nombre;
    private String descripcion;
    private int experiencia;
    private int nivel;
    private int id_usuario;

    public Atributo(int id_atributo, String nombre, String descripcion, int experiencia, int id_usuario) {
        this.id_atributo = id_atributo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.experiencia = experiencia;
        this.id_usuario = id_usuario;
    }

    public int getId_atributo() {
        return id_atributo;
    }

    public void setId_atributo(int id_atributo) {
        this.id_atributo = id_atributo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getExperiencia() {
        return experiencia;
    }

    public void setExperiencia(int experiencia) {
        this.experiencia = experiencia;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }
}

