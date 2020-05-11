package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import model.Evento;
import model.Tematica;
import model.Ubicacion;
import model.UsuarioResponsable;
import util.ConfiguracionBD;

/**
 *
 * @author melkart
 */
public class EventoDAOSQL implements EventoDAO {

    private String url;
    private String puerto_sgbd;
    private String host_sgbd;
    private String db;
    private String usuario;
    private String contrasenia;
    private Connection conexion;

    public EventoDAOSQL() {

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
    public List listaTematica() {

        List<Tematica> listaTematica = new ArrayList<Tematica>();

        PreparedStatement sentencia = null;
        ResultSet resultado = null;

        try {

            abrirConexion();
            sentencia = this.conexion.prepareStatement(
                    "SELECT * "
                    + "FROM tematica ");

            resultado = sentencia.executeQuery();

            int id_tematica;
            String nombre;
            String descripcion;
            int edad_legal;

            while (resultado.next()) {

                id_tematica = resultado.getInt(1);
                nombre = resultado.getString(2);
                descripcion = resultado.getString(3);
                edad_legal = resultado.getInt(4);

                listaTematica.add(new Tematica(id_tematica, nombre, descripcion, edad_legal));
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
        return listaTematica;
    }

    @Override
    public boolean insertarEvento(Evento evento) {

        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        int id_evento = 0;
        List<Integer> id_ubicaciones = new ArrayList<>();
        boolean insertado = false;
        Date fechaPublicacion = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
        evento.setFecha_publicacion(fechaPublicacion);

        try {
            abrirConexion();

            /*Primero insertamos el evento junto con la ID del temática y usuario
            porque ya lo tenemos de antes.*/
            sentencia = this.conexion.prepareStatement(
                    "INSERT INTO evento (nombre, fecha_publicacion, fecha_evento, hora_inicio, hora_fin,"
                    + " codigo_qr, id_tematica, id_usuario) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);

            sentencia.setString(1, evento.getNombre());
            sentencia.setDate(2, java.sql.Date.valueOf(evento.getFecha_publicacion_LocalDate()));
            sentencia.setDate(3, java.sql.Date.valueOf(evento.getFecha_evento_LocalDate()));
            sentencia.setTime(4, java.sql.Time.valueOf(evento.getHora_inicio()));
            sentencia.setTime(5, java.sql.Time.valueOf(evento.getHora_fin()));
            sentencia.setString(6, evento.getCodigoQR());
            sentencia.setInt(7, evento.getTematica().getId_tematica());
            sentencia.setInt(8, evento.getUsuario().getId_usuario());

            int affectedRows = sentencia.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("No se puede insertar los datos.");
                /*Si ha sido insertdao, guardamos la clave para posteriormente
            relacionarla con las ubicaciones en la tabla muchos a muchos.
                 */
            } else if (affectedRows == 1) {
                resultado = sentencia.getGeneratedKeys();
                if (resultado.next()) {
                    id_evento = resultado.getInt(1);

                }

                /*Recuperamos la lista de ubicaciones del objeto evento que en el
                formulario guardó, y recorremos uno a uno. Comprobamos si existe,
                y de ser así guardamos su clave en una lista, y de no ser así, 
                lo insertamos e igualmente pedimos su clave para guardar en la misma lista.                
                 */
                HashSet<Ubicacion> listaUbicaciones = evento.getUbicaciones();

                for (Ubicacion ubicacion : listaUbicaciones) {

                    sentencia = this.conexion.prepareStatement(
                            "SELECT id_ubicacion "
                            + "FROM ubicacion "
                            + "WHERE calle = ? "
                            + "AND codigo_postal = ? "
                            + "AND latitud = ? "
                            + "AND longitud = ? ");

                    sentencia.setString(1, ubicacion.getCalle());
                    sentencia.setInt(2, ubicacion.getCodigo_postal());
                    sentencia.setDouble(3, ubicacion.getLatitud());
                    sentencia.setDouble(4, ubicacion.getLongitud());
                    resultado = sentencia.executeQuery();

                    if (resultado.next()) {
                        id_ubicaciones.add(resultado.getInt(1));
                    } else {

                        sentencia = this.conexion.prepareStatement(
                                "INSERT INTO ubicacion (calle, codigo_postal, latitud, longitud) "
                                + "VALUES (?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);

                        sentencia.setString(1, ubicacion.getCalle());
                        sentencia.setInt(2, ubicacion.getCodigo_postal());
                        sentencia.setDouble(3, ubicacion.getLatitud());
                        sentencia.setDouble(4, ubicacion.getLongitud());
                        affectedRows = sentencia.executeUpdate();

                        if (affectedRows == 0) {
                            throw new SQLException("No se puede insertar los datos");
                        } else if (affectedRows == 1) {
                            resultado = sentencia.getGeneratedKeys();
                            if (resultado.next()) {
                                id_ubicaciones.add(resultado.getInt(1));
                            }
                        }
                    }
                }

                /*Recorremos ahora la listas de claves de ubicaciones para relacionarla
                en la tabla muchos a muchos con el evento.
                 */
                for (Integer id_ubicacion : id_ubicaciones) {
                    sentencia = this.conexion.prepareStatement(
                            "INSERT INTO evento_ubicacion (id_evento, id_ubicacion) "
                            + "VALUES (?, ?)");
                    sentencia.setInt(1, id_evento);
                    sentencia.setInt(2, id_ubicacion);
                    affectedRows = sentencia.executeUpdate();
                    if (affectedRows == 0) {
                        insertado = false;
                        throw new SQLException("No se puede insertar los datos.");
                    } else if (affectedRows == 1) {
                        insertado = true;
                    }
                }
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
