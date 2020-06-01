package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    public boolean altaEntidad(int id_usuario) {

        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        boolean insertado = false;
        try {
            abrirConexion();
            sentencia = this.conexion.prepareStatement(
                    "INSERT INTO entidad (id_usuario) "
                    + "VALUES (?)");

            sentencia.setInt(1, id_usuario);

            int affectedRows = sentencia.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("No se puede insertar los datos");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (sentencia != null) {
                    sentencia.close();
                }
                if (resultado != null) {
                    resultado.close();
                }
                if (this.conexion != null) {
                    cerrarConexion();
                }
            } catch (SQLException ex) {
                ex.getMessage();
            }
        }
        return insertado;
    }

    @Override
    public Entidad consultarEntidad(int id_usuario) {

        PreparedStatement sentencia = null;
        ResultSet resultado = null;

        Entidad entidad = null;

        try {
            abrirConexion();
            sentencia = this.conexion.prepareStatement(
                    "SELECT e.id_entidad, e.nombre, e.nif, e.calle, "
                    + "e.provincia, e.localidad, e.codigo_postal, e.telefono "
                    + "FROM entidad e "
                    + "WHERE e.id_usuario=? ");

            sentencia.setInt(1, id_usuario);
            resultado = sentencia.executeQuery();

            while (resultado.next()) {

                entidad = new Entidad(
                        resultado.getInt(1),
                        resultado.getString(2),
                        resultado.getString(3),
                        resultado.getString(4),
                        resultado.getString(5),
                        resultado.getString(6),
                        resultado.getString(7),
                        resultado.getString(8),
                        id_usuario
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultado != null) {
                    resultado.close();
                }
                if (sentencia != null) {
                    sentencia.close();
                }
                if (this.conexion != null) {
                    cerrarConexion();
                }
            } catch (SQLException ex) {
                ex.getMessage();
            }
        }
        return entidad;
    }

    @Override
    public boolean actualizarEntidad(Entidad entidad) {

        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        boolean insertado = false;

        try {
            abrirConexion();
            this.conexion.setAutoCommit(false);
            sentencia = this.conexion.prepareStatement(
                    "UPDATE entidad SET nombre = ?, nif = ?, calle = ?, "
                    + "provincia = ?, localidad = ?, codigo_postal = ?, telefono = ? "
                    + "WHERE id_entidad = ? ");

            sentencia.setString(1, entidad.getNombre());
            sentencia.setString(2, entidad.getNif());
            sentencia.setString(3, entidad.getCalle());
            sentencia.setString(4, entidad.getProvincia());
            sentencia.setString(5, entidad.getLocalidad());
            sentencia.setString(6, entidad.getCodigo_postal());
            sentencia.setString(7, entidad.getTelefono());
            sentencia.setInt(8, entidad.getId_entidad());

            int affectedRows = sentencia.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("No se puede actualizar los datos.");

            }

            this.conexion.commit();
            insertado = true;
        } catch (SQLException e) {
            try {
                this.conexion.rollback();
                e.printStackTrace();
            } catch (SQLException ex) {
                Logger.getLogger(EventoDAOSQL.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            try {
                if (sentencia != null) {
                    sentencia.close();
                }
                if (resultado != null) {
                    resultado.close();
                }
                if (this.conexion != null) {
                    cerrarConexion();
                }
            } catch (SQLException ex) {
                ex.getMessage();
            }
        }
        return insertado;
    }
}
