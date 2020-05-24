
package dao;

import java.util.HashSet;
import model.Ubicacion;

/**
 *
 * @author melkart
 */
public interface UbicacionDAO {
    
    public HashSet<Ubicacion> listaUbicacionesEvento(int id_evento);
    public HashSet<Ubicacion> listaUbicacionesCodigoPostal(String codigo_postal);

}
