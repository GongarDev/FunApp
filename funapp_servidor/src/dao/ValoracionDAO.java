package dao;

import java.util.List;
import model.Valoracion;

/**
 *
 * @author melkart
 */
public interface ValoracionDAO {

    public List<Valoracion> listaValoraciones(int id_evento);

    public boolean insertarValoracion(Valoracion valoracion);
}
