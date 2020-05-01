package view;

import com.google.gson.Gson;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Usuario;
import util.Protocolo;
import org.apache.commons.codec.binary.Hex;
import util.ConfiguracionServidor;

/**
 *
 * @author melkart
 */
public class VentanaPrincipal extends javax.swing.JFrame implements Protocolo {

    private Socket cliente;
    private DataOutputStream salida;
    private DataInputStream entrada;
    private Integer estadoSesion;
    private Gson gson;
    private MessageDigest md;
    private Usuario usuario;
    
    private view.JPRegistrarse jPRegistrarse;

    public VentanaPrincipal() {
        initComponents();
        setParentsComponentes();
        this.cliente = null;
        this.salida = null;
        this.entrada = null;
        this.estadoSesion = SIN_SESION;
        this.gson = new Gson();

        setLocationRelativeTo(null);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPInicioSesion = new view.JPInicioSesion();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(jPInicioSesion, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public void establecerSocket() {
        try {
            ConfiguracionServidor c = new ConfiguracionServidor();
            c.importar();
            this.setCliente(new Socket(c.getIp_servidor(), c.getPuerto_servidor()));
            this.setSalida(new DataOutputStream(this.cliente.getOutputStream()));
            this.setEntrada(new DataInputStream(this.cliente.getInputStream()));
        } catch (IOException ex) {
            Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setParentsComponentes() {
        this.jPInicioSesion.setParent(this);
    }

    public Socket getCliente() {
        return cliente;
    }

    public void setCliente(Socket cliente) {
        this.cliente = cliente;
    }

    public DataOutputStream getSalida() {
        return salida;
    }

    public void setSalida(DataOutputStream salida) {
        this.salida = salida;
    }

    public DataInputStream getEntrada() {
        return entrada;
    }

    public void setEntrada(DataInputStream entrada) {
        this.entrada = entrada;
    }

    public Integer getEstadoSesion() {
        return estadoSesion;
    }

    public void setEstadoSesion(Integer estadoSesion) {
        this.estadoSesion = estadoSesion;
    }

    public Gson getGson() {
        return gson;
    }

    public void setGson(Gson gson) {
        this.gson = gson;
    }

    public MessageDigest getMd() {
        return md;
    }

    public void setMd(MessageDigest md) {
        this.md = md;
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
            Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cadenaEncriptada;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public void vistaRegistrarse() {
        this.jPInicioSesion.setVisible(false);
        this.jPRegistrarse = new view.JPRegistrarse();
        this.jPRegistrarse.setParent(this);
        this.setSize(700, 515);
        getContentPane().add(jPRegistrarse, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 700, 515));
        this.jPRegistrarse.setVisible(true);
        //this.jPRegistrarse.setPreferredSize(676, 445);
    }

    public void vistaInicioSesion() {
        this.jPInicioSesion.setVisible(true);
        this.setSize(391, 445);
        getContentPane().add(jPInicioSesion, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 391, 445));
        this.jPRegistrarse = null;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private view.JPInicioSesion jPInicioSesion;
    // End of variables declaration//GEN-END:variables
}
