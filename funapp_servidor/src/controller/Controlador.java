package controller;

import dao.EventoDAOSQL;
import dao.UsuarioDAOSQL;
import java.util.List;
import model.Credenciales;
import model.Evento;
import model.UsuarioEstandar;
import model.UsuarioResponsable;

/**
 *
 * @author melkart
 */
public class Controlador {

    UsuarioDAOSQL usuarioDAOSQL;
    EventoDAOSQL eventoDAOSQL;
    
    public Controlador() {
        this.usuarioDAOSQL = new UsuarioDAOSQL();
        this.eventoDAOSQL = new EventoDAOSQL();
    }

    public boolean existeUsuario(String correo) {
        return this.usuarioDAOSQL.existeUsuario(correo);
    }
    
        public boolean existeNombreUsuario(String seudonimo) {
        return this.usuarioDAOSQL.existeUsuario(seudonimo);
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
    
    public boolean insertarEvento(Evento evento) {
        return this.eventoDAOSQL.insertarEvento(evento);
    }
    
    public List listaTematica(){     
        return this.eventoDAOSQL.listaTematica();
    }
}
