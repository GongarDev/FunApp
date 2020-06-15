
package model;

import java.util.Date;

/**
 *
 * @author melkart
 */
public class UsuarioAdmin extends Usuario{
    
    private String dni;
    private String nombre;
    private String apellido;

    public UsuarioAdmin(String dni, String nombre, String apellido, 
            int id_usuario, String seudonimo, String email, 
            Date fecha_nac, Date fecha_ingreso, String contrasenia, String codigo_qr) {
        super(id_usuario, seudonimo, email, fecha_nac, fecha_ingreso, contrasenia, codigo_qr);
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
    }
   
    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
}
