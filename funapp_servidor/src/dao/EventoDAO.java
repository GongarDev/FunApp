
package dao;

import java.util.List;
import model.Evento;

/**
 *
 * @author melkart
 */
public interface EventoDAO {
    
    public boolean insertarEvento(Evento evento);
    public List listaTematica();
}
