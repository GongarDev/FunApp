package controller;

import dao.AnuncioDAOSQL;
import dao.EntidadDAOSQL;
import dao.EventoDAOSQL;
import dao.IncidenciaDAOSQL;
import dao.PublicacionDAOSQL;
import dao.UsuarioDAOSQL;
import dao.ValoracionDAOSQL;
import java.util.List;
import model.Anuncio;
import model.Atributo;
import model.Credenciales;
import model.Entidad;
import model.Evento;
import model.Incidencia;
import model.Publicacion;
import model.Usuario;
import model.UsuarioAdmin;
import model.UsuarioEstandar;
import model.UsuarioResponsable;
import model.Valoracion;

/**
 *
 * @author melkart
 */
public class Controlador {

    UsuarioDAOSQL usuarioDAOSQL;
    EventoDAOSQL eventoDAOSQL;
    EntidadDAOSQL entidadDAOSQL;
    PublicacionDAOSQL publicacionDAOSQL;
    ValoracionDAOSQL valoracionDAOSQL;
    IncidenciaDAOSQL incidenciaDAOSQL;
    AnuncioDAOSQL anuncioDAOSQL;

    public Controlador() {
        this.usuarioDAOSQL = new UsuarioDAOSQL();
        this.eventoDAOSQL = new EventoDAOSQL();
        this.entidadDAOSQL = new EntidadDAOSQL();
        this.publicacionDAOSQL = new PublicacionDAOSQL();
        this.valoracionDAOSQL = new ValoracionDAOSQL();
        this.incidenciaDAOSQL = new IncidenciaDAOSQL();
        this.anuncioDAOSQL = new AnuncioDAOSQL();
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

    public UsuarioAdmin buscarUsAdmin(Credenciales credenciales) {
        return this.usuarioDAOSQL.consultarUsAdmin(credenciales);
    }

    public boolean insertarUsEstandar(UsuarioEstandar usuario) {
        return this.usuarioDAOSQL.altaUsEstandar(usuario);
    }

    public boolean insertarUsResponsable(UsuarioResponsable usuario) {
        return this.usuarioDAOSQL.altaUsResponsable(usuario);
    }

    public boolean insertarUsResponsableEscritorio(UsuarioResponsable usuario, Entidad entidad) {
        return this.usuarioDAOSQL.altaUsResponsableEscritorio(usuario, entidad);
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

    public UsuarioResponsable consultarUsuarioResponsable(int id_usuario) {
        return this.usuarioDAOSQL.consultarResponsbalePerfil(id_usuario);
    }

    public boolean actualizarUsuarioResponsable(UsuarioResponsable usuarioResponsable) {
        return this.usuarioDAOSQL.actualizarUsResponsable(usuarioResponsable);
    }

    public boolean actualizarUsuarioEstandar(Usuario usuario) {
        return this.usuarioDAOSQL.actualizarUsEstandar(usuario);
    }

    public Entidad consultarEntidad(int id_usuario) {
        return this.entidadDAOSQL.consultarEntidad(id_usuario);
    }

    public boolean actualizarEntidad(Entidad entidad) {
        return this.entidadDAOSQL.actualizarEntidad(entidad);
    }

    public List<Evento> suscripcionesEventos(int id_usuario) {
        return this.eventoDAOSQL.suscripcionesEventos(id_usuario);
    }

    public boolean suscribirseEvento(int id_evento, int id_usuario) {
        return this.eventoDAOSQL.suscribirseEvento(id_evento, id_usuario);
    }

    public boolean desuscribirseEvento(int id_evento, int id_usuario) {
        return this.eventoDAOSQL.desuscribirseEvento(id_evento, id_usuario);
    }

    public List<Evento> listaEventosProximos(String codigo_postal) {
        return this.eventoDAOSQL.listaEventosProximos(codigo_postal);
    }

    public List<Evento> listaEventosRecomendados(String codigo_postal, int id_usuario) {
        return this.eventoDAOSQL.listaEventosRecomendados(codigo_postal, id_usuario);
    }

    public List<Publicacion> listaPublicaciones(int id_evento) {
        return this.publicacionDAOSQL.listaPublicaciones(id_evento);
    }

    public boolean insertarPublicacion(Publicacion publicacion, int id_evento, int id_usuario) {
        return this.publicacionDAOSQL.insertarPublicacion(publicacion, id_evento, id_usuario);
    }

    public List<Valoracion> listaValoraciones(int id_evento) {
        return this.valoracionDAOSQL.listaValoraciones(id_evento);
    }

    public boolean insertarValoracion(Valoracion valoracion) {
        return this.valoracionDAOSQL.insertarValoracion(valoracion);
    }

    public boolean existeSuscritoEvento(int id_evento, int id_usuario) {
        return this.eventoDAOSQL.existeSuscritoEvento(id_evento, id_usuario);
    }

    public boolean aumentarPuntosEvento(int id_evento, int id_usuario) {
        return this.eventoDAOSQL.aumentarPuntosEvento(id_evento, id_usuario);
    }

    public boolean existeEntidadUsuario(int id_usuario) {
        return this.usuarioDAOSQL.existeEntidadUsuario(id_usuario);
    }

    public List<Usuario> listaUsuariosAmigos(int id_usuario) {
        return this.usuarioDAOSQL.listaUsuariosAmigos(id_usuario);
    }

    public int cantidadSuscripciones(int id_usuario) {
        return this.usuarioDAOSQL.cantidadSuscripciones(id_usuario);
    }

    public boolean eliminarAmigo(int id_usuarioAmigo, int id_usuario) {
        return this.usuarioDAOSQL.eliminarAmigo(id_usuarioAmigo, id_usuario);
    }

    public boolean existeSeguimientoUsuario(int id_usuarioAmigo, int id_usuario) {
        return this.usuarioDAOSQL.existeSeguimientoUsuario(id_usuarioAmigo, id_usuario);
    }

    public boolean insertarSeguimiento(int id_usuarioAmigo, int id_usuario) {
        return this.usuarioDAOSQL.insertarSeguimiento(id_usuarioAmigo, id_usuario);
    }

    public boolean eliminarCuenta(int id_usuario) {
        return this.usuarioDAOSQL.eliminarCuenta(id_usuario);
    }

    public boolean insertarIncidencia(Incidencia incidencia) {
        return this.incidenciaDAOSQL.insertarIncidencia(incidencia);
    }

    public List<Anuncio> listaAnunciosAdmin() {
        return this.anuncioDAOSQL.listaPublicacionesAdmin();
    }

    public boolean adminInsertarAnuncio(Anuncio anuncio) {
        return this.anuncioDAOSQL.adminInsertarAnuncio(anuncio);
    }

    public boolean adminEliminarAnuncio(int id_anuncio) {
        return this.anuncioDAOSQL.adminEliminarAnuncio(id_anuncio);
    }

    public List<Incidencia> listaIncidenciasAdmin() {
        return this.incidenciaDAOSQL.listaIncidenciasAdmin();
    }

    public boolean adminEliminarIncidencia(int id_incidencia) {
        return this.incidenciaDAOSQL.adminEliminarIncidencia(id_incidencia);
    }

    public List<Evento> listaEventosAdmin() {
        return this.eventoDAOSQL.listaEventosAdmin();
    }

    public boolean adminEliminarEventos(int id_evento) {
        return this.eventoDAOSQL.eliminarEventoAdmin(id_evento);
    }

    public List<Usuario> adminListaUsuariosEstandar() {
        return this.usuarioDAOSQL.adminListaUsuariosEstandar();
    }

    public List<UsuarioResponsable> adminListaUsuariosResponsable() {
        return this.usuarioDAOSQL.adminListaUsuariosResponsable();
    }
    public List<Atributo> listaAtributosUsuario(int id_usuario) {
        return this.usuarioDAOSQL.listaAtributosUsuario(id_usuario);      
    }
}
