package model;

/**
 *
 * @author melkart
 */
public class Entidad {
    
    private int id;
    private String nombre;
    private String nif;
    private String calle;
    private String provincia;
    private String localidad;
    private int codigo_postal;
    private int telefono;
    private double latitud;
    private double longitud;

    public Entidad(String nombre, String nif, String calle, String provincia, String localidad, int codigo_postal, int telefono) {
        this.nombre = nombre;
        this.nif = nif;
        this.calle = calle;
        this.provincia = provincia;
        this.localidad = localidad;
        this.codigo_postal = codigo_postal;
        this.telefono = telefono;
    }

    public Entidad(int id, String nombre, String nif, String calle, String provincia, String localidad, int codigo_postal, int telefono, double latitud, double longitud) {
        this.id = id;
        this.nombre = nombre;
        this.nif = nif;
        this.calle = calle;
        this.provincia = provincia;
        this.localidad = localidad;
        this.codigo_postal = codigo_postal;
        this.telefono = telefono;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getCodigo_postal() {
        return codigo_postal;
    }

    public void setCodigo_postal(int codigo_postal) {
        this.codigo_postal = codigo_postal;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
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
