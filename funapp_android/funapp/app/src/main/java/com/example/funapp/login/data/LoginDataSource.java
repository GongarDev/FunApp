package com.example.funapp.login.data;

import android.os.StrictMode;
import android.util.Log;

import com.example.funapp.login.LoginActivity;
import com.example.funapp.login.data.model.LoggedInUser;
import com.example.funapp.models.Credenciales;
import com.example.funapp.models.Usuario;
import com.example.funapp.models.UsuarioEstandar;
import com.example.funapp.models.UsuarioResponsable;
import com.example.funapp.util.ConfiguracionServidor;
import com.example.funapp.util.Protocolo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.commons.codec.binary.Hex;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource implements Protocolo {

    private MessageDigest md;
    private Socket socket;
    private DataOutputStream salida;
    private DataInputStream entrada;
    private String mensaje;
    private Gson gson;
    private Integer estadoSesion;
    private Usuario usuario;

    public LoginDataSource(){
        this.socket = null;
        this.salida = null;
        this.entrada = null;
        this.gson = new GsonBuilder().setDateFormat("dd-MM-yyyy").create();
        this.estadoSesion = SIN_SESION;
    }

    public Result<LoggedInUser> login(String username, String password) {

        try {

            //ConfiguracionServidor c = new ConfiguracionServidor();
            //c.importar();

            this.socket = new Socket("192.168.1.144", 8036);
            this.salida = new DataOutputStream(this.socket.getOutputStream());
            this.entrada = new DataInputStream(this.socket.getInputStream());

            Log.v("LoginDataSource", this.socket.toString());

            Integer protocolo = INICIAR_SESION;

            this.mensaje = this.gson.toJson(protocolo);
            this.salida.writeUTF(this.mensaje);

            Credenciales credenciales = new Credenciales(username, encriptacion(password));

            this.mensaje = this.gson.toJson(credenciales);
            this.salida.writeUTF(this.mensaje);

            this.mensaje = (String) this.entrada.readUTF();
            this.estadoSesion= (Integer) this.gson.fromJson(this.mensaje, Integer.class);

            if (this.estadoSesion == SESION_ABIERTA_ESTANDAR) {
                this.mensaje = (String) this.entrada.readUTF();
                this.usuario = (UsuarioEstandar) this.gson.fromJson(this.mensaje, UsuarioEstandar.class);
            } else if(this.estadoSesion == SESION_ABIERTA_RESPONSABLE){
                this.mensaje = (String) this.entrada.readUTF();
                this.usuario = (UsuarioResponsable) this.gson.fromJson(this.mensaje, UsuarioResponsable.class);
            }else if(this.estadoSesion == SESION_FALLIDA){
                this.mensaje = (String) this.entrada.readUTF();
                this.usuario = this.gson.fromJson(this.mensaje, Usuario.class);
                this.socket.close();
                return new Result.Error(new IOException("El correo o contraseña no coincide con ningún usuario"));
            }

            LoggedInUser usuario =
                    new LoggedInUser(
                            java.util.UUID.randomUUID().toString(),
                            "Sesión iniciada", this.socket, this.usuario, this.estadoSesion);
            return new Result.Success<>(usuario);
        } catch (Exception e) {
            e.printStackTrace();
            Log.v("LoginDataSource", "");

            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }

    public String encriptacion(String cadena) {

        String cadenaEncriptada = "";
        try {
            this.md = MessageDigest.getInstance("SHA-512");
            this.md.update(cadena.getBytes());
            byte[] mb = md.digest();
            cadenaEncriptada = String.copyValueOf(Hex.encodeHex(mb));
            System.out.println(cadenaEncriptada);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(LoginActivity.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cadenaEncriptada;
    }
}
