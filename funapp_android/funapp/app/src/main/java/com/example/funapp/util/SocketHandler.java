package com.example.funapp.util;

import android.content.Context;
import android.widget.EditText;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SocketHandler {

    private static Socket socket;
    private static DataOutputStream salida;
    private static DataInputStream entrada;
    private static Context context;
    private static EditText ip;

    public static void abrirSocket(){

        try {

            ConfiguracionServidor c = new ConfiguracionServidor();
            c.importar(SocketHandler.context);

            if(ip.getText().toString().length()==11 ||
                    ip.getText().toString().length()==12 ||
                    ip.getText().toString().length()==13){
                SocketHandler.socket = new Socket(ip.getText().toString(), c.getPuerto_servidor());
            }else {
                SocketHandler.socket = new Socket(c.getIp_servidor(), c.getPuerto_servidor());
            }

            SocketHandler.salida = new DataOutputStream(SocketHandler.socket.getOutputStream());
            SocketHandler.entrada = new DataInputStream(SocketHandler.socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void cerrarSocket(){
        try {
            if(salida!=null)
                SocketHandler.salida.close();
            if(entrada!=null)
                SocketHandler.entrada.close();
            if(socket!=null)
                SocketHandler.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized Socket getSocket(){
        return socket;
    }

    public static synchronized void setSocket(Socket socket){
        SocketHandler.socket = socket;
    }

    public static synchronized DataOutputStream getSalida() {
        return salida;
    }

    public static synchronized void setSalida(DataOutputStream salida) {
        SocketHandler.salida = salida;
    }

    public static synchronized DataInputStream getEntrada() {
        return entrada;
    }

    public static synchronized void setEntrada(DataInputStream entrada) {
        SocketHandler.entrada = entrada;
    }

    public static synchronized Context getContext() {
        return context;
    }

    public static synchronized void setContext(Context context) {
        SocketHandler.context = context;
    }

    public static EditText getIp() {
        return ip;
    }

    public static void setIp(EditText ip) {
        SocketHandler.ip = ip;
    }
}
