package dao;

import model.Entidad;

/**
 *
 * @author melkart
 */
public interface EntidadDAO {
    
    public boolean altaEntidad(int id_usuario);
    public boolean actualizarEntidad(Entidad entidad);    
    public Entidad consultarEntidad(int id_usuario);
}
