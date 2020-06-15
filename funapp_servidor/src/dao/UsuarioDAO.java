package dao;

import java.util.List;
import model.Atributo;
import model.Credenciales;
import model.Entidad;
import model.Usuario;
import model.UsuarioAdmin;
import model.UsuarioEstandar;
import model.UsuarioResponsable;

/**
 *
 * @author melkart
 */
public interface UsuarioDAO {

    public boolean existeUsuario(String correo);

    public boolean existeNombreUsuario(String seudonimo);

    public boolean existeEntidadUsuario(int id_usuario);

    public UsuarioEstandar consultarUsEstandar(Credenciales credenciales);

    public UsuarioResponsable consultarUsResponsable(Credenciales credenciales);

    public UsuarioAdmin consultarUsAdmin(Credenciales credenciales);

    public boolean altaUsEstandar(UsuarioEstandar usuario);

    public boolean altaUsResponsable(UsuarioResponsable usuario);

    public boolean altaUsResponsableEscritorio(UsuarioResponsable usuario, Entidad entidad);

    public boolean actualizarUsEstandar(Usuario usuario);

    public boolean actualizarUsResponsable(UsuarioResponsable usuario);

    public boolean bajaUsEstandar(UsuarioEstandar usuario);

    public boolean bajaUsResponsable(UsuarioResponsable usuario);

    public UsuarioResponsable consultarResponsbaleParaEvento(int id_usuario);

    public UsuarioResponsable consultarResponsbalePerfil(int id_usuario);

    public List<Usuario> listaUsuariosAmigos(int id_usuario);

    public int cantidadSuscripciones(int id_usuario);

    public boolean eliminarAmigo(int id_usuarioAmigo, int id_usuario);

    public boolean existeSeguimientoUsuario(int id_usuarioAmigo, int id_usuario);

    public boolean insertarSeguimiento(int id_usuarioAmigo, int id_usuario);

    public boolean eliminarCuenta(int id_usuario);
    
    public List<Usuario> adminListaUsuariosEstandar();
    
    public List<UsuarioResponsable> adminListaUsuariosResponsable();    
    
    public List<Atributo> listaAtributosUsuario(int id_usuario);
    
}
