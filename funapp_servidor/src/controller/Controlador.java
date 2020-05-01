package controller;

import dao.UsuarioDAOSQL;
import model.Credenciales;
import model.UsuarioEstandar;
import model.UsuarioResponsable;

/**
 *
 * @author melkart
 */
public class Controlador {

    UsuarioDAOSQL usuarioDAOSQL;

    public Controlador() {
        this.usuarioDAOSQL = new UsuarioDAOSQL();
    }

    public boolean existeUsuario(Credenciales credenciales) {
        return this.usuarioDAOSQL.existeUsuario(credenciales);
    }

    public UsuarioEstandar buscarUsEstandar(Credenciales credenciales) {
        return this.usuarioDAOSQL.consultarUsEstandar(credenciales);
    }

    public UsuarioResponsable buscarUsResponsable(Credenciales credenciales) {
        return this.usuarioDAOSQL.consultarUsResponsable(credenciales);
    }

    public boolean insertarUsEstandar(UsuarioEstandar usuario) {
        return this.usuarioDAOSQL.altaUsEstandar(usuario);
    }

    public boolean insertarUsResponsable(UsuarioResponsable usuario) {
        return this.usuarioDAOSQL.altaUsResponsable(usuario);
    }

    public Object buscarUsuario(Credenciales credenciales) {

        Object usuario = null;

        usuario = this.usuarioDAOSQL.consultarUsEstandar(credenciales);

        if (usuario == null) {
            usuario = this.usuarioDAOSQL.consultarUsResponsable(credenciales);
        }

        return usuario;
    }
}
