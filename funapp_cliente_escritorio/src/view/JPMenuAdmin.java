package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

/**
 *
 * @author melkart
 */
public class JPMenuAdmin extends javax.swing.JPanel {

    private JPSesionAdmin parent;

    public JPMenuAdmin() {
        initComponents();
        iniciarLayout();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPSecciones = new javax.swing.JPanel();
        jBAnuncios = new javax.swing.JButton();
        jBIncidencias = new javax.swing.JButton();
        jBUsuarios = new javax.swing.JButton();
        jBEventos = new javax.swing.JButton();
        jBUsuarios1 = new javax.swing.JButton();

        jBAnuncios.setFont(new java.awt.Font("Ubuntu", 1, 14)); // NOI18N
        jBAnuncios.setText("   Anuncios");
        jBAnuncios.setBorder(null);
        jBAnuncios.setFocusPainted(false);
        jBAnuncios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAnunciosActionPerformed(evt);
            }
        });

        jBIncidencias.setFont(new java.awt.Font("Ubuntu", 1, 14)); // NOI18N
        jBIncidencias.setText("   Incidencias");
        jBIncidencias.setBorder(null);
        jBIncidencias.setFocusPainted(false);
        jBIncidencias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBIncidenciasActionPerformed(evt);
            }
        });

        jBUsuarios.setFont(new java.awt.Font("Ubuntu", 1, 14)); // NOI18N
        jBUsuarios.setText("   Usuarios Resp.");
        jBUsuarios.setBorder(null);
        jBUsuarios.setFocusPainted(false);
        jBUsuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBUsuariosActionPerformed(evt);
            }
        });

        jBEventos.setFont(new java.awt.Font("Ubuntu", 1, 14)); // NOI18N
        jBEventos.setText("   Eventos");
        jBEventos.setBorder(null);
        jBEventos.setFocusPainted(false);
        jBEventos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBEventosActionPerformed(evt);
            }
        });

        jBUsuarios1.setFont(new java.awt.Font("Ubuntu", 1, 14)); // NOI18N
        jBUsuarios1.setText("   Usuarios Estandar");
        jBUsuarios1.setBorder(null);
        jBUsuarios1.setFocusPainted(false);
        jBUsuarios1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBUsuarios1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPSeccionesLayout = new javax.swing.GroupLayout(jPSecciones);
        jPSecciones.setLayout(jPSeccionesLayout);
        jPSeccionesLayout.setHorizontalGroup(
            jPSeccionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPSeccionesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPSeccionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jBUsuarios)
                    .addComponent(jBIncidencias)
                    .addComponent(jBAnuncios)
                    .addComponent(jBEventos)
                    .addComponent(jBUsuarios1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPSeccionesLayout.setVerticalGroup(
            jPSeccionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPSeccionesLayout.createSequentialGroup()
                .addGap(61, 61, 61)
                .addComponent(jBAnuncios)
                .addGap(42, 42, 42)
                .addComponent(jBIncidencias)
                .addGap(42, 42, 42)
                .addComponent(jBEventos)
                .addGap(42, 42, 42)
                .addComponent(jBUsuarios)
                .addGap(42, 42, 42)
                .addComponent(jBUsuarios1)
                .addContainerGap(138, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPSecciones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPSecciones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jBIncidenciasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBIncidenciasActionPerformed
        this.parent.vistaIncidencias();
    }//GEN-LAST:event_jBIncidenciasActionPerformed

    private void jBAnunciosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAnunciosActionPerformed
        this.parent.vistaAnuncios();
     }//GEN-LAST:event_jBAnunciosActionPerformed

    private void jBEventosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBEventosActionPerformed
        this.parent.vistaEventos();
    }//GEN-LAST:event_jBEventosActionPerformed

    private void jBUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBUsuariosActionPerformed
        this.parent.vistaUsuariosResponsable();
    }//GEN-LAST:event_jBUsuariosActionPerformed

    private void jBUsuarios1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBUsuarios1ActionPerformed
        this.parent.vistaUsuariosEstandar();
    }//GEN-LAST:event_jBUsuarios1ActionPerformed

    private void iniciarLayout() {

        this.setBackground(Color.decode("#012e33"));

        //Sección Menú
        this.jPSecciones.setBackground(Color.decode("#012e33"));

        Image imgAnuncios = new ImageIcon(getClass().getResource("/imagenes/mis_eventos.png")).getImage();
        Image imgAnunciosResc = imgAnuncios.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
        this.jBAnuncios.setIcon(new ImageIcon(imgAnunciosResc));
        this.jBAnuncios.setBackground(Color.decode("#012e33"));
        this.jBAnuncios.setForeground(Color.decode("#ffffff"));
        this.jBAnuncios.setContentAreaFilled(false);
        this.jBAnuncios.setOpaque(true);
        this.jBAnuncios.setHorizontalTextPosition(SwingConstants.RIGHT);
        this.jBAnuncios.setVerticalTextPosition(SwingConstants.CENTER);
        this.jBAnuncios.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        Image imgIncidencias = new ImageIcon(getClass().getResource("/imagenes/notificacion1.png")).getImage();
        Image imgIncidenciasResc = imgIncidencias.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
        this.jBIncidencias.setIcon(new ImageIcon(imgIncidenciasResc));
        this.jBIncidencias.setBackground(Color.decode("#012e33"));
        this.jBIncidencias.setForeground(Color.decode("#ffffff"));
        this.jBIncidencias.setContentAreaFilled(false);
        this.jBIncidencias.setOpaque(true);
        this.jBIncidencias.setHorizontalTextPosition(SwingConstants.RIGHT);
        this.jBIncidencias.setVerticalTextPosition(SwingConstants.CENTER);
        this.jBIncidencias.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        Image imgUsuarios = new ImageIcon(getClass().getResource("/imagenes/seguidores.png")).getImage();
        Image imgUsuariosResc = imgUsuarios.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
        this.jBUsuarios.setIcon(new ImageIcon(imgUsuariosResc));
        this.jBUsuarios.setBackground(Color.decode("#012e33"));
        this.jBUsuarios.setForeground(Color.decode("#ffffff"));
        this.jBUsuarios.setContentAreaFilled(false);
        this.jBUsuarios.setOpaque(true);
        this.jBUsuarios.setHorizontalTextPosition(SwingConstants.RIGHT);
        this.jBUsuarios.setVerticalTextPosition(SwingConstants.CENTER);
        this.jBUsuarios.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        this.jBUsuarios1.setIcon(new ImageIcon(imgUsuariosResc));
        this.jBUsuarios1.setBackground(Color.decode("#012e33"));
        this.jBUsuarios1.setForeground(Color.decode("#ffffff"));
        this.jBUsuarios1.setContentAreaFilled(false);
        this.jBUsuarios1.setOpaque(true);
        this.jBUsuarios1.setHorizontalTextPosition(SwingConstants.RIGHT);
        this.jBUsuarios1.setVerticalTextPosition(SwingConstants.CENTER);
        this.jBUsuarios1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        Image imgEventos = new ImageIcon(getClass().getResource("/imagenes/suscripciones.png")).getImage();
        Image imgEventosResc = imgEventos.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
        this.jBEventos.setIcon(new ImageIcon(imgEventosResc));
        this.jBEventos.setBackground(Color.decode("#012e33"));
        this.jBEventos.setForeground(Color.decode("#ffffff"));
        this.jBEventos.setContentAreaFilled(false);
        this.jBEventos.setOpaque(true);
        this.jBEventos.setHorizontalTextPosition(SwingConstants.RIGHT);
        this.jBEventos.setVerticalTextPosition(SwingConstants.CENTER);
        this.jBEventos.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    public void setParent(JPSesionAdmin parent) {
        this.parent = parent;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBAnuncios;
    private javax.swing.JButton jBEventos;
    private javax.swing.JButton jBIncidencias;
    private javax.swing.JButton jBUsuarios;
    private javax.swing.JButton jBUsuarios1;
    private javax.swing.JPanel jPSecciones;
    // End of variables declaration//GEN-END:variables
}
