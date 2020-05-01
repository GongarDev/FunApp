package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Credenciales;
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

    public UsuarioDAOSQL() {

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
    public boolean existeUsuario(Credenciales credenciales) {

        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        boolean existe = false;

        try {
            abrirConexion();
            sentencia = this.conexion.prepareStatement(
                    "SELECT email "
                    + "FROM usuario "
                    + "WHERE email=? "
                    + " AND contrasenia=? ");

            sentencia.setString(1, credenciales.getEmail());
            sentencia.setString(2, credenciales.getContrasenia());
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
                    + "u.contrasenia, u.codigo_qr "
                    + "FROM usuario u, estandar e "
                    + "WHERE u.email=? "
                    + " AND u.contrasenia=? "
                    + " AND u.id_usuario=e.id_usuario ");

            sentencia.setString(1, credenciales.getEmail());
            sentencia.setString(2, credenciales.getContrasenia());
            resultado = sentencia.executeQuery();

            while (resultado.next()) {
                estandar = new UsuarioEstandar(
                        resultado.getBoolean(1),
                        resultado.getInt(2),
                        resultado.getString(3),
                        resultado.getString(4),
                        resultado.getDate(5),
                        resultado.getDate(6),
                        resultado.getString(7),
                        resultado.getString(8)
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
                    + "u.contrasenia, u.codigo_qr "
                    + "FROM usuario u, responsable r "
                    + "WHERE u.email=? "
                    + "AND u.contrasenia=? "
                    + "AND u.id_usuario=r.id_usuario ");

            sentencia.setString(1, credenciales.getEmail());
            sentencia.setString(2, credenciales.getContrasenia());
            resultado = sentencia.executeQuery();

            while (resultado.next()) {
                responsable = new UsuarioResponsable(
                        resultado.getString(1),
                        resultado.getString(2),
                        resultado.getString(3),
                        resultado.getInt(4),
                        resultado.getInt(5),
                        resultado.getString(6),
                        resultado.getString(7),
                        resultado.getDate(8),
                        resultado.getDate(9),
                        resultado.getString(10),
                        resultado.getString(11)
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
        return responsable;
    }

    @Override
    public boolean altaUsEstandar(UsuarioEstandar usuario) {

        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        int id = 0;
        boolean insertado = false;

        try {
            abrirConexion();
            sentencia = this.conexion.prepareStatement(
                    "INSERT INTO usuario (seudonimo, email, fecha_nac, fecha_ingreso, contrasenia, código_qr) "
                    + "VALUES (?, ?, ?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);

            sentencia.setString(1, usuario.getSeudonimo());
            sentencia.setString(2, usuario.getEmail());
            sentencia.setDate(3, java.sql.Date.valueOf(usuario.getFecha_nac_LocalDate()));
            sentencia.setDate(4, java.sql.Date.valueOf(usuario.getFecha_ingreso_LocalDate()));
            sentencia.setString(5, usuario.getContrasenia());
            sentencia.setString(6, usuario.getCodigo_qr());

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

        try {
            abrirConexion();
            sentencia = this.conexion.prepareStatement(
                    "INSERT INTO usuario (seudonimo, email, fecha_nac, fecha_ingreso, contrasenia, código_qr) "
                    + "VALUES (?, ?, ?, ?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS);

            sentencia.setString(1, usuario.getSeudonimo());
            sentencia.setString(2, usuario.getEmail());
            sentencia.setDate(3, java.sql.Date.valueOf(usuario.getFecha_nac_LocalDate()));
            sentencia.setDate(4, java.sql.Date.valueOf(usuario.getFecha_ingreso_LocalDate()));
            sentencia.setString(5, usuario.getContrasenia());
            sentencia.setString(6, usuario.getCodigo_qr());

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
            sentencia.setInt(5, usuario.getTelefono());

            affectedRows = sentencia.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("No se puede insertar los datos.");
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
    public boolean actualizarUsEstandar(UsuarioEstandar usuario) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean actualizarUsResponsable(UsuarioResponsable usuario) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean bajaUsEstandar(UsuarioEstandar usuario) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean bajaUsResponsable(UsuarioResponsable usuario) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
