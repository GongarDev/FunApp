package dao;

import java.util.List;
import model.Evento;

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
}
