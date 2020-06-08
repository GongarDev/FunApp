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
import model.Usuario;
import model.Valoracion;
import util.ConfiguracionBD;

/**
 *
 * @author melkart
 */
public class ValoracionDAOSQL implements ValoracionDAO {

    private String url;
    private String puerto_sgbd;
    private String host_sgbd;
    private String db;
    private String usuario;
    private String contrasenia;
    private Connection conexion;

    public ValoracionDAOSQL() {

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
    public List<Valoracion> listaValoraciones(int id_evento) {

        List<Valoracion> listaValoraciones = new ArrayList<Valoracion>();
        PreparedStatement sentencia = null;
        ResultSet resultado = null;

        try {
            abrirConexion();
            this.conexion.setAutoCommit(false);
            sentencia = this.conexion.prepareStatement(
                    "SELECT v.comentario, v.fecha, v.hora, v.puntaje, u.seudonimo "
                    + "FROM valoracion v, usuario u "
                    + "WHERE v.id_evento = ? "
                    + "AND v.id_usuario = u.id_usuario ");

            sentencia.setInt(1, id_evento);
            resultado = sentencia.executeQuery();
            String comentario = "";
            Date fecha;
            LocalTime hora;
            float puntaje;

            while (resultado.next()) {
                comentario = resultado.getString(1);
                fecha = resultado.getDate(2);
                hora = resultado.getTime(3).toLocalTime();
                puntaje = resultado.getFloat(4);
                
                Usuario u = new Usuario();
                u.setSeudonimo(resultado.getString(5));
                listaValoraciones.add(new Valoracion(comentario, fecha, hora, puntaje, u, null));
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
        return listaValoraciones;
    }

    @Override
    public boolean insertarValoracion(Valoracion valoracion) {
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        boolean insertado = false;
        Date fechaPublicacion = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
        valoracion.setFecha(fechaPublicacion);
        LocalTime horaPublicacion = LocalTime.from(LocalTime.now());
        valoracion.setHora(horaPublicacion);

        try {
            abrirConexion();
            sentencia = this.conexion.prepareStatement(
                    "INSERT INTO valoracion (comentario, fecha, hora, puntaje, id_evento, id_usuario) "
                    + "VALUES (?, ?, ?, ?, ?, ?)");

            sentencia.setString(1, valoracion.getComentario());
            sentencia.setDate(2, java.sql.Date.valueOf(valoracion.getFecha_publicacion_LocalDate()));
            sentencia.setTime(3, java.sql.Time.valueOf(valoracion.getHora()));
            sentencia.setFloat(4, valoracion.getPuntaje());
            sentencia.setInt(5, valoracion.getEvento().getId_evento());
            sentencia.setInt(6, valoracion.getUsuario().getId_usuario());

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
