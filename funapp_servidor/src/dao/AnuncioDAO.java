package dao;

import java.util.List;
import model.Anuncio;

/**
 *
 * @author melkart
 */
public interface AnuncioDAO {

    public List listaPublicacionesAdmin();
    
    public boolean adminInsertarAnuncio(Anuncio anuncio);
    
    public boolean adminEliminarAnuncio(int id_anuncio);

}
