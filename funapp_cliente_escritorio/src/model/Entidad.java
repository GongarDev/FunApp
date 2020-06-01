package model;

import java.io.Serializable;

/**
 *
 * @author melkart
 */
public class Entidad implements Serializable {

    private int id_entidad;
    private String nombre;
    private String nif;
    private String calle;
    private String provincia;
    private String localidad;
    private String codigo_postal;
    private String telefono;
    private int id_usuario;

    public Entidad() {
    }

    public Entidad(int id_entidad, String nombre, String nif, String calle, String provincia,
                   String localidad, String codigo_postal, String telefono, int id_usuario) {
        this.id_entidad = id_entidad;
        this.nombre = nombre;
        this.nif = nif;
        this.calle = calle;
        this.provincia = provincia;
        this.localidad = localidad;
        this.codigo_postal = codigo_postal;
        this.telefono = telefono;
        this.id_usuario = id_usuario;
    }

    public int getId_entidad() {
        return id_entidad;
    }

    public void setId_entidad(int id_entidad) {
        this.id_entidad = id_entidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getLocalidad() {
        return localidad;
    }

    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    public String getCodigo_postal() {
        return codigo_postal;
    }

    public void setCodigo_postal(String codigo_postal) {
        this.codigo_postal = codigo_postal;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }
}
