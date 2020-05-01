package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import model.Entidad;
import util.ConfiguracionBD;

/**
 *
 * @author melkart
 */
public class EntidadDAOSQL implements EntidadDAO {

    private String url;
    private String puerto_sgbd;
    private String host_sgbd;
    private String db;
    private String usuario;
    private String contrasenia;
    private Connection conexion;

    public EntidadDAOSQL() {

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
    public boolean altaEntidad(Entidad entidad) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
