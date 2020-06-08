package dao;

import java.util.List;
import model.Publicacion;

/**
 *
 * @author melkart
 */
public interface PublicacionDAO {

    public List<Publicacion> listaPublicaciones(int id_evento);

    public boolean insertarPublicacion(Publicacion publicacion, int id_evento, int id_usuario);
}
