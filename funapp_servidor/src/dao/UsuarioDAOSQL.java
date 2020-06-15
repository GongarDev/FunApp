package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Atributo;
import model.Credenciales;
import model.Entidad;
import model.Publicacion;
import model.Usuario;
import model.UsuarioAdmin;
import model.UsuarioEstandar;
import model.UsuarioResponsable;
import util.ConfiguracionBD;

/**
 *
 * @author melkart
 */
public class UsuarioDAOSQL implements UsuarioDAO {

    private String url;
    private String puerto_sgbd;
    private String host_sgbd;
    private String db;
    private String usuario;
    private String contrasenia;
    private Connection conexion;
    private EntidadDAOSQL entidadDAOSQL;

    public UsuarioDAOSQL() {

        ConfiguracionBD c = new ConfiguracionBD();
        c.importar();
        this.entidadDAOSQL = new EntidadDAOSQL();
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
    public boolean existeUsuario(String correo) {

        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        boolean existe = false;

        try {
            abrirConexion();
            sentencia = this.conexion.prepareStatement(
                    "SELECT email "
                    + "FROM usuario "
                    + "WHERE email=? "
                    + " ");

            sentencia.setString(1, correo);
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
    public boolean existeNombreUsuario(String seudonimo) {

        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        boolean existe = false;

        try {
            abrirConexion();
            sentencia = this.conexion.prepareStatement(
                    "SELECT seudonimo "
                    + "FROM usuario "
                    + "WHERE seudonimo=? "
                    + " ");

            sentencia.setString(1, seudonimo);
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
    public UsuarioEstandar consultarUsEstandar(Credenciales credenciales) {

        PreparedStatement sentencia = null;
        ResultSet resultado = null;

        UsuarioEstandar estandar = null;

        try {
            abrirConexion();
            sentencia = this.conexion.prepareStatement(
                    "SELECT e.perfil_publico, u.id_usuario, u.seudonimo, "
                    + "u.email, u.fecha_nac, u.fecha_ingreso, "
                    + "u.contrasenia, u.imagen "
                    + "FROM usuario u, estandar e "
                    + "WHERE u.email=? "
                    + " AND u.contrasenia=? "
                    + " AND u.id_usuario=e.id_usuario ");

            sentencia.setString(1, credenciales.getEmail());
            sentencia.setString(2, credenciales.getContrasenia());
            resultado = sentencia.executeQuery();

            while (resultado.next()) {

                String pattern = "dd-MM-yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                Date fecha_nac = sdf.parse(resultado.getDate(5).toString());
                Date fecha_ingreso = sdf.parse(resultado.getDate(6).toString());
                estandar = new UsuarioEstandar(
                        resultado.getBoolean(1),
                        resultado.getInt(2),
                        resultado.getString(3),
                        resultado.getString(4),
                        fecha_nac,
                        fecha_ingreso,
                        resultado.getString(7),
                        resultado.getString(8)
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException ex) {
            Logger.getLogger(UsuarioDAOSQL.class.getName()).log(Level.SEVERE, null, ex);
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
        return estandar;
    }

    @Override
    public UsuarioResponsable consultarUsResponsable(Credenciales credenciales) {

        PreparedStatement sentencia = null;
        ResultSet resultado = null;

        UsuarioResponsable responsable = null;

        try {
            abrirConexion();
            sentencia = this.conexion.prepareStatement(
                    "SELECT r.dni, r.nombre, r.apellido, r.telefono, "
                    + "u.id_usuario, u.seudonimo, u.email, u.fecha_nac, u.fecha_ingreso, "
                    + "u.contrasenia, u.imagen "
                    + "FROM usuario u, responsable r "
                    + "WHERE u.email=? "
                    + "AND u.contrasenia=? "
                    + "AND u.id_usuario=r.id_usuario ");

            sentencia.setString(1, credenciales.getEmail());
            sentencia.setString(2, credenciales.getContrasenia());
            resultado = sentencia.executeQuery();

            while (resultado.next()) {

                String pattern = "dd-MM-yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                Date fecha_nac = sdf.parse(resultado.getDate(8).toString());
                Date fecha_ingreso = sdf.parse(resultado.getDate(9).toString());
                System.out.println(fecha_nac);
                responsable = new UsuarioResponsable(
                        resultado.getString(1),
                        resultado.getString(2),
                        resultado.getString(3),
                        resultado.getString(4),
                        resultado.getInt(5),
                        resultado.getString(6),
                        resultado.getString(7),
                        fecha_nac,
                        fecha_ingreso,
                        resultado.getString(10),
                        resultado.getString(11)
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException ex) {
            Logger.getLogger(UsuarioDAOSQL.class.getName()).log(Level.SEVERE, null, ex);
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
        return responsable;
    }

    @Override
    public boolean altaUsEstandar(UsuarioEstandar usuario) {

        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        int id = 0;
        boolean insertado = false;
        Date fechaIngreso = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
        usuario.setFecha_ingreso(fechaIngreso);

        try {
            abrirConexion();
            sentencia = this.conexion.prepareStatement(
                    "INSERT INTO usuario (seudonimo, email, fecha_nac, fecha_ingreso, contrasenia, imagen) "
                    + "VALUES (?, ?, ?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);
            
            usuario.setImagen("avatar0");
            sentencia.setString(1, usuario.getSeudonimo());
            sentencia.setString(2, usuario.getEmail());
            sentencia.setDate(3, java.sql.Date.valueOf(usuario.getFecha_nac_LocalDate()));
            sentencia.setDate(4, java.sql.Date.valueOf(usuario.getFecha_ingreso_LocalDate()));
            sentencia.setString(5, usuario.getContrasenia());
            sentencia.setString(6, usuario.getImagen());

            int affectedRows = sentencia.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("No se puede insertar los datos");
            } else if (affectedRows == 1) {
                resultado = sentencia.getGeneratedKeys();
                if (resultado.next()) {
                    id = resultado.getInt(1);
                }
            }

            sentencia = this.conexion.prepareStatement(
                    "INSERT INTO estandar (id_usuario, perfil_publico) "
                    + "VALUES (?, ?)");

            sentencia.setInt(1, id);
            sentencia.setBoolean(2, false);

            affectedRows = sentencia.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("No se puede insertar los datos");
            } else if (affectedRows == 1) {
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
    public boolean altaUsResponsable(UsuarioResponsable usuario) {

        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        int id = 0;
        boolean insertado = false;
        Date fechaIngreso = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
        usuario.setFecha_ingreso(fechaIngreso);

        try {
            abrirConexion();
            sentencia = this.conexion.prepareStatement(
                    "INSERT INTO usuario (seudonimo, email, fecha_nac, fecha_ingreso, contrasenia, imagen) "
                    + "VALUES (?, ?, ?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);

            usuario.setImagen("avatar0");
            sentencia.setString(1, usuario.getSeudonimo());
            sentencia.setString(2, usuario.getEmail());
            sentencia.setDate(3, java.sql.Date.valueOf(usuario.getFecha_nac_LocalDate()));
            sentencia.setDate(4, java.sql.Date.valueOf(usuario.getFecha_ingreso_LocalDate()));
            sentencia.setString(5, usuario.getContrasenia());
            sentencia.setString(6, usuario.getImagen());

            int affectedRows = sentencia.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("No se puede insertar los datos.");
            } else if (affectedRows == 1) {
                resultado = sentencia.getGeneratedKeys();
                if (resultado.next()) {
                    id = resultado.getInt(1);
                }
            }

            sentencia = this.conexion.prepareStatement(
                    "INSERT INTO responsable (id_usuario, dni, nombre, apellido, telefono) "
                    + "VALUES (?, ?, ?, ?, ?)");

            sentencia.setInt(1, id);
            sentencia.setString(2, usuario.getDni());
            sentencia.setString(3, usuario.getNombre());
            sentencia.setString(4, usuario.getApellido());
            sentencia.setString(5, usuario.getTelefono());

            affectedRows = sentencia.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("No se puede insertar los datos.");
            } else if (affectedRows == 1) {
                insertado = true;
            }

            this.entidadDAOSQL.altaEntidad(id);
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
    public boolean altaUsResponsableEscritorio(UsuarioResponsable usuario, Entidad entidad) {

        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        int id = 0;
        boolean insertado = false;
        Date fechaIngreso = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
        usuario.setFecha_ingreso(fechaIngreso);

        try {
            abrirConexion();
            sentencia = this.conexion.prepareStatement(
                    "INSERT INTO usuario (seudonimo, email, fecha_nac, fecha_ingreso, contrasenia, imagen) "
                    + "VALUES (?, ?, ?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);

            usuario.setImagen("avatar0");
            sentencia.setString(1, usuario.getSeudonimo());
            sentencia.setString(2, usuario.getEmail());
            sentencia.setDate(3, java.sql.Date.valueOf(usuario.getFecha_nac_LocalDate()));
            sentencia.setDate(4, java.sql.Date.valueOf(usuario.getFecha_ingreso_LocalDate()));
            sentencia.setString(5, usuario.getContrasenia());
            sentencia.setString(6, usuario.getImagen());

            int affectedRows = sentencia.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("No se puede insertar los datos.");
            } else if (affectedRows == 1) {
                resultado = sentencia.getGeneratedKeys();
                if (resultado.next()) {
                    id = resultado.getInt(1);
                }
            }

            sentencia = this.conexion.prepareStatement(
                    "INSERT INTO responsable (id_usuario, dni, nombre, apellido, telefono) "
                    + "VALUES (?, ?, ?, ?, ?)");

            sentencia.setInt(1, id);
            sentencia.setString(2, usuario.getDni());
            sentencia.setString(3, usuario.getNombre());
            sentencia.setString(4, usuario.getApellido());
            sentencia.setString(5, usuario.getTelefono());

            affectedRows = sentencia.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("No se puede insertar los datos.");
            } else if (affectedRows == 1) {
                insertado = true;
            }

            entidad.setId_usuario(id);
            this.entidadDAOSQL.altaEntidadEscritorio(entidad);
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
    public boolean actualizarUsEstandar(Usuario usuario) {

        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        boolean insertado = false;

        try {
            abrirConexion();
            this.conexion.setAutoCommit(false);
            sentencia = this.conexion.prepareStatement(
                    "UPDATE usuario SET seudonimo = ?, email = ?, fecha_nac = ?, "
                    + "contrasenia = ?, imagen= ? "
                    + "WHERE id_usuario = ? ");

            sentencia.setString(1, usuario.getSeudonimo());
            sentencia.setString(2, usuario.getEmail());
            sentencia.setDate(3, java.sql.Date.valueOf(usuario.getFecha_nac_LocalDate()));
            sentencia.setString(4, usuario.getContrasenia());
            sentencia.setString(5, usuario.getImagen());            
            sentencia.setInt(6, usuario.getId_usuario());


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
    public boolean actualizarUsResponsable(UsuarioResponsable usuario) {

        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        boolean insertado = false;

        try {
            abrirConexion();
            this.conexion.setAutoCommit(false);
            sentencia = this.conexion.prepareStatement(
                    "UPDATE usuario SET seudonimo = ?, email = ?, fecha_nac = ?, "
                    + "contrasenia = ?, imagen = ? "
                    + "WHERE id_usuario = ? ");

            sentencia.setString(1, usuario.getSeudonimo());
            sentencia.setString(2, usuario.getEmail());
            sentencia.setDate(3, java.sql.Date.valueOf(usuario.getFecha_nac_LocalDate()));
            sentencia.setString(4, usuario.getContrasenia());
            sentencia.setString(5, usuario.getImagen());
            sentencia.setInt(6, usuario.getId_usuario());

            int affectedRows = sentencia.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("No se puede actualizar los datos.");

            } else if (affectedRows == 1) {

                sentencia = this.conexion.prepareStatement(
                        "UPDATE responsable SET dni = ?, nombre = ?, apellido = ?, "
                        + "telefono = ? "
                        + "WHERE id_usuario = ? ");

                sentencia.setString(1, usuario.getDni());
                sentencia.setString(2, usuario.getNombre());
                sentencia.setString(3, usuario.getApellido());
                sentencia.setString(4, usuario.getTelefono());
                sentencia.setInt(5, usuario.getId_usuario());

                affectedRows = sentencia.executeUpdate();

                if (affectedRows == 0) {
                    throw new SQLException("No se puede actualizar los datos.");
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
    public boolean bajaUsEstandar(UsuarioEstandar usuario) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean bajaUsResponsable(UsuarioResponsable usuario) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public UsuarioResponsable consultarResponsbaleParaEvento(int id_usuario) {
        PreparedStatement sentencia = null;
        ResultSet resultado = null;

        UsuarioResponsable responsable = null;

        try {
            abrirConexion();
            sentencia = this.conexion.prepareStatement(
                    "SELECT r.nombre, r.apellido, "
                    + "u.id_usuario, u.seudonimo, u.fecha_ingreso "
                    + "FROM usuario u, responsable r "
                    + "WHERE r.id_usuario=? "
                    + "AND u.id_usuario=r.id_usuario ");

            sentencia.setInt(1, id_usuario);
            resultado = sentencia.executeQuery();

            while (resultado.next()) {

                String pattern = "dd-MM-yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                Date fecha_ingreso = sdf.parse(resultado.getDate(5).toString());
                responsable = new UsuarioResponsable(
                        null,
                        resultado.getString(1),
                        resultado.getString(2),
                        null,
                        resultado.getInt(3),
                        resultado.getString(4),
                        null,
                        null,
                        fecha_ingreso,
                        null,
                        null
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException ex) {
            Logger.getLogger(UsuarioDAOSQL.class.getName()).log(Level.SEVERE, null, ex);
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
        return responsable;
    }

    @Override
    public UsuarioResponsable consultarResponsbalePerfil(int id_usuario) {
        PreparedStatement sentencia = null;
        ResultSet resultado = null;

        UsuarioResponsable responsable = null;

        try {
            abrirConexion();
            sentencia = this.conexion.prepareStatement(
                    "SELECT r.dni, r.nombre, r.apellido, r.telefono, "
                    + "u.id_usuario, u.seudonimo, u.email, u.fecha_nac, u.fecha_ingreso "
                    + "FROM usuario u, responsable r "
                    + "WHERE r.id_usuario=? "
                    + "AND u.id_usuario=r.id_usuario ");

            sentencia.setInt(1, id_usuario);
            resultado = sentencia.executeQuery();

            while (resultado.next()) {

                String pattern = "dd-MM-yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                Date fecha_nac = sdf.parse(resultado.getDate(8).toString());
                Date fecha_ingreso = sdf.parse(resultado.getDate(9).toString());
                responsable = new UsuarioResponsable(
                        resultado.getString(1),
                        resultado.getString(2),
                        resultado.getString(3),
                        resultado.getString(4),
                        resultado.getInt(5),
                        resultado.getString(6),
                        resultado.getString(7),
                        fecha_nac,
                        fecha_ingreso,
                        null,
                        null
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException ex) {
            Logger.getLogger(UsuarioDAOSQL.class.getName()).log(Level.SEVERE, null, ex);
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
        return responsable;
    }

    @Override
    public boolean existeEntidadUsuario(int id_usuario) {
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        boolean existe = false;

        try {
            abrirConexion();
            sentencia = this.conexion.prepareStatement(
                    "SELECT * "
                    + "FROM entidad "
                    + "WHERE id_usuario=? "
                            + "AND nif IS NOT NULL "
                            + "AND nombre IS NOT NULL "
                            + "AND telefono IS NOT NULL ");

            sentencia.setInt(1, id_usuario);
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
    public List<Usuario> listaUsuariosAmigos(int id_usuario) {

        List<Usuario> listaUsuariosAmigos = new ArrayList<Usuario>();

        PreparedStatement sentenciaEventos = null;
        PreparedStatement sentenciaUbicaciones = null;
        ResultSet resultado = null;

        try {

            abrirConexion();
            this.conexion.setAutoCommit(false);
            sentenciaEventos = this.conexion.prepareStatement(
                    "SELECT u.id_usuario, u.seudonimo, u.fecha_ingreso, u.imagen "
                    + "FROM usuario u "
                    + "WHERE u.id_usuario in"
                    + " (SELECT id_usuario_seguido "
                    + "FROM seguidor "
                    + "WHERE id_usuario_seguidor = ?)");

            sentenciaEventos.setInt(1, id_usuario);
            resultado = sentenciaEventos.executeQuery();

            int id_usuario_amigo;
            String seudonimo, imagen;
            Date fecha_ingreso;

            while (resultado.next()) {

                id_usuario_amigo = resultado.getInt(1);
                seudonimo = resultado.getString(2);
                fecha_ingreso = resultado.getDate(3);
                imagen = resultado.getString(4);

                listaUsuariosAmigos.add(new Usuario(id_usuario_amigo, seudonimo, fecha_ingreso, imagen));
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
        return listaUsuariosAmigos;
    }

    @Override
    public int cantidadSuscripciones(int id_usuario) {

        int suscripciones = 0;
        PreparedStatement sentencia = null;
        ResultSet resultado = null;

        try {

            abrirConexion();
            sentencia = this.conexion.prepareStatement(
                    "SELECT COUNT(id_evento) "
                    + "FROM usuario_evento "
                    + "WHERE id_usuario = ?");

            sentencia.setInt(1, id_usuario);
            resultado = sentencia.executeQuery();

            while (resultado.next()) {
                suscripciones = resultado.getInt(1);
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
        return suscripciones;
    }

    @Override
    public boolean eliminarAmigo(int id_usuarioAmigo, int id_usuario) {

        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        boolean eliminado = false;

        try {
            abrirConexion();
            sentencia = this.conexion.prepareStatement(
                    "DELETE FROM seguidor "
                    + "WHERE (id_usuario_seguidor = ? "
                    + "AND id_usuario_seguido = ?) "
                    + "OR (id_usuario_seguido = ? "
                    + "AND id_usuario_seguidor = ?) ");

            sentencia.setInt(1, id_usuarioAmigo);
            sentencia.setInt(2, id_usuario);
            sentencia.setInt(3, id_usuarioAmigo);
            sentencia.setInt(4, id_usuario);

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
    public boolean existeSeguimientoUsuario(int id_usuarioAmigo, int id_usuario) {
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        boolean existe = false;

        try {
            abrirConexion();
            sentencia = this.conexion.prepareStatement(
                    "SELECT * "
                    + "FROM seguidor s "
                    + "WHERE s.id_usuario_seguidor=? AND id_usuario_seguido=? ");

            sentencia.setInt(1, id_usuario);
            sentencia.setInt(2, id_usuarioAmigo);
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
    public boolean insertarSeguimiento(int id_usuarioAmigo, int id_usuario) {

        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        boolean insertado = false;;

        try {
            abrirConexion();
            sentencia = this.conexion.prepareStatement(
                    "INSERT INTO seguidor (id_usuario_seguidor, id_usuario_seguido) "
                    + "VALUES (?, ?)");

            sentencia.setInt(1, id_usuario);
            sentencia.setInt(2, id_usuarioAmigo);

            int affectedRows = sentencia.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("No se puede insertar los datos");
            } else if (affectedRows == 1) {
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
    public boolean eliminarCuenta(int id_usuario) {
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        boolean eliminado = false;

        try {
            abrirConexion();
            sentencia = this.conexion.prepareStatement(
                    "DELETE FROM entidad "
                    + "WHERE id_usuario = ? ");
            sentencia.setInt(1, id_usuario);
            sentencia.executeUpdate();

            sentencia = this.conexion.prepareStatement(
                    "DELETE FROM evento "
                    + "WHERE id_usuario = ? ");
            sentencia.setInt(1, id_usuario);
            sentencia.executeUpdate();

            sentencia = this.conexion.prepareStatement(
                    "DELETE FROM usuario_evento "
                    + "WHERE id_usuario = ? ");
            sentencia.setInt(1, id_usuario);
            sentencia.executeUpdate();

            sentencia = this.conexion.prepareStatement(
                    "DELETE FROM mensaje "
                    + "WHERE id_usuario = ? ");
            sentencia.setInt(1, id_usuario);
            sentencia.executeUpdate();

            sentencia = this.conexion.prepareStatement(
                    "DELETE FROM incidencia "
                    + "WHERE id_usuario = ? ");
            sentencia.setInt(1, id_usuario);
            sentencia.executeUpdate();

            sentencia = this.conexion.prepareStatement(
                    "DELETE FROM usuario_logro "
                    + "WHERE id_usuario = ? ");
            sentencia.setInt(1, id_usuario);
            sentencia.executeUpdate();

            sentencia = this.conexion.prepareStatement(
                    "DELETE FROM seguidor "
                    + "WHERE id_usuario_seguidor = ? OR "
                    + "id_usuario_seguido = ? ");
            sentencia.setInt(1, id_usuario);
            sentencia.setInt(2, id_usuario);
            sentencia.executeUpdate();

            sentencia = this.conexion.prepareStatement(
                    "DELETE FROM usuario "
                    + "WHERE id_usuario = ? ");
            sentencia.setInt(1, id_usuario);
            sentencia.executeUpdate();

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
    public UsuarioAdmin consultarUsAdmin(Credenciales credenciales) {

        PreparedStatement sentencia = null;
        ResultSet resultado = null;

        UsuarioAdmin admin = null;

        try {
            abrirConexion();
            sentencia = this.conexion.prepareStatement(
                    "SELECT ad.dni, ad.nombre, ad.apellido, "
                    + "u.id_usuario, u.seudonimo, u.email, u.fecha_nac, u.fecha_ingreso, "
                    + "u.contrasenia, u.imagen "
                    + "FROM usuario u, administrador ad "
                    + "WHERE u.email=? "
                    + "AND u.contrasenia=? "
                    + "AND u.id_usuario=ad.id_usuario ");

            sentencia.setString(1, credenciales.getEmail());
            sentencia.setString(2, credenciales.getContrasenia());
            resultado = sentencia.executeQuery();

            while (resultado.next()) {

                String pattern = "dd-MM-yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                Date fecha_nac = sdf.parse(resultado.getDate(7).toString());
                Date fecha_ingreso = sdf.parse(resultado.getDate(8).toString());
                System.out.println(fecha_nac);
                admin = new UsuarioAdmin(
                        resultado.getString(1),
                        resultado.getString(2),
                        resultado.getString(3),
                        resultado.getInt(4),
                        resultado.getString(5),
                        resultado.getString(6),
                        fecha_nac,
                        fecha_ingreso,
                        resultado.getString(9),
                        resultado.getString(10)
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ParseException ex) {
            Logger.getLogger(UsuarioDAOSQL.class.getName()).log(Level.SEVERE, null, ex);
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
        return admin;
    }

    @Override
    public List<Usuario> adminListaUsuariosEstandar() {

        List<Usuario> listaUsuarios = new ArrayList<Usuario>();
        PreparedStatement sentenciaEventos = null;
        PreparedStatement sentenciaUbicaciones = null;
        ResultSet resultado = null;

        try {

            abrirConexion();
            this.conexion.setAutoCommit(false);
            sentenciaEventos = this.conexion.prepareStatement(
                    "SELECT u.id_usuario, u.seudonimo, u.email, u.fecha_nac, u.fecha_ingreso "
                    + "FROM usuario u, estandar e "
                    + "WHERE u.id_usuario = e.id_usuario ");

            resultado = sentenciaEventos.executeQuery();

            int id_usuario;
            String seudonimo, email;
            Date fecha_ingreso, fecha_nac;

            while (resultado.next()) {

                id_usuario = resultado.getInt(1);
                seudonimo = resultado.getString(2);
                email = resultado.getString(3);
                fecha_nac = resultado.getDate(4);
                fecha_ingreso = resultado.getDate(5);

                listaUsuarios.add(new Usuario(id_usuario, seudonimo, email, fecha_nac, fecha_ingreso, null, null));
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
        return listaUsuarios;
    }

    @Override
    public List<UsuarioResponsable> adminListaUsuariosResponsable() {
        List<UsuarioResponsable> listaUsuarios = new ArrayList<UsuarioResponsable>();
        PreparedStatement sentenciaEventos = null;
        PreparedStatement sentenciaUbicaciones = null;
        ResultSet resultado = null;

        try {

            abrirConexion();
            this.conexion.setAutoCommit(false);
            sentenciaEventos = this.conexion.prepareStatement(
                    "SELECT u.id_usuario, u.seudonimo, u.email, u.fecha_nac, u.fecha_ingreso, "
                    + "r.dni, r.nombre, r.apellido, r.telefono "
                    + "FROM usuario u, responsable r "
                    + "WHERE u.id_usuario = r.id_usuario ");

            resultado = sentenciaEventos.executeQuery();

            int id_usuario;
            String seudonimo, email, dni, nombre, apellido, telefono;
            Date fecha_ingreso, fecha_nac;

            while (resultado.next()) {

                id_usuario = resultado.getInt(1);
                seudonimo = resultado.getString(2);
                email = resultado.getString(3);
                fecha_nac = resultado.getDate(4);
                fecha_ingreso = resultado.getDate(5);
                dni = resultado.getString(6);
                nombre = resultado.getString(7);
                apellido = resultado.getString(8);
                telefono = resultado.getString(9);

                listaUsuarios.add(new UsuarioResponsable(dni, nombre, apellido, telefono,
                        id_usuario, seudonimo, email, fecha_nac, fecha_ingreso, null, null));
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
        return listaUsuarios;
    }

    @Override
    public List<Atributo> listaAtributosUsuario(int id_usuario) {
        List<Atributo> listaAtributos = new ArrayList<Atributo>();
        PreparedStatement sentencia = null;
        ResultSet resultado = null;

        try {
            abrirConexion();
            this.conexion.setAutoCommit(false);
            sentencia = this.conexion.prepareStatement(
                    "SELECT a.id_atributo, a.nombre, a.descripcion, "
                    + "ua.experiencia, ua.id_usuario "
                    + "FROM atributo a, usuario_atributo ua "
                    + "WHERE ua.id_usuario = ? "
                    + "AND ua.id_atributo = a.id_atributo ");

            sentencia.setInt(1, id_usuario);
            resultado = sentencia.executeQuery();
            int id_atributo, experiencia;
            String nombre, descripcion = "";
            Date fecha;
            LocalTime hora;

            while (resultado.next()) {
                id_atributo = resultado.getInt(1);
                nombre = resultado.getString(2);
                descripcion = resultado.getString(3);
                experiencia = resultado.getInt(4);
                listaAtributos.add(new Atributo(id_atributo, nombre, descripcion, experiencia, id_usuario));
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
        return listaAtributos;
    }
}
