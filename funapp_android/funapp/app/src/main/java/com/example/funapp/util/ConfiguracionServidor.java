package com.example.funapp.util;

import android.content.Context;
import android.content.res.AssetManager;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfiguracionServidor {

    private String puerto_servidor;
    private String ip_servidor;

    public void importar(Context context) {

        Properties properties = new Properties();

        try {

            AssetManager assetManager = context.getAssets();
            InputStream inputStream = assetManager.open("servidor.props");
            properties.load(inputStream);
            this.puerto_servidor = properties.getProperty("puerto_servidor");
            this.ip_servidor = properties.getProperty("ip_servidor");

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
