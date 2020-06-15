package util;

import java.util.Comparator;
import model.Evento;

/**
 *
 * @author melkart
 */
public class FechaInicioCompare implements Comparator<Evento> {

    @Override
    public int compare(Evento e1, Evento e2) {

        return (e1.getFecha_evento().compareTo(e2.getFecha_evento()));
    }
}
