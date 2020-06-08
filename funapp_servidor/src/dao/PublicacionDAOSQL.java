package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.Publicacion;
import util.ConfiguracionBD;

/**
 *
 * @author melkart
 */
public class PublicacionDAOSQL implements PublicacionDAO {

    private String url;
    private String puerto_sgbd;
    private String host_sgbd;
    private String db;
    private String usuario;
    private String contrasenia;
    private Connection conexion;

    public PublicacionDAOSQL() {

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
    public List<Publicacion> listaPublicaciones(int id_evento) {
        
        List<Publicacion> listaPublicaciones = new ArrayList<Publicacion>();
        PreparedStatement sentencia = null;
        ResultSet resultado = null;

        try {
            abrirConexion();
            this.conexion.setAutoCommit(false);
            sentencia = this.conexion.prepareStatement(
                    "SELECT m.mensaje, m.fecha, m.hora "
                    + "FROM mensaje m "
                    + "WHERE m.id_evento = ? ");

            sentencia.setInt(1, id_evento);
            resultado = sentencia.executeQuery();
            String mensaje = "";
            Date fecha;
            LocalTime hora;

            while (resultado.next()) {
                mensaje = resultado.getString(1);
                fecha = resultado.getDate(2);
                hora = resultado.getTime(3).toLocalTime();
                listaPublicaciones.add(new Publicacion(mensaje, fecha, hora));
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
        return listaPublicaciones;
    }

    @Override
    public boolean insertarPublicacion(Publicacion publicacion, int id_evento, int id_usuario) {
       
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        boolean insertado = false;
        Date fechaPublicacion = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
        publicacion.setFecha(fechaPublicacion);
        LocalTime horaPublicacion = LocalTime.from(LocalTime.now());
        publicacion.setHora(horaPublicacion);

        try {
            abrirConexion();
            sentencia = this.conexion.prepareStatement(
                    "INSERT INTO mensaje (mensaje, fecha, hora, id_evento, id_usuario) "
                    + "VALUES (?, ?, ?, ?, ?)");

            sentencia.setString(1, publicacion.getMensaje());
            sentencia.setDate(2, java.sql.Date.valueOf(publicacion.getFecha_publicacion_LocalDate()));
            sentencia.setTime(3, java.sql.Time.valueOf(publicacion.getHora()));
            sentencia.setInt(4, id_evento);
            sentencia.setInt(5, id_usuario);

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
}
