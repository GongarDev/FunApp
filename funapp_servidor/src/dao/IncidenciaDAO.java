
package dao;

import java.util.List;
import model.Incidencia;
/**
 *
 * @author melkart
 */
public interface IncidenciaDAO {

    public List<Incidencia> listaIncidencia(String codigoPostal);

    public List<Incidencia> listaIncidenciasAdmin();
    
    public boolean insertarIncidencia(Incidencia incidencia); 
    
    public boolean adminEliminarIncidencia(int id_incidencia);
}
