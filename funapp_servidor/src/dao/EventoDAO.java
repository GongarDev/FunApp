package dao;

import java.util.List;
import model.Evento;
import model.Usuario;
import model.UsuarioResponsable;

/**
 *
 * @author melkart
 */
public interface EventoDAO {

    public List<Evento> listaEventosPorUsuarioResponsable(int id_usuario);

    public boolean insertarEvento(Evento evento);

    public boolean actualizarEvento(Evento evento);

    public boolean activarEvento(Evento evento);

    public int suscritosEvento(int id_evento);

    public List listaTematica();

    public List<Evento> listaEventosPorCodigoPostal(String codigo_postal);

    public List<Evento> historialEventos(int id_usuario);

    public List<Evento> listaEventosExplorar(String codigo_postal, String nombreTematica);

    public List<Evento> suscripcionesEventos(int id_usuario);

    public boolean suscribirseEvento(int id_evento, int id_usuario);

    public boolean desuscribirseEvento(int id_evento, int id_usuario);

    public List<Evento> listaEventosProximos(String codigo_postal);

    public List<Evento> listaEventosRecomendados(String codigo_postal);

}
