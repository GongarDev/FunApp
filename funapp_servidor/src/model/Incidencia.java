package model;

/**
 *
 * @author melkart
 */
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class Incidencia implements Serializable {

    private String tipo;
    private String descripcion;
    private Date fecha;
    private int id_usuario;

    public Incidencia(String descripcion, int id_usuario) {
        this.descripcion = descripcion;
        this.id_usuario = id_usuario;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public LocalDate getFecha_publicacion_LocalDate() {
        return getFecha().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
