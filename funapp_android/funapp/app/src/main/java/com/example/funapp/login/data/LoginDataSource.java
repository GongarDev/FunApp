package com.example.funapp.login.data;

import android.util.Log;

import com.example.funapp.login.LoginActivity;
import com.example.funapp.login.data.model.LoggedInUser;
import com.example.funapp.models.Credenciales;
import com.example.funapp.models.Usuario;
import com.example.funapp.models.UsuarioEstandar;
import com.example.funapp.models.UsuarioResponsable;
import com.example.funapp.util.Protocolo;
import com.example.funapp.util.SocketHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.codec.binary.Hex;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource implements Protocolo {

    private MessageDigest md;
    private String mensaje;
    private Gson gson;
    private Integer estadoSesion;
    private Usuario usuario;
    private String display;

    public LoginDataSource(){
        this.gson = new GsonBuilder().setDateFormat("dd-MM-yyyy").create();
        this.usuario = null;
        this.estadoSesion = SIN_SESION;
        this.display = "";
    }

    public Result<LoggedInUser> login(String username, String password) {

        try {

            SocketHandler.abrirSocket();
            Integer protocolo = INICIAR_SESION;

            this.mensaje = this.gson.toJson(protocolo);
            SocketHandler.getSalida().writeUTF(this.mensaje);

            Credenciales credenciales = new Credenciales(username, encriptacion(password));

            this.mensaje = this.gson.toJson(credenciales);
            SocketHandler.getSalida().writeUTF(this.mensaje);

            this.mensaje = (String) SocketHandler.getEntrada().readUTF();
            this.estadoSesion= (Integer) this.gson.fromJson(this.mensaje, Integer.class);

            if (this.estadoSesion == SESION_ABIERTA_ESTANDAR) {
                this.mensaje = (String) SocketHandler.getEntrada().readUTF();
                this.usuario = (UsuarioEstandar) this.gson.fromJson(this.mensaje, UsuarioEstandar.class);
                this.display = "Sesión iniciada";
            } else if(this.estadoSesion == SESION_ABIERTA_RESPONSABLE){
                this.mensaje = (String) SocketHandler.getEntrada().readUTF();
                this.usuario = (UsuarioResponsable) this.gson.fromJson(this.mensaje, UsuarioResponsable.class);
                this.display = "Sesión iniciada";
            }else if(this.estadoSesion == SESION_FALLIDA){
                this.mensaje = (String) SocketHandler.getEntrada().readUTF();
                this.usuario = this.gson.fromJson(this.mensaje, Usuario.class);
                this.display = "El correo o contraseña no coincide con ningún usuario";
                SocketHandler.cerrarSocket();
            }

            Log.v("LoginDatSource - estado",String.valueOf(this.estadoSesion));
            LoggedInUser usuario =
                    new LoggedInUser(
                            java.util.UUID.randomUUID().toString(),
                            this.display, this.usuario, this.estadoSesion);
            return new Result.Success<>(usuario);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result.Error(new IOException("Error login", e));
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
