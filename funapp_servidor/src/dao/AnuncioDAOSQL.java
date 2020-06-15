
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.Anuncio;
import util.ConfiguracionBD;

/**
 *
 * @author melkart
 */
public class AnuncioDAOSQL implements AnuncioDAO{

    private String url;
    private String puerto_sgbd;
    private String host_sgbd;
    private String db;
    private String usuario;
    private String contrasenia;
    private Connection conexion;

    public AnuncioDAOSQL() {

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
    public List listaPublicacionesAdmin() {
        
        List<Anuncio> listaAnuncios = new ArrayList<Anuncio>();
        PreparedStatement sentencia = null;
        ResultSet resultado = null;

        try {

            abrirConexion();
            sentencia = this.conexion.prepareStatement(
                    "SELECT id_anuncio, nombre, descripcion, fecha, id_usuario "
                    + "FROM anuncio ");

            resultado = sentencia.executeQuery();

            int id_anuncio;
            String nombre;
            String descripcion;
            Date fecha;
            int id_usuario;

            while (resultado.next()) {

                id_anuncio = resultado.getInt(1);
                nombre = resultado.getString(2);
                descripcion = resultado.getString(3);
                fecha = resultado.getDate(4);
                id_usuario = resultado.getInt(5);

                listaAnuncios.add(new Anuncio(id_anuncio, nombre, descripcion, fecha, id_usuario));
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
        return listaAnuncios;
    }   

    @Override
    public boolean adminInsertarAnuncio(Anuncio anuncio) {
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        boolean insertado = false;
        Date fechaPublicacion = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
        anuncio.setFecha(fechaPublicacion);

        try {
            abrirConexion();
            sentencia = this.conexion.prepareStatement(
                    "INSERT INTO anuncio (nombre, descripcion, fecha, id_usuario) "
                    + "VALUES (?, ?, ?, ?)");

            sentencia.setString(1, anuncio.getNombre());
            sentencia.setString(2, anuncio.getMensaje());            
            sentencia.setDate(3, java.sql.Date.valueOf(anuncio.getFecha_publicacion_LocalDate()));
            sentencia.setInt(4, anuncio.getId_usuario());

            int affectedRows = sentencia.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("No se puede insertar los datos");
            }else if (affectedRows == 1) {
                insertado = true;
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
    public boolean adminEliminarAnuncio(int id_anuncio) {
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        boolean eliminado = false;

        try {
            abrirConexion();
            sentencia = this.conexion.prepareStatement(
                    "DELETE FROM anuncio "
                    + "WHERE id_anuncio = ? ");

            sentencia.setInt(1, id_anuncio);
            int affectedRows = sentencia.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("No se puede eliminar los datos");
            }
            eliminado = true;
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
        return eliminado;
    }
}

