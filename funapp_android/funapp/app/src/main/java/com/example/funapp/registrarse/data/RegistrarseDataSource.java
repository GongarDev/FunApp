package com.example.funapp.registrarse.data;

import android.util.Log;

import com.example.funapp.models.Credenciales;
import com.example.funapp.models.Usuario;
import com.example.funapp.models.UsuarioEstandar;
import com.example.funapp.models.UsuarioResponsable;
import com.example.funapp.registrarse.RegistrarseActivity;
import com.example.funapp.registrarse.data.model.RegistradoInUser;
import com.example.funapp.util.Protocolo;
import com.example.funapp.util.SocketHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.commons.codec.binary.Hex;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RegistrarseDataSource implements Protocolo {

    private MessageDigest md;
    private String mensaje;
    private Gson gson;
    private Integer estadoSesion;
    private Usuario usuario;
    private String display;

    public RegistrarseDataSource(){
        this.gson = new GsonBuilder().setDateFormat("dd-MM-yyyy").create();
        this.estadoSesion = SIN_SESION;
    }

    public Result<RegistradoInUser> registrarse(String seudonimo, String contrasenia, String confirmarContrasenia,
                                                String correo, String fechaNacimiento, boolean responsable) {

        try {

            SocketHandler.abrirSocket();

            if(responsable){
                Integer protocolo = REGISTRARSE_RESPONSABLE;
                this.mensaje = this.gson.toJson(protocolo);
                SocketHandler.getSalida().writeUTF(this.mensaje);
                Date fechaNacDate = new SimpleDateFormat("dd-MM-yyyy").parse(fechaNacimiento);
                UsuarioResponsable usuarioResponsable = new UsuarioResponsable(
                        null, null, null, 0, 0, seudonimo,
                        correo, fechaNacDate, null, encriptacion(contrasenia), null
                );
                this.mensaje = this.gson.toJson(usuarioResponsable);
                SocketHandler.getSalida().writeUTF(this.mensaje);
            }else{
                Integer protocolo = REGISTRARSE_ESTANDAR;
                this.mensaje = this.gson.toJson(protocolo);
                SocketHandler.getSalida().writeUTF(this.mensaje);
                Date fechaNacDate = new SimpleDateFormat("dd-MM-yyyy").parse(fechaNacimiento);
                UsuarioEstandar usuarioEstandar = new UsuarioEstandar(
                        false, 0, seudonimo, correo, fechaNacDate,
                        null, encriptacion(contrasenia), null
                );
                this.mensaje = this.gson.toJson(usuarioEstandar);
                SocketHandler.getSalida().writeUTF(this.mensaje);
            }

            Log.v("RegistrarseDataSource",this.mensaje);
            this.mensaje = (String) SocketHandler.getEntrada().readUTF();
            Log.v("RegistrarseDataSource",this.mensaje);
            this.estadoSesion= (Integer) this.gson.fromJson(this.mensaje, Integer.class);

            if (this.estadoSesion == SIN_SESION) {
                this.display = "Se ha registrado con éxito, ahora podrás iniciar sesión.";
            } else if(this.estadoSesion == REGISTRARSE_EXISTE_USUARIO){
                this.display = "Ya existe un usuario registrado con ese correo electrónico.";
            }else if(this.estadoSesion == REGISTRARSE_EXISTE_SEUDONIMO){
                this.display = "El nombre de usuario introducio no está disponible.";
            }else if(this.estadoSesion == REGISTRARSE_FALLIDO){
                this.display = "No se puede realizar la operación, inténtelo más tarde.";
            }

            RegistradoInUser usuario =
                    new RegistradoInUser(
                            java.util.UUID.randomUUID().toString(),
                            this.display, this.estadoSesion);

            return new Result.Success<>(usuario);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result.Error(new IOException("Error en registrarse", e));
        }finally{
            SocketHandler.cerrarSocket();
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
            Logger.getLogger(RegistrarseActivity.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cadenaEncriptada;
    }
}
