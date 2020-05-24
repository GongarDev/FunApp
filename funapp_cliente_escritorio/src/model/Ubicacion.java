
package model;

import java.io.Serializable;

/**
 *
 * @author melkart
 */
public class Ubicacion implements Serializable{
    
    private int id_ubicacion;
    private String calle;
    private String codigo_postal;
    private double latitud;
    private double longitud;

    public Ubicacion(int id_ubicacion, String calle, String codigo_postal, double latitud, double longitud) {
        this.id_ubicacion = id_ubicacion;
        this.calle = calle;
        this.codigo_postal = codigo_postal;
        this.latitud = latitud;
        this.longitud = longitud;
    }
    
    public int getId_ubicacion() {
        return id_ubicacion;
    }

    public void setId_ubicacion(int id_ubicacion) {
        this.id_ubicacion = id_ubicacion;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getCodigo_postal() {
        return codigo_postal;
    }

    public void setCodigo_postal(String codigo_postal) {
        this.codigo_postal = codigo_postal;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }
    
    
}
