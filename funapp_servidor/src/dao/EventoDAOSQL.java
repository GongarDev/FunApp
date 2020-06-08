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
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private UbicacionDAOSQL ubicacionDAOSQL;
    private UsuarioDAOSQL usuarioDAOSQL;

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
        this.ubicacionDAOSQL = new UbicacionDAOSQL();
        this.usuarioDAOSQL = new UsuarioDAOSQL();
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
            this.conexion.setAutoCommit(false);
            /*Primero insertamos el evento junto con la ID del temática y usuario
            porque ya lo tenemos de antes.*/
            sentencia = this.conexion.prepareStatement(
                    "INSERT INTO evento (nombre, descripcion, fecha_publicacion, fecha_evento, hora_inicio, hora_fin, "
                    + " codigo_qr, id_tematica, id_usuario, activo) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);

            sentencia.setString(1, evento.getNombre());
            sentencia.setString(2, evento.getDescripcion());
            sentencia.setDate(3, java.sql.Date.valueOf(evento.getFecha_publicacion_LocalDate()));
            sentencia.setDate(4, java.sql.Date.valueOf(evento.getFecha_evento_LocalDate()));
            sentencia.setTime(5, java.sql.Time.valueOf(evento.getHora_inicio()));
            sentencia.setTime(6, java.sql.Time.valueOf(evento.getHora_fin()));
            sentencia.setString(7, evento.getCodigoQR());
            sentencia.setInt(8, evento.getTematica().getId_tematica());
            sentencia.setInt(9, evento.getUsuario().getId_usuario());
            sentencia.setBoolean(10, true);

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
                    sentencia.setString(2, ubicacion.getCodigo_postal());
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
                        sentencia.setString(2, ubicacion.getCodigo_postal());
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
            this.conexion.commit();
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

    @Override
    public boolean actualizarEvento(Evento evento) {

        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        List<Integer> id_ubicaciones = new ArrayList<>();
        boolean insertado = false;
        //Date fechaPublicacion = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
        //evento.setFecha_publicacion(fechaPublicacion);

        try {
            abrirConexion();
            this.conexion.setAutoCommit(false);
            /*Primero insertamos el evento junto con la ID del temática y usuario
            porque ya lo tenemos de antes.*/
            sentencia = this.conexion.prepareStatement(
                    "UPDATE evento SET nombre = ?, descripcion = ?, fecha_publicacion = ?, "
                    + "fecha_evento = ?, hora_inicio = ?, hora_fin = ?, "
                    + " codigo_qr = ?, id_tematica = ?, id_usuario = ?, activo = ? "
                    + "WHERE id_evento = ? ");

            sentencia.setString(1, evento.getNombre());
            sentencia.setString(2, evento.getDescripcion());
            sentencia.setDate(3, java.sql.Date.valueOf(evento.getFecha_publicacion_LocalDate()));
            sentencia.setDate(4, java.sql.Date.valueOf(evento.getFecha_evento_LocalDate()));
            sentencia.setTime(5, java.sql.Time.valueOf(evento.getHora_inicio()));
            sentencia.setTime(6, java.sql.Time.valueOf(evento.getHora_fin()));
            sentencia.setString(7, evento.getCodigoQR());
            sentencia.setInt(8, evento.getTematica().getId_tematica());
            sentencia.setInt(9, evento.getUsuario().getId_usuario());
            sentencia.setBoolean(10, evento.isActivo());
            sentencia.setInt(11, evento.getId_evento());

            int affectedRows = sentencia.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("No se puede actualizar los datos.");

            } else if (affectedRows == 1) {

                HashSet<Ubicacion> listaUbicaciones = evento.getUbicaciones();

                for (Ubicacion ubicacion : listaUbicaciones) {

                    sentencia = this.conexion.prepareStatement(
                            "SELECT id_ubicacion "
                            + "FROM ubicacion "
                            + "WHERE calle = ? "
                            + "AND codigo_postal = ? ");

                    sentencia.setString(1, ubicacion.getCalle());
                    sentencia.setString(2, ubicacion.getCodigo_postal());
                    resultado = sentencia.executeQuery();

                    if (resultado.next()) {

                    } else {

                        sentencia = this.conexion.prepareStatement(
                                "INSERT INTO ubicacion (calle, codigo_postal, latitud, longitud) "
                                + "VALUES (?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);

                        sentencia.setString(1, ubicacion.getCalle());
                        sentencia.setString(2, ubicacion.getCodigo_postal());
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

                for (Integer id_ubicacion : id_ubicaciones) {
                    sentencia = this.conexion.prepareStatement(
                            "INSERT INTO evento_ubicacion (id_evento, id_ubicacion) "
                            + "VALUES (?, ?)");
                    sentencia.setInt(1, evento.getId_evento());
                    sentencia.setInt(2, id_ubicacion);
                    affectedRows = sentencia.executeUpdate();
                    if (affectedRows == 0) {
                        throw new SQLException("No se puede insertar los datos.");
                    }
                }
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

    @Override
    public boolean activarEvento(Evento evento) {

        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        boolean insertado = false;

        try {
            abrirConexion();
            this.conexion.setAutoCommit(false);
            sentencia = this.conexion.prepareStatement(
                    "UPDATE evento SET activo = ? "
                    + "WHERE id_evento = ? ");

            sentencia.setBoolean(1, evento.isActivo());
            sentencia.setInt(2, evento.getId_evento());

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

    @Override
    public List<Evento> listaEventosPorUsuarioResponsable(int id_usuario) {

        List<Evento> listaEventos = new ArrayList<Evento>();

        PreparedStatement sentenciaEventos = null;
        PreparedStatement sentenciaUbicaciones = null;
        ResultSet resultado = null;

        try {

            abrirConexion();
            this.conexion.setAutoCommit(false);
            sentenciaEventos = this.conexion.prepareStatement(
                    "SELECT e.id_evento, e.nombre, e.descripcion, e.fecha_publicacion, e.fecha_evento, "
                    + "e.hora_inicio, e.hora_fin, e.codigo_qr, e.activo, "
                    + "t.id_tematica, t.nombre, t.descripcion, t.edad_legal "
                    + "FROM evento e, tematica t, responsable r "
                    + "WHERE e.id_usuario = r.id_usuario "
                    + "AND r.id_usuario = ? "
                    + "AND e.id_tematica = t.id_tematica "
                    + "AND e.fecha_evento > now() ");

            sentenciaEventos.setInt(1, id_usuario);
            resultado = sentenciaEventos.executeQuery();

            int id_evento, id_tematica = 0, edad_legal = 0;
            String nombreEvento, descripcionEvento, nombreTematica = "", descripcionTematica = "";
            Date fecha_publicacion, fecha_evento;
            LocalTime hora_inicio, hora_fin;
            boolean activo;
            Tematica tematica;

            while (resultado.next()) {

                id_evento = resultado.getInt(1);
                nombreEvento = resultado.getString(2);
                descripcionEvento = resultado.getString(3);
                fecha_publicacion = resultado.getDate(4);
                fecha_evento = resultado.getDate(5);
                hora_inicio = resultado.getTime(6).toLocalTime();
                hora_fin = resultado.getTime(7).toLocalTime();
                // 8 codigo qr
                activo = resultado.getBoolean(9);

                id_tematica = resultado.getInt(10);
                nombreTematica = resultado.getString(11);
                descripcionTematica = resultado.getString(12);
                edad_legal = resultado.getInt(13);
                tematica = new Tematica(id_tematica, nombreTematica, descripcionTematica, edad_legal);

                HashSet<Ubicacion> listaUbicaciones = this.ubicacionDAOSQL.listaUbicacionesEvento(id_evento);

                UsuarioResponsable usuario = this.usuarioDAOSQL.consultarResponsbaleParaEvento(id_usuario);

                listaEventos.add(new Evento(id_evento, nombreEvento, descripcionEvento, fecha_publicacion, fecha_evento,
                        hora_inicio, hora_fin, listaUbicaciones, null, tematica, usuario, activo));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultado != null) {
                    resultado.close();
                }
                if (sentenciaEventos != null) {
                    sentenciaEventos.close();
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
        return listaEventos;
    }

    @Override
    public int suscritosEvento(int id_evento) {

        int suscritos = 0;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;

        try {

            abrirConexion();
            sentencia = this.conexion.prepareStatement(
                    "SELECT COUNT(id_usuario) "
                    + "FROM usuario_evento "
                    + "WHERE id_evento = ?");

            sentencia.setInt(1, id_evento);
            resultado = sentencia.executeQuery();

            while (resultado.next()) {
                suscritos = resultado.getInt(1);
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
        return suscritos;
    }

    @Override
    public List<Evento> listaEventosPorCodigoPostal(String codigo_postal) {

        List<Evento> listaEventos = new ArrayList<Evento>();

        PreparedStatement sentenciaEventos = null;
        PreparedStatement sentenciaUbicaciones = null;
        ResultSet resultado = null;

        try {

            abrirConexion();
            this.conexion.setAutoCommit(false);
            sentenciaEventos = this.conexion.prepareStatement(
                    "SELECT e.id_evento, e.nombre, e.descripcion, e.fecha_publicacion, "
                    + "e.fecha_evento, e.hora_inicio, e.hora_fin, e.codigo_qr, e.activo, "
                    + "t.id_tematica, t.nombre, t.descripcion, t.edad_legal, "
                    + "us.id_usuario "
                    + "FROM evento e, tematica t, responsable r, ubicacion u, usuario us, evento_ubicacion eu "
                    + "WHERE e.id_usuario = r.id_usuario "
                    + "AND r.id_usuario  = us.id_usuario "
                    + "AND e.id_tematica = t.id_tematica  "
                    + "AND e.id_evento = eu.id_evento "
                    + "AND eu.id_ubicacion = u.id_ubicacion "
                    + "AND e.activo = true "
                    + "AND u.codigo_postal = ? "
                    + "GROUP BY e.id_evento ");

            sentenciaEventos.setString(1, codigo_postal);
            resultado = sentenciaEventos.executeQuery();

            int id_evento, id_tematica, edad_legal = 0, id_usuario;
            String nombreEvento, descripcionEvento, nombreTematica = "", descripcionTematica = "";
            Date fecha_publicacion, fecha_evento;
            LocalTime hora_inicio, hora_fin;
            boolean activo;
            Tematica tematica;

            while (resultado.next()) {

                id_evento = resultado.getInt(1);
                nombreEvento = resultado.getString(2);
                descripcionEvento = resultado.getString(3);
                fecha_publicacion = resultado.getDate(4);
                fecha_evento = resultado.getDate(5);
                hora_inicio = resultado.getTime(6).toLocalTime();
                hora_fin = resultado.getTime(7).toLocalTime();
                // 8 codigo qr
                activo = resultado.getBoolean(9);

                id_tematica = resultado.getInt(10);
                nombreTematica = resultado.getString(11);
                descripcionTematica = resultado.getString(12);
                edad_legal = resultado.getInt(13);
                id_usuario = resultado.getInt(14);
                tematica = new Tematica(id_tematica, nombreTematica, descripcionTematica, edad_legal);

                HashSet<Ubicacion> listaUbicaciones = this.ubicacionDAOSQL.listaUbicacionesEvento(id_evento);

                UsuarioResponsable usuario = this.usuarioDAOSQL.consultarResponsbaleParaEvento(id_usuario);

                listaEventos.add(new Evento(id_evento, nombreEvento, descripcionEvento, fecha_publicacion, fecha_evento,
                        hora_inicio, hora_fin, listaUbicaciones, null, tematica, usuario, activo));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultado != null) {
                    resultado.close();
                }
                if (sentenciaEventos != null) {
                    sentenciaEventos.close();
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
        return listaEventos;
    }

    @Override
    public List<Evento> historialEventos(int id_usuario) {

        List<Evento> listaEventos = new ArrayList<Evento>();

        PreparedStatement sentenciaEventos = null;
        PreparedStatement sentenciaUbicaciones = null;
        ResultSet resultado = null;

        try {

            abrirConexion();
            this.conexion.setAutoCommit(false);
            sentenciaEventos = this.conexion.prepareStatement(
                    "SELECT e.id_evento, e.nombre, e.descripcion, e.fecha_publicacion, e.fecha_evento, "
                    + "e.hora_inicio, e.hora_fin, e.codigo_qr, e.activo, e.id_usuario, "
                    + "t. id_tematica, t.nombre, t.descripcion, t.edad_legal "
                    + "FROM evento e, tematica t, responsable r, estandar es, usuario_evento ue "
                    + "WHERE ((e.id_usuario = r.id_usuario "
                    + "AND r.id_usuario = ?) "
                    + "OR (e.id_evento = ue.id_evento "
                    + "AND ue.id_usuario = ?)) "
                    + "AND e.id_tematica = t.id_tematica "
                    + "AND e.fecha_evento < now() "
                    + "GROUP BY e.id_evento ");

            sentenciaEventos.setInt(1, id_usuario);
            sentenciaEventos.setInt(2, id_usuario);
            resultado = sentenciaEventos.executeQuery();

            int id_evento, id_usuarioResp, id_tematica = 0, edad_legal = 0;
            String nombreEvento, descripcionEvento, nombreTematica = "", descripcionTematica = "";
            Date fecha_publicacion, fecha_evento;
            LocalTime hora_inicio, hora_fin;
            boolean activo;
            Tematica tematica;

            while (resultado.next()) {

                id_evento = resultado.getInt(1);
                nombreEvento = resultado.getString(2);
                descripcionEvento = resultado.getString(3);
                fecha_publicacion = resultado.getDate(4);
                fecha_evento = resultado.getDate(5);
                hora_inicio = resultado.getTime(6).toLocalTime();
                hora_fin = resultado.getTime(7).toLocalTime();
                // 8 codigo qr
                activo = resultado.getBoolean(9);
                id_usuarioResp = resultado.getInt(10);
                id_tematica = resultado.getInt(11);
                nombreTematica = resultado.getString(12);
                descripcionTematica = resultado.getString(13);
                edad_legal = resultado.getInt(14);
                tematica = new Tematica(id_tematica, nombreTematica, descripcionTematica, edad_legal);

                HashSet<Ubicacion> listaUbicaciones = this.ubicacionDAOSQL.listaUbicacionesEvento(id_evento);

                UsuarioResponsable usuario = this.usuarioDAOSQL.consultarResponsbaleParaEvento(id_usuarioResp);

                listaEventos.add(new Evento(id_evento, nombreEvento, descripcionEvento, fecha_publicacion, fecha_evento,
                        hora_inicio, hora_fin, listaUbicaciones, null, tematica, usuario, activo));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultado != null) {
                    resultado.close();
                }
                if (sentenciaEventos != null) {
                    sentenciaEventos.close();
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
        return listaEventos;
    }

    @Override
    public List<Evento> listaEventosExplorar(String codigo_postal, String tema) {

        List<Evento> listaEventos = new ArrayList<Evento>();

        PreparedStatement sentenciaEventos = null;
        PreparedStatement sentenciaUbicaciones = null;
        ResultSet resultado = null;

        try {

            abrirConexion();
            this.conexion.setAutoCommit(false);
            sentenciaEventos = this.conexion.prepareStatement(
                    "SELECT e.id_evento, e.nombre, e.descripcion, e.fecha_publicacion, "
                    + "e.fecha_evento, e.hora_inicio, e.hora_fin, e.codigo_qr, e.activo, "
                    + "t.id_tematica, t.nombre, t.descripcion, t.edad_legal, "
                    + "us.id_usuario "
                    + "FROM evento e, tematica t, responsable r, ubicacion u, usuario us, evento_ubicacion eu "
                    + "WHERE e.id_usuario = r.id_usuario "
                    + "AND r.id_usuario  = us.id_usuario "
                    + "AND e.id_tematica = t.id_tematica  "
                    + "AND e.id_evento = eu.id_evento "
                    + "AND eu.id_ubicacion = u.id_ubicacion "
                    + "AND e.activo = true "
                    + "AND u.codigo_postal = ? "
                    + "AND t.nombre = ? "
                    + "GROUP BY e.id_evento ");

            sentenciaEventos.setString(1, codigo_postal);
            sentenciaEventos.setString(2, tema);
            resultado = sentenciaEventos.executeQuery();

            int id_evento, id_tematica, edad_legal = 0, id_usuario;
            String nombreEvento, descripcionEvento, nombreTematica = "", descripcionTematica = "";
            Date fecha_publicacion, fecha_evento;
            LocalTime hora_inicio, hora_fin;
            boolean activo;
            Tematica tematica;

            while (resultado.next()) {

                id_evento = resultado.getInt(1);
                nombreEvento = resultado.getString(2);
                descripcionEvento = resultado.getString(3);
                fecha_publicacion = resultado.getDate(4);
                fecha_evento = resultado.getDate(5);
                hora_inicio = resultado.getTime(6).toLocalTime();
                hora_fin = resultado.getTime(7).toLocalTime();
                // 8 codigo qr
                activo = resultado.getBoolean(9);

                id_tematica = resultado.getInt(10);
                nombreTematica = resultado.getString(11);
                descripcionTematica = resultado.getString(12);
                edad_legal = resultado.getInt(13);
                id_usuario = resultado.getInt(14);
                tematica = new Tematica(id_tematica, nombreTematica, descripcionTematica, edad_legal);

                HashSet<Ubicacion> listaUbicaciones = this.ubicacionDAOSQL.listaUbicacionesEvento(id_evento);

                UsuarioResponsable usuario = this.usuarioDAOSQL.consultarResponsbaleParaEvento(id_usuario);

                listaEventos.add(new Evento(id_evento, nombreEvento, descripcionEvento, fecha_publicacion, fecha_evento,
                        hora_inicio, hora_fin, listaUbicaciones, null, tematica, usuario, activo));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultado != null) {
                    resultado.close();
                }
                if (sentenciaEventos != null) {
                    sentenciaEventos.close();
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
        return listaEventos;
    }

    @Override
    public List<Evento> suscripcionesEventos(int id_usuario) {

        List<Evento> listaEventos = new ArrayList<Evento>();

        PreparedStatement sentenciaEventos = null;
        PreparedStatement sentenciaUbicaciones = null;
        ResultSet resultado = null;

        try {

            abrirConexion();
            this.conexion.setAutoCommit(false);
            sentenciaEventos = this.conexion.prepareStatement(
                    "SELECT e.id_evento, e.nombre, e.descripcion, e.fecha_publicacion, e.fecha_evento, "
                    + "e.hora_inicio, e.hora_fin, e.codigo_qr, e.activo, e.id_usuario, "
                    + "t. id_tematica, t.nombre, t.descripcion, t.edad_legal "
                    + "FROM evento e, tematica t, responsable r, estandar es, usuario_evento ue "
                    + "WHERE e.id_evento = ue.id_evento "
                    + "AND ue.id_usuario = ? "
                    + "AND e.id_tematica = t.id_tematica "
                    + "AND e.fecha_evento > now() "
                    + "GROUP BY e.id_evento ");

            sentenciaEventos.setInt(1, id_usuario);
            resultado = sentenciaEventos.executeQuery();

            int id_evento, id_usuarioResp, id_tematica = 0, edad_legal = 0;
            String nombreEvento, descripcionEvento, nombreTematica = "", descripcionTematica = "";
            Date fecha_publicacion, fecha_evento;
            LocalTime hora_inicio, hora_fin;
            boolean activo;
            Tematica tematica;

            while (resultado.next()) {

                id_evento = resultado.getInt(1);
                nombreEvento = resultado.getString(2);
                descripcionEvento = resultado.getString(3);
                fecha_publicacion = resultado.getDate(4);
                fecha_evento = resultado.getDate(5);
                hora_inicio = resultado.getTime(6).toLocalTime();
                hora_fin = resultado.getTime(7).toLocalTime();
                // 8 codigo qr
                activo = resultado.getBoolean(9);
                id_usuarioResp = resultado.getInt(10);
                id_tematica = resultado.getInt(11);
                nombreTematica = resultado.getString(12);
                descripcionTematica = resultado.getString(13);
                edad_legal = resultado.getInt(14);
                tematica = new Tematica(id_tematica, nombreTematica, descripcionTematica, edad_legal);

                HashSet<Ubicacion> listaUbicaciones = this.ubicacionDAOSQL.listaUbicacionesEvento(id_evento);

                UsuarioResponsable usuario = this.usuarioDAOSQL.consultarResponsbaleParaEvento(id_usuarioResp);

                listaEventos.add(new Evento(id_evento, nombreEvento, descripcionEvento, fecha_publicacion, fecha_evento,
                        hora_inicio, hora_fin, listaUbicaciones, null, tematica, usuario, activo));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultado != null) {
                    resultado.close();
                }
                if (sentenciaEventos != null) {
                    sentenciaEventos.close();
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
        return listaEventos;
    }

    @Override
    public boolean suscribirseEvento(int id_evento, int id_usuario) {

        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        int id = 0;
        boolean insertado = false;
        Date fechaIngreso = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());

        try {
            abrirConexion();
            sentencia = this.conexion.prepareStatement(
                    "INSERT INTO usuario_evento (id_usuario, id_evento) "
                    + "VALUES (?, ?)");

            sentencia.setInt(1, id_usuario);
            sentencia.setInt(2, id_evento);

            int affectedRows = sentencia.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("No se puede insertar los datos");
            }
            insertado = true;
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
    public boolean desuscribirseEvento(int id_evento, int id_usuario) {
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        int id = 0;
        boolean eliminado = false;

        try {
            abrirConexion();
            sentencia = this.conexion.prepareStatement(
                    "DELETE FROM usuario_evento "
                    + "WHERE id_usuario = ? "
                    + "AND id_evento = ? ");

            sentencia.setInt(1, id_usuario);
            sentencia.setInt(2, id_evento);

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

    @Override
    public List<Evento> listaEventosProximos(String codigo_postal) {
        List<Evento> listaEventos = new ArrayList<Evento>();

        PreparedStatement sentenciaEventos = null;
        PreparedStatement sentenciaUbicaciones = null;
        ResultSet resultado = null;

        try {

            abrirConexion();
            this.conexion.setAutoCommit(false);
            sentenciaEventos = this.conexion.prepareStatement(
                    "SELECT e.id_evento, e.nombre, e.descripcion, e.fecha_publicacion, "
                    + "e.fecha_evento, e.hora_inicio, e.hora_fin, e.codigo_qr, e.activo, "
                    + "t.id_tematica, t.nombre, t.descripcion, t.edad_legal, "
                    + "us.id_usuario "
                    + "FROM evento e, tematica t, responsable r, ubicacion u, usuario us, evento_ubicacion eu "
                    + "WHERE e.id_usuario = r.id_usuario "
                    + "AND r.id_usuario  = us.id_usuario "
                    + "AND e.id_tematica = t.id_tematica  "
                    + "AND e.id_evento = eu.id_evento "
                    + "AND eu.id_ubicacion = u.id_ubicacion "
                    + "AND e.activo = true "
                    + "AND u.codigo_postal =? "
                    + "AND e.fecha_evento > now() "
                    + "GROUP BY e.id_evento "
                    + "ORDER BY e.fecha_evento "
                    + "LIMIT 8 ");

            sentenciaEventos.setString(1, codigo_postal);
            resultado = sentenciaEventos.executeQuery();

            int id_evento, id_tematica, edad_legal = 0, id_usuario;
            String nombreEvento, descripcionEvento, nombreTematica = "", descripcionTematica = "";
            Date fecha_publicacion, fecha_evento;
            LocalTime hora_inicio, hora_fin;
            boolean activo;
            Tematica tematica;

            while (resultado.next()) {

                id_evento = resultado.getInt(1);
                nombreEvento = resultado.getString(2);
                descripcionEvento = resultado.getString(3);
                fecha_publicacion = resultado.getDate(4);
                fecha_evento = resultado.getDate(5);
                hora_inicio = resultado.getTime(6).toLocalTime();
                hora_fin = resultado.getTime(7).toLocalTime();
                // 8 codigo qr
                activo = resultado.getBoolean(9);

                id_tematica = resultado.getInt(10);
                nombreTematica = resultado.getString(11);
                descripcionTematica = resultado.getString(12);
                edad_legal = resultado.getInt(13);
                id_usuario = resultado.getInt(14);
                tematica = new Tematica(id_tematica, nombreTematica, descripcionTematica, edad_legal);

                HashSet<Ubicacion> listaUbicaciones = this.ubicacionDAOSQL.listaUbicacionesEvento(id_evento);

                UsuarioResponsable usuario = this.usuarioDAOSQL.consultarResponsbaleParaEvento(id_usuario);

                listaEventos.add(new Evento(id_evento, nombreEvento, descripcionEvento, fecha_publicacion, fecha_evento,
                        hora_inicio, hora_fin, listaUbicaciones, null, tematica, usuario, activo));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultado != null) {
                    resultado.close();
                }
                if (sentenciaEventos != null) {
                    sentenciaEventos.close();
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
        return listaEventos;
    }

    @Override
    public List<Evento> listaEventosRecomendados(String codigo_postal, int id_usuario) {
        List<Evento> listaEventos = new ArrayList<Evento>();

        PreparedStatement sentencia = null;
        ResultSet resultado = null;

        try {

            abrirConexion();
            this.conexion.setAutoCommit(false);

            sentencia = this.conexion.prepareStatement(
                    "SELECT e.id_tematica, COUNT(e.id_tematica) as contador "
                    + "FROM evento e, usuario_evento ue  "
                    + "WHERE ue.id_usuario = ? "
                    + "AND ue.id_evento = e.id_evento  "
                    + "GROUP BY e.id_tematica "
                    + "ORDER BY contador DESC "
                    + "LIMIT 1");
            
            sentencia.setInt(1, id_usuario);
            resultado = sentencia.executeQuery();
            int id_tematica=0;
            while (resultado.next()) {
                id_tematica = resultado.getInt(1);
            }
            
            sentencia = this.conexion.prepareStatement(
                    "SELECT e.id_evento, e.nombre, e.descripcion, e.fecha_publicacion, "
                    + "e.fecha_evento, e.hora_inicio, e.hora_fin, e.codigo_qr, e.activo, "
                    + "t.id_tematica, t.nombre, t.descripcion, t.edad_legal, "
                    + "us.id_usuario "
                    + "FROM evento e, tematica t, responsable r, ubicacion u, usuario us, evento_ubicacion eu "
                    + "WHERE e.id_usuario = r.id_usuario "
                    + "AND r.id_usuario  = us.id_usuario "
                    + "AND e.id_tematica = ? "
                    + "AND e.id_evento = eu.id_evento "
                    + "AND eu.id_ubicacion = u.id_ubicacion "
                    + "AND e.activo = true "
                    + "AND u.codigo_postal = ? "
                    + "AND e.fecha_evento > now() "
                    + "GROUP BY e.id_evento "
                    + "LIMIT 8 ");

            sentencia.setInt(1, id_tematica);
            sentencia.setString(2, codigo_postal);
            resultado = sentencia.executeQuery();

            int id_evento, edad_legal = 0, id_usuario_resp;
            String nombreEvento, descripcionEvento, nombreTematica = "", descripcionTematica = "";
            Date fecha_publicacion, fecha_evento;
            LocalTime hora_inicio, hora_fin;
            boolean activo;
            Tematica tematica;

            while (resultado.next()) {

                id_evento = resultado.getInt(1);
                nombreEvento = resultado.getString(2);
                descripcionEvento = resultado.getString(3);
                fecha_publicacion = resultado.getDate(4);
                fecha_evento = resultado.getDate(5);
                hora_inicio = resultado.getTime(6).toLocalTime();
                hora_fin = resultado.getTime(7).toLocalTime();
                // 8 codigo qr
                activo = resultado.getBoolean(9);

                id_tematica = resultado.getInt(10);
                nombreTematica = resultado.getString(11);
                descripcionTematica = resultado.getString(12);
                edad_legal = resultado.getInt(13);
                id_usuario_resp = resultado.getInt(14);
                tematica = new Tematica(id_tematica, nombreTematica, descripcionTematica, edad_legal);

                HashSet<Ubicacion> listaUbicaciones = this.ubicacionDAOSQL.listaUbicacionesEvento(id_evento);

                UsuarioResponsable usuario = this.usuarioDAOSQL.consultarResponsbaleParaEvento(id_usuario_resp);

                listaEventos.add(new Evento(id_evento, nombreEvento, descripcionEvento, fecha_publicacion, fecha_evento,
                        hora_inicio, hora_fin, listaUbicaciones, null, tematica, usuario, activo));
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
        return listaEventos;
    }

    @Override
    public boolean existeSuscritoEvento(int id_evento, int id_usuario) {
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        boolean existe = false;

        try {
            abrirConexion();
            sentencia = this.conexion.prepareStatement(
                    "SELECT * "
                    + "FROM usuario_evento ue "
                    + "WHERE id_evento=? AND id_usuario=? "
                    + " ");

            sentencia.setInt(1, id_evento);
            sentencia.setInt(2, id_usuario);
            resultado = sentencia.executeQuery();

            while (resultado.next()) {
                existe = true;
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
        return existe;
    }

    @Override
    public boolean aumentarPuntosEvento(int id_evento, int id_usuario) {
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        String nombreTematica = "";
        String atributo = "";
        int id_atributo = 0;
        boolean existe = false;
        boolean insertado = false;

        try {
            abrirConexion();
            this.conexion.setAutoCommit(false);
            sentencia = this.conexion.prepareStatement(
                    "SELECT nombre "
                    + "FROM tematica "
                    + "WHERE id_tematica=(SELECT id_tematica FROM evento WHERE id_evento=?) ");
            sentencia.setInt(1, id_evento);
            resultado = sentencia.executeQuery();
            while (resultado.next()) {
                nombreTematica = resultado.getString(1);
            }

            switch (nombreTematica) {
                case "Cultura local":
                    atributo = "Cultural";
                    break;
                case "Espectáculos":
                    atributo = "Entretenimiento";
                    break;
                case "Gastronomía":
                    atributo = "Gastronómico";
                    break;
                case "Entretenimiento":
                    atributo = "Entretenimiento";
                    break;
                case "Deporte":
                    atributo = "Deportivo";
                    break;
                case "Tecnología":
                    atributo = "Entretenimiento";
                    break;
                case "Benéficos":
                    atributo = "Compromiso social";
                    break;
                case "Ponencias":
                    atributo = "Cultural";
                    break;
            }

            sentencia = this.conexion.prepareStatement(
                    "SELECT * "
                    + "FROM usuario_atributo "
                    + "WHERE id_atributo = ("
                    + "SELECT id_atributo "
                    + "FROM atributo "
                    + "WHERE nombre =?) "
                    + "AND id_usuario =? ");
            sentencia.setString(1, atributo);
            sentencia.setInt(2, id_usuario);
            resultado = sentencia.executeQuery();
            while (resultado.next()) {
                existe = true;
            }

            if (existe) {
                sentencia = this.conexion.prepareStatement(
                        "UPDATE usuario_atributo SET experiencia = experiencia+25 "
                        + "WHERE id_atributo = ("
                        + "SELECT id_atributo "
                        + "FROM atributo "
                        + "WHERE nombre = ?) "
                        + "AND id_usuario = ?");

                sentencia.setString(1, atributo);
                sentencia.setInt(2, id_usuario);

                int affectedRows = sentencia.executeUpdate();

                if (affectedRows == 0) {
                    throw new SQLException("No se puede actualizar los datos.");
                } else if (affectedRows == 1) {
                    insertado = true;
                }
            } else {

                sentencia = this.conexion.prepareStatement(
                        "SELECT id_atributo "
                        + "FROM atributo "
                        + "WHERE nombre = ? ");
                sentencia.setString(1, atributo);
                resultado = sentencia.executeQuery();
                while (resultado.next()) {
                    id_atributo = resultado.getInt(1);
                }

                sentencia = this.conexion.prepareStatement(
                        "INSERT INTO usuario_atributo (id_usuario, id_atributo, experiencia) "
                        + "VALUES ( ?, ?, 25)");

                sentencia.setInt(1, id_usuario);
                sentencia.setInt(2, id_atributo);

                int affectedRows = sentencia.executeUpdate();

                if (affectedRows == 0) {
                    throw new SQLException("No se puede actualizar los datos.");
                } else if (affectedRows == 1) {
                    insertado = true;
                }
            }
            this.conexion.commit();
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
