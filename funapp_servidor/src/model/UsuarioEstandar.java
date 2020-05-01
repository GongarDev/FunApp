package model;

import java.util.Date;

/**
 *
 * @author melkart
 */
public class UsuarioEstandar extends Usuario{
    
    private boolean perfil_publico;

    public UsuarioEstandar(boolean perfil_publico, int id_usuario, String seudonimo, String email, Date fecha_nac, Date fecha_ingreso, String contrasenia, String codigo_qr) {
        super(id_usuario, seudonimo, email, fecha_nac, fecha_ingreso, contrasenia, codigo_qr);
        this.perfil_publico = perfil_publico;
    }

    public boolean isPerfil_publico() {
        return perfil_publico;
    }

    public void setPerfil_publico(boolean perfil_publico) {
        this.perfil_publico = perfil_publico;
    }
}
