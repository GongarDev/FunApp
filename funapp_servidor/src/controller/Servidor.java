package controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import util.ConfiguracionServidor;


/**
 *
 * @author melkart
 */
public class Servidor{

    private ServerSocket servidor;
    private int usuarios;
    private ExecutorService executor;
    
    public Servidor() {

        ConfiguracionServidor c = new ConfiguracionServidor();
        c.importar();
        this.servidor = null;
        this.usuarios = 0;
        this.executor = Executors.newCachedThreadPool();
        
        try {
            this.servidor = new ServerSocket(c.getPuerto_servidor());
            System.out.println("Servidor iniciado.");
            System.out.println("-----------------FunApp--------------------");
        } catch (IOException e) {
            System.err.println("No se puede utilizar el puerto indicado.");
            System.exit(1);
        }        
    }
    
    private void listen() {

        Socket clienteSocket = null; 
        
        while (true) {
            try {

                System.out.println("Número total de usuarios que se han conectado o lo han intentado: " + this.usuarios);
                System.out.println("\nEsperando usuario.");

                clienteSocket = this.servidor.accept();
                System.out.println("Usuario conectado.");
                
                

                this.executor.execute(new HiloServidor(clienteSocket));
                this.usuarios++;
            } catch (IOException ioe) {
                ioe.printStackTrace();
                System.err.println("Fallo al aceptar el cliente.");
            }
        }
    }
    
    public static void main(String[] args) {
        new Servidor().listen();
    }
}
