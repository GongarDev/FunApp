package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Credenciales;
import model.Usuario;
import model.UsuarioEstandar;
import model.UsuarioResponsable;
import util.Protocolo;

/**
 *
 * @author melkart
 */
public class HiloServidor extends Thread implements Protocolo {

    private DataOutputStream salida;
    private DataInputStream entrada;
    private Integer estadoSesion;
    private Gson gson;
    private String mensaje;
    private Usuario usuario;
    private Controlador controlador;

    public HiloServidor(Socket socket) {

        try {
            this.salida = new DataOutputStream(socket.getOutputStream());
            this.entrada = new DataInputStream(socket.getInputStream());
            this.estadoSesion = SIN_SESION;
            this.controlador = new Controlador();
            this.gson = new GsonBuilder().setDateFormat("dd-MM-yyyy").create();
            this.usuario = null;
        } catch (IOException e) {
            System.out.println("ERROR DE E/S en HiloCliente");
            e.printStackTrace();
        }
    }

    public void run() {

        try {
            String estadoJson = (String) this.entrada.readUTF();
            this.estadoSesion = gson.fromJson(estadoJson, Integer.class);

            if (this.estadoSesion == INICIAR_SESION) {

                this.mensaje = (String) this.entrada.readUTF();
                Credenciales credenciales = this.gson.fromJson(this.mensaje, Credenciales.class);

                this.usuario = (UsuarioEstandar) this.controlador.buscarUsEstandar(credenciales);

                if (this.usuario != null) {
                    this.estadoSesion = SESION_ABIERTA_ESTANDAR;
                } else {
                    this.usuario = (UsuarioResponsable) this.controlador.buscarUsResponsable(credenciales);
                    if (this.usuario != null) {
                        this.estadoSesion = SESION_ABIERTA_RESPONSABLE;
                    } else {
                        this.estadoSesion = SESION_FALLIDA;
                    }
                }

                this.mensaje = gson.toJson(this.estadoSesion);
                this.salida.writeUTF(this.mensaje);
                this.mensaje = gson.toJson(this.usuario);
                this.salida.writeUTF(this.mensaje);

            } else if (this.estadoSesion == REGISTRARSE_RESPONSABLE) {

                this.mensaje = (String) this.entrada.readUTF();
                UsuarioResponsable usuario = this.gson.fromJson(this.mensaje, UsuarioResponsable.class);
                boolean insertado = this.controlador.insertarUsResponsable(usuario);
                if (insertado) {
                    this.estadoSesion = SIN_SESION;
                } else {
                    this.estadoSesion = REGISTRARSE_FALLIDO;
                }
                this.mensaje = gson.toJson(this.estadoSesion);
                this.salida.writeUTF(this.mensaje);

            } else if (this.estadoSesion == REGISTRARSE_ESTANDAR) {

                this.mensaje = (String) this.entrada.readUTF();
                UsuarioEstandar usuario = this.gson.fromJson(this.mensaje, UsuarioEstandar.class);
                boolean insertado = this.controlador.insertarUsEstandar(usuario);
                if (insertado) {
                    this.estadoSesion = SIN_SESION;
                } else {
                    this.estadoSesion = REGISTRARSE_FALLIDO;
                }
                this.mensaje = gson.toJson(this.estadoSesion);
                this.salida.writeUTF(this.mensaje);
            }
        
            
            while(this.estadoSesion == SESION_ABIERTA_ESTANDAR ||
                    this.estadoSesion == SESION_ABIERTA_RESPONSABLE){
                
            }

        } catch (IOException ex) {
            Logger.getLogger(HiloServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
