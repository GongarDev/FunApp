package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Image;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import model.Credenciales;
import model.Usuario;
import model.UsuarioEstandar;
import model.UsuarioResponsable;
import util.Protocolo;
import util.TextPrompt;

/**
 *
 * @author melkart
 */
public class JPInicioSesion extends javax.swing.JPanel implements Protocolo {

    private VentanaPrincipal parent;
    private TextPrompt pHUsuario;
    private TextPrompt pHContrasenia;
    private String mensaje;

    public JPInicioSesion() {
        initComponents();
        iniciarLayout();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPPrincipal = new javax.swing.JPanel();
        jLImagen2 = new javax.swing.JLabel();
        jLImagen1 = new javax.swing.JLabel();
        jTFUsuario = new javax.swing.JTextField();
        jCBRecuerdame = new javax.swing.JCheckBox();
        jBRestContrasenia = new javax.swing.JButton();
        jLImagenSeparador = new javax.swing.JLabel();
        jBIniciarSesion = new javax.swing.JButton();
        jSeparator = new javax.swing.JSeparator();
        jLRegistrate = new javax.swing.JLabel();
        jBRegistrate = new javax.swing.JButton();
        jLImagen3 = new javax.swing.JLabel();
        jPFcontrasenia = new javax.swing.JPasswordField();
        jLGestion = new javax.swing.JLabel();

        setMaximumSize(new java.awt.Dimension(399, 408));
        setMinimumSize(new java.awt.Dimension(399, 408));

        jTFUsuario.setFont(new java.awt.Font("Ubuntu", 0, 12)); // NOI18N

        jCBRecuerdame.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        jCBRecuerdame.setText("Recuérdame");

        jBRestContrasenia.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        jBRestContrasenia.setText("¿Olvidaste la contraseña?");
        jBRestContrasenia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jBRestContraseniaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jBRestContraseniaMouseExited(evt);
            }
        });
        jBRestContrasenia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBRestContraseniaActionPerformed(evt);
            }
        });

        jBIniciarSesion.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        jBIniciarSesion.setText("Iniciar sesión");
        jBIniciarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBIniciarSesionActionPerformed(evt);
            }
        });

        jLRegistrate.setFont(new java.awt.Font("Ubuntu", 1, 12)); // NOI18N
        jLRegistrate.setText("¿No tienes una cuenta?");

        jBRegistrate.setFont(new java.awt.Font("Ubuntu", 1, 14)); // NOI18N
        jBRegistrate.setText("Regístrate");
        jBRegistrate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBRegistrateActionPerformed(evt);
            }
        });

        jPFcontrasenia.setFont(new java.awt.Font("Ubuntu", 0, 12)); // NOI18N

        jLGestion.setFont(new java.awt.Font("Ubuntu", 3, 12)); // NOI18N
        jLGestion.setText("Gestión de eventos");

        javax.swing.GroupLayout jPPrincipalLayout = new javax.swing.GroupLayout(jPPrincipal);
        jPPrincipal.setLayout(jPPrincipalLayout);
        jPPrincipalLayout.setHorizontalGroup(
            jPPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPPrincipalLayout.createSequentialGroup()
                .addGroup(jPPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPPrincipalLayout.createSequentialGroup()
                        .addGap(67, 67, 67)
                        .addComponent(jBIniciarSesion, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPPrincipalLayout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addGroup(jPPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPPrincipalLayout.createSequentialGroup()
                                .addComponent(jLImagen1, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLImagen2, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLGestion)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPPrincipalLayout.createSequentialGroup()
                                .addComponent(jCBRecuerdame)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLImagenSeparador, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jBRestContrasenia))
                            .addComponent(jTFUsuario, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPPrincipalLayout.createSequentialGroup()
                                .addGroup(jPPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLRegistrate)
                                    .addGroup(jPPrincipalLayout.createSequentialGroup()
                                        .addGap(17, 17, 17)
                                        .addComponent(jBRegistrate, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addComponent(jLImagen3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jSeparator, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPFcontrasenia))))
                .addGap(50, 50, 50))
        );
        jPPrincipalLayout.setVerticalGroup(
            jPPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPPrincipalLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLImagen1, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPPrincipalLayout.createSequentialGroup()
                        .addComponent(jLGestion)
                        .addGap(2, 2, 2)
                        .addComponent(jLImagen2, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jTFUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPFcontrasenia, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(9, 9, 9)
                .addGroup(jPPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jCBRecuerdame, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLImagenSeparador, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBRestContrasenia, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addComponent(jBIniciarSesion, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPPrincipalLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLRegistrate)
                        .addGap(18, 18, 18)
                        .addComponent(jBRegistrate, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLImagen3, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jBRestContraseniaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBRestContraseniaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jBRestContraseniaActionPerformed

    private void jBRestContraseniaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBRestContraseniaMouseEntered
        if (evt.getSource() == this.jBRestContrasenia) {
            this.jBRestContrasenia.setForeground(Color.decode("#94c8d6"));
        }
    }//GEN-LAST:event_jBRestContraseniaMouseEntered

    private void jBRestContraseniaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jBRestContraseniaMouseExited
        if (evt.getSource() == this.jBRestContrasenia) {
            this.jBRestContrasenia.setForeground(Color.decode("#f2f2f2"));
        }
    }//GEN-LAST:event_jBRestContraseniaMouseExited

    private void jBIniciarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBIniciarSesionActionPerformed

        if (!this.jTFUsuario.getText().equals("") && this.jPFcontrasenia.getPassword().length != 0) {

            try {

                if (this.parent.getEstadoSesion() == SIN_SESION) {
                    this.parent.establecerSocket();

                    Integer protocolo = INICIAR_SESION;
                    this.mensaje = this.parent.getGson().toJson(protocolo);
                    this.parent.getSalida().writeUTF(this.mensaje);

                    Credenciales credenciales = new Credenciales(
                            this.jTFUsuario.getText(),
                            this.parent.encriptacion(String.copyValueOf(this.jPFcontrasenia.getPassword())));

                    this.mensaje = this.parent.getGson().toJson(credenciales);
                    this.parent.getSalida().writeUTF(this.mensaje);

                    this.mensaje = (String) this.parent.getEntrada().readUTF();
                    this.parent.setEstadoSesion((Integer) this.parent.getGson().fromJson(this.mensaje, Integer.class));

                    if (this.parent.getEstadoSesion() == SESION_ABIERTA_ESTANDAR) {
                        this.mensaje = (String) this.parent.getEntrada().readUTF();
                        this.parent.setUsuario((UsuarioEstandar) this.parent.getGson().fromJson(this.mensaje, UsuarioEstandar.class));
                    } else if (this.parent.getEstadoSesion() == SESION_ABIERTA_RESPONSABLE) {
                        this.mensaje = (String) this.parent.getEntrada().readUTF();
                        this.parent.setUsuario((UsuarioResponsable) this.parent.getGson().fromJson(this.mensaje, UsuarioResponsable.class));
                        JOptionPane.showMessageDialog(null, "Correcto");
                    } else if(this.parent.getEstadoSesion() == SESION_FALLIDA){
                        JOptionPane.showMessageDialog(null, "El correo o contraseña no coincide con ningún usuario");
                        this.mensaje = (String) this.parent.getEntrada().readUTF();
                        this.parent.setUsuario(this.parent.getGson().fromJson(this.mensaje, Usuario.class));
                        this.parent.setEstadoSesion(SIN_SESION);
                        this.parent.getCliente().close();
                        this.parent.getSalida().close();
                        this.parent.getEntrada().close();
                    }                    
                }

            } catch (IOException ex) {
                Logger.getLogger(JPInicioSesion.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            if (this.jTFUsuario.getText().equals("")) {
                this.pHUsuario.setText("Debes introducir un email");
                this.pHUsuario.setForeground(Color.decode("#d64d55"));
            }
            if (this.jPFcontrasenia.getPassword().length == 0) {
                this.pHContrasenia.setText("Debes introducir una contraseña");
                this.pHContrasenia.setForeground(Color.decode("#d64d55"));
            }
        }
    }//GEN-LAST:event_jBIniciarSesionActionPerformed

    private void jBRegistrateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBRegistrateActionPerformed
        
        this.parent.vistaRegistrarse();
        
    }//GEN-LAST:event_jBRegistrateActionPerformed

    private void iniciarLayout() {
        //Color de fondo
        this.jPPrincipal.setBackground(Color.decode("#012e33"));

        //Imagen logo
        Image imgLogo = new ImageIcon(getClass().getResource("/imagenes/logo_blanco.png")).getImage();
        Image imgLogoResc = imgLogo.getScaledInstance(62, 62, java.awt.Image.SCALE_SMOOTH);
        this.jLImagen1.setIcon(new ImageIcon(imgLogoResc));
        this.jLImagen1.setBackground(Color.decode("#012e33"));
        this.jLImagen1.setOpaque(true);
        this.jLImagen1.setHorizontalAlignment(SwingConstants.CENTER);

        //Imagen nombre
        Image imgNombre = new ImageIcon(getClass().getResource("/imagenes/nombre_azul.png")).getImage();
        Image imgNombreResc = imgNombre.getScaledInstance(180, 180, java.awt.Image.SCALE_SMOOTH);
        this.jLImagen2.setIcon(new ImageIcon(imgNombreResc));
        this.jLImagen2.setBackground(Color.decode("#012e33"));
        this.jLImagen2.setOpaque(true);
        this.jLImagen2.setHorizontalAlignment(SwingConstants.CENTER);
        this.jLGestion.setForeground(Color.decode("#94c8d6"));

        //Configuración checkbox
        this.jCBRecuerdame.setForeground(Color.decode("#f2f2f2"));
        this.jCBRecuerdame.setBackground(Color.decode("#012e33"));
        this.jCBRecuerdame.setContentAreaFilled(false);
        this.jCBRecuerdame.setFocusPainted(false);

        //Configuración botón de restablecer contraseña
        this.jBRestContrasenia.setBackground(Color.decode("#012e33"));
        this.jBRestContrasenia.setOpaque(true);
        this.jBRestContrasenia.setContentAreaFilled(false);
        this.jBRestContrasenia.setHorizontalAlignment(SwingConstants.CENTER);
        this.jBRestContrasenia.setBorder(BorderFactory.createLineBorder(Color.decode("#012e33")));
        this.jBRestContrasenia.setForeground(Color.decode("#f2f2f2"));
        this.jBRestContrasenia.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        this.jBRestContrasenia.setFocusPainted(false);

        //Configuración de los campos de textos y agregar placehorder
        this.jTFUsuario.setBorder(BorderFactory.createLineBorder(Color.decode("#ffffff")));
        this.pHUsuario = new TextPrompt("Correo electrónico", this.jTFUsuario);
        this.pHUsuario.changeAlpha(0.75f);
        this.pHUsuario.changeStyle(Font.ITALIC);
        this.jPFcontrasenia.setBorder(BorderFactory.createLineBorder(Color.decode("#ffffff")));
        this.pHContrasenia = new TextPrompt("Contraseña", this.jPFcontrasenia);
        this.pHContrasenia.changeAlpha(0.75f);
        this.pHContrasenia.changeStyle(Font.ITALIC);

        //Imagen separador círculo
        Image imgSeparador = new ImageIcon(getClass().getResource("/imagenes/separador_circulo.png")).getImage();
        Image imgSeparadorResc = imgSeparador.getScaledInstance(10, 10, java.awt.Image.SCALE_SMOOTH);
        this.jLImagenSeparador.setIcon(new ImageIcon(imgSeparadorResc));
        this.jLImagenSeparador.setBackground(Color.decode("#012e33"));
        this.jLImagenSeparador.setOpaque(true);
        this.jLImagenSeparador.setHorizontalAlignment(SwingConstants.CENTER);

        //Boton iniciar sesion
        this.jBIniciarSesion.setBackground(Color.decode("#94c8d6"));
        this.jBIniciarSesion.setOpaque(true);
        this.jBIniciarSesion.setHorizontalAlignment(SwingConstants.CENTER);
        this.jBIniciarSesion.setBorder(BorderFactory.createLineBorder(Color.decode("#94c8d6")));
        this.jBIniciarSesion.setForeground(Color.decode("#f2f2f2"));
        this.jBIniciarSesion.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        this.jBIniciarSesion.setFocusPainted(false);

        //Separador
        this.jSeparator.setBackground(Color.decode("#d64d55"));
        this.jSeparator.setForeground(Color.decode("#d64d55"));

        //Sección Registrarse
        this.jLRegistrate.setForeground(Color.decode("#f2f2f2"));

        this.jBRegistrate.setBackground(Color.decode("#94c8d6"));
        this.jBRegistrate.setOpaque(true);
        this.jBRegistrate.setHorizontalAlignment(SwingConstants.CENTER);
        this.jBRegistrate.setBorder(BorderFactory.createLineBorder(Color.decode("#94c8d6")));
        this.jBRegistrate.setForeground(Color.decode("#f2f2f2"));
        this.jBRegistrate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        this.jBRegistrate.setFocusPainted(false);

        Image imgRegistrate = new ImageIcon(getClass().getResource("/imagenes/adorno_rojo1.png")).getImage();
        Image imgRegistrateResc = imgRegistrate.getScaledInstance(110, 110, java.awt.Image.SCALE_SMOOTH);
        this.jLImagen3.setIcon(new ImageIcon(imgRegistrateResc));
        this.jLImagen3.setBackground(Color.decode("#012e33"));
        this.jLImagen3.setOpaque(true);
        this.jLImagen3.setHorizontalAlignment(SwingConstants.CENTER);
        
    }

    public void setParent(VentanaPrincipal parent) {
        this.parent = parent;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBIniciarSesion;
    private javax.swing.JButton jBRegistrate;
    private javax.swing.JButton jBRestContrasenia;
    private javax.swing.JCheckBox jCBRecuerdame;
    private javax.swing.JLabel jLGestion;
    private javax.swing.JLabel jLImagen1;
    private javax.swing.JLabel jLImagen2;
    private javax.swing.JLabel jLImagen3;
    private javax.swing.JLabel jLImagenSeparador;
    private javax.swing.JLabel jLRegistrate;
    private javax.swing.JPasswordField jPFcontrasenia;
    private javax.swing.JPanel jPPrincipal;
    private javax.swing.JSeparator jSeparator;
    private javax.swing.JTextField jTFUsuario;
    // End of variables declaration//GEN-END:variables
}
