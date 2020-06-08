
package dao;

import java.util.List;
import model.Incidencia;
/**
 *
 * @author melkart
 */
public interface IncidenciaDAO {

    public List<Incidencia> listaIncidencia(String codigoPostal);

    public boolean insertarIncidencia(Incidencia incidencia);    
}
