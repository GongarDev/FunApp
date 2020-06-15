
package util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author melkart
 */
public class ConfiguracionServidor {

    private String puerto_servidor;
    private String ip_servidor;

    public void importar() {

        Properties importar = new Properties();
        try {
            importar.load(new FileInputStream("servidor.props"));
            this.puerto_servidor = importar.getProperty("puerto_servidor");
            this.ip_servidor = importar.getProperty("ip_servidor");
        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    public int getPuerto_servidor() {
        return Integer.valueOf(puerto_servidor);
    }

    public String getIp_servidor() {
        return ip_servidor;
    }
}
