
package model;

import java.io.Serializable;

/**
 *
 * @author melkart
 */
public class Tematica implements Serializable{
    
    private int id_tematica;
    private String nombre;
    private String descripcion;
    private int edad_legal;

    
    public Tematica(int id_tematica, String nombre, String descripcion, int edad_legal) {
        this.id_tematica = id_tematica;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.edad_legal = edad_legal;
    }

    public int getId_tematica() {
        return id_tematica;
    }

    public void setId_tematica(int id_tematica) {
        this.id_tematica = id_tematica;
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

    public int getEdad_legal() {
        return edad_legal;
    }

    public void setEdad_legal(int edad_legal) {
        this.edad_legal = edad_legal;
    }

    @Override
    public String toString() {
        return nombre + ", " + descripcion + ", a partir de " + edad_legal + " años";
    }   
}
