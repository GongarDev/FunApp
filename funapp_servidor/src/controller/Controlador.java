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

    public boolean actualizarEvento(Evento evento) {
        return this.eventoDAOSQL.actualizarEvento(evento);
    }

    public boolean activarEvento(Evento evento) {
        return this.eventoDAOSQL.activarEvento(evento);
    }

    public List listaTematica() {
        return this.eventoDAOSQL.listaTematica();
    }

    public List<Evento> listaEventosPorUsuarioResposable(int id_usuario) {
        return this.eventoDAOSQL.listaEventosPorUsuarioResponsable(id_usuario);
    }

    public int suscritosEvento(int id_evento) {
        return this.eventoDAOSQL.suscritosEvento(id_evento);
    }

    public List<Evento> listaEventosPorCodigoPostal(String codigo_postal) {
        return this.eventoDAOSQL.listaEventosPorCodigoPostal(codigo_postal);
    }

    public List<Evento> historialEventos(int id_usuario) {
        return this.eventoDAOSQL.historialEventos(id_usuario);
    }

    public List<Evento> listaEventosExplorar(String codigo_postal, String nombreTematica) {
        return this.eventoDAOSQL.listaEventosExplorar(codigo_postal, nombreTematica);
    }
}
