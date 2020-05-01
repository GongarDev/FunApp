package util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author melkart
 */
public class ConfiguracionBD {

    private String puerto_sgbd;
    private String host_sgbd;
    private String db;
    private String usuario;
    private String contrasenia;

    public void importar() {

        Properties importar = new Properties();

        try {

            importar.load(new FileInputStream("database.props"));
            this.puerto_sgbd = importar.getProperty("puerto_sgbd");
            this.host_sgbd = importar.getProperty("host_sgbd");
            this.db = importar.getProperty("db");
            this.usuario = importar.getProperty("usuario");
            this.contrasenia = importar.getProperty("contrasenia");

        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public String getPuerto_sgbd() {
        return puerto_sgbd;
    }

    public String getHost_sgbd() {
        return host_sgbd;
    }

    public String getDb() {
        return db;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }
}
