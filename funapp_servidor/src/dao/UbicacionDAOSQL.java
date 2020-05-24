package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import model.Ubicacion;
import util.ConfiguracionBD;

/**
 *
 * @author melkart
 */
public class UbicacionDAOSQL implements UbicacionDAO {

    private String url;
    private String puerto_sgbd;
    private String host_sgbd;
    private String db;
    private String usuario;
    private String contrasenia;
    private Connection conexion;

    public UbicacionDAOSQL() {

        ConfiguracionBD c = new ConfiguracionBD();
        c.importar();
        this.puerto_sgbd = c.getPuerto_sgbd();
        this.host_sgbd = c.getHost_sgbd();
        this.db = c.getDb();
        this.usuario = c.getUsuario();
        this.contrasenia = c.getContrasenia();
        this.url = "jdbc:mariadb://" + this.host_sgbd + ":" + this.puerto_sgbd + "/" + this.db;
        this.conexion = null;
        pruebaConexion();
    }

    public void pruebaConexion() {
        try {
            this.conexion = DriverManager.getConnection(url, usuario, contrasenia);
            this.conexion.close();
        } catch (SQLException e) {
            System.out.println("Error al conectar con el servidor.");
            System.exit(0);
        }
        System.out.println("Conectado a la base de datos.");
    }

    public Connection abrirConexion() {
        try {
            this.conexion = DriverManager.getConnection(this.url, this.usuario, this.contrasenia);
        } catch (SQLException e) {
            System.out.println("Error al conectar con el servidor.");
            System.exit(0);
        }
        return this.conexion;
    }

    public void cerrarConexion() {
        try {
            this.conexion.close();
        } catch (SQLException e) {

            System.out.println("No se puede cerrar la conexión a la base de datos.");
        }
    }

    @Override
    public HashSet<Ubicacion> listaUbicacionesEvento(int id_evento) {

        HashSet<Ubicacion> listaUbicaciones = new HashSet<>();
        
        PreparedStatement sentenciaUbicaciones = null;
        ResultSet resultado = null;
        
        try {

            abrirConexion();
            sentenciaUbicaciones = this.conexion.prepareStatement(
                    "SELECT u.id_ubicacion, u.calle, u.codigo_postal, u.latitud, u.longitud "
                    + "FROM ubicacion u, evento_ubicacion eu "
                    + "WHERE u.id_ubicacion = eu.id_ubicacion "
                    + "AND eu.id_evento = ? ");
            
            sentenciaUbicaciones.setInt(1, id_evento);
            resultado = sentenciaUbicaciones.executeQuery(); 
            
            int id_ubicacion = 0;
            String calle, codigo_postal;
            double latitud, longitud;
            
            while (resultado.next()) {
                id_ubicacion = resultado.getInt(1);
                calle = resultado.getString(2);
                codigo_postal = resultado.getString(3);
                latitud = resultado.getDouble(4);
                longitud = resultado.getDouble(5);
                listaUbicaciones.add(new Ubicacion(id_ubicacion, calle,
                     codigo_postal, latitud, longitud));
            }            
            
         } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultado != null) {
                    resultado.close();
                }
                if (sentenciaUbicaciones != null) {
                    sentenciaUbicaciones.close();
                }
                if (this.conexion != null) {
                    cerrarConexion();
                }
            } catch (SQLException ex) {
                ex.getMessage();
            }
        }           
        return listaUbicaciones;
    }

    @Override
    public HashSet<Ubicacion> listaUbicacionesCodigoPostal(String codigo_postal) {
        
        HashSet<Ubicacion> listaUbicaciones = new HashSet<>();
        
        PreparedStatement sentenciaUbicaciones = null;
        ResultSet resultado = null;
        
        try {

            abrirConexion();
            sentenciaUbicaciones = this.conexion.prepareStatement(
                    "SELECT u.id_ubicacion, u.calle, u.codigo_postal, u.latitud, u.longitud "
                    + "FROM ubicacion u "
                    + "WHERE u.codigo_postal = ? ");
            
            sentenciaUbicaciones.setString(1, codigo_postal);
            resultado = sentenciaUbicaciones.executeQuery(); 
            
            int id_ubicacion = 0;
            String calle;
            double latitud, longitud;
            
            while (resultado.next()) {
                id_ubicacion = resultado.getInt(1);
                calle = resultado.getString(2);
                codigo_postal = resultado.getString(3);
                latitud = resultado.getDouble(4);
                longitud = resultado.getDouble(5);
                listaUbicaciones.add(new Ubicacion(id_ubicacion, calle,
                     codigo_postal, latitud, longitud));
            }            
            
         } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultado != null) {
                    resultado.close();
                }
                if (sentenciaUbicaciones != null) {
                    sentenciaUbicaciones.close();
                }
                if (this.conexion != null) {
                    cerrarConexion();
                }
            } catch (SQLException ex) {
                ex.getMessage();
            }
        }           
        return listaUbicaciones;
    }
    
}
