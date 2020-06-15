/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import com.google.gson.reflect.TypeToken;
import java.awt.Color;
import java.awt.Cursor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import model.UsuarioAdmin;
import model.UsuarioResponsable;
import util.Protocolo;

/**
 *
 * @author melkart
 */
public class JPUsuariosResp extends javax.swing.JPanel implements Protocolo {

    private VentanaPrincipal parent;
    private JPSesionAdmin vistaSesionAdmin;
    private List<UsuarioResponsable> listaUsuarios;
    private UsuarioAdmin admin;
    private UsuariosRespTableModel usuariosTM;
    private String mensaje;
    private ListSelectionModel _lsm;

    public JPUsuariosResp(VentanaPrincipal parent, JPSesionAdmin vistaSesionAdmin) {
        this.usuariosTM = new UsuariosRespTableModel();
        initComponents();
        this.parent = parent;
        this.vistaSesionAdmin = vistaSesionAdmin;
        refrescarTablaUsuarios();
        this.jTUsuarios.setModel(this.usuariosTM);
        this.jTUsuarios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        iniciarLayout();
    }

        @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPPrincipal = new javax.swing.JPanel();
        jLMisEventos = new javax.swing.JLabel();
        jSeparator = new javax.swing.JSeparator();
        jSPAnuncios = new javax.swing.JScrollPane();
        jTUsuarios = new javax.swing.JTable();
        jBBorrarSeleccion = new javax.swing.JButton();

        jPPrincipal.setPreferredSize(new java.awt.Dimension(726, 625));

        jLMisEventos.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        jLMisEventos.setText("Usuarios Responsable");

        jSPAnuncios.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jSPAnuncios.setMinimumSize(new java.awt.Dimension(803, 70));

        jTUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jSPAnuncios.setViewportView(jTUsuarios);

        jBBorrarSeleccion.setText("Borrar Selección");
        jBBorrarSeleccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBBorrarSeleccionActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPPrincipalLayout = new javax.swing.GroupLayout(jPPrincipal);
        jPPrincipal.setLayout(jPPrincipalLayout);
        jPPrincipalLayout.setHorizontalGroup(
            jPPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPPrincipalLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator)
                    .addGroup(jPPrincipalLayout.createSequentialGroup()
                        .addComponent(jLMisEventos)
                        .addGap(0, 512, Short.MAX_VALUE))
                    .addComponent(jBBorrarSeleccion, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
            .addGroup(jPPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPPrincipalLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jSPAnuncios, javax.swing.GroupLayout.PREFERRED_SIZE, 702, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPPrincipalLayout.setVerticalGroup(
            jPPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPPrincipalLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLMisEventos)
                .addGap(0, 0, 0)
                .addComponent(jSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 319, Short.MAX_VALUE)
                .addComponent(jBBorrarSeleccion, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(jPPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPPrincipalLayout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jSPAnuncios, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(56, 56, 56)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPPrincipal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 413, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jBBorrarSeleccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBBorrarSeleccionActionPerformed
        int id_usuario = (int) this.jTUsuarios.getModel().getValueAt(this.jTUsuarios.getSelectedRow(), 0);

        int confirmado = JOptionPane.showConfirmDialog(
                this,
                "¿Confirmas eliminar el usuario seleccionado?", "Atención", JOptionPane.YES_NO_OPTION);
        if (JOptionPane.OK_OPTION == confirmado) {
            eliminarUsuario(id_usuario);
            refrescarTablaUsuarios();
        }
    }//GEN-LAST:event_jBBorrarSeleccionActionPerformed

        public void refrescarTablaUsuarios() {
        llenarListUsuarios();
        this.usuariosTM.refreshTableModel(this.listaUsuarios);
    }

    public void actualizar() {
        //vistaSesionAdmin.vistaCrearEvento();
        vistaSesionAdmin.vistaAnuncios();
    }

    public void eliminarUsuario(int id_usuario) {

        try {
            Integer protocolo = ADMIN_ELIMINAR_USUARIO_RESPONSABLE;
            this.mensaje = this.parent.getGson().toJson(protocolo);
            this.parent.getSalida().writeUTF(this.mensaje);
            this.mensaje = this.parent.getGson().toJson(id_usuario);
            this.parent.getSalida().writeUTF(this.mensaje);
            this.mensaje = (String) this.parent.getEntrada().readUTF();
        } catch (IOException ex) {
            Logger.getLogger(JPCrearEvento.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void llenarListUsuarios() {
        try {
            Integer protocolo = ADMIN_VER_USUARIOS_RESPONSABLE;
            this.mensaje = this.parent.getGson().toJson(protocolo);
            this.parent.getSalida().writeUTF(this.mensaje);
            this.mensaje = (String) this.parent.getEntrada().readUTF();
        } catch (IOException ex) {
            Logger.getLogger(JPCrearEvento.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.listaUsuarios
                = (ArrayList) this.parent.getGson().fromJson(
                        this.mensaje, new TypeToken<ArrayList<UsuarioResponsable>>() {
                        }.getType());
    }

    public void iniciarLayout() {

        this.jPPrincipal.setBackground(Color.decode("#012326"));
        this.jLMisEventos.setForeground(Color.decode("#F2F2F2"));
        this.jSeparator.setBackground(Color.decode("#d64d55"));
        this.jSeparator.setForeground(Color.decode("#d64d55"));
        this.jBBorrarSeleccion.setBackground(Color.decode("#94c8d6"));
        this.jBBorrarSeleccion.setOpaque(true);
        this.jBBorrarSeleccion.setHorizontalAlignment(SwingConstants.CENTER);
        this.jBBorrarSeleccion.setBorder(BorderFactory.createLineBorder(Color.decode("#94c8d6")));
        this.jBBorrarSeleccion.setForeground(Color.decode("#f2f2f2"));
        this.jBBorrarSeleccion.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        this.jBBorrarSeleccion.setFocusPainted(false);
    }

    public void setParent(VentanaPrincipal parent) {
        this.parent = parent;
    }

    public void setJPSesionAdmin(JPSesionAdmin jPSesionAdmin) {
        this.vistaSesionAdmin = jPSesionAdmin;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBBorrarSeleccion;
    private javax.swing.JLabel jLMisEventos;
    private javax.swing.JPanel jPPrincipal;
    private javax.swing.JScrollPane jSPAnuncios;
    private javax.swing.JSeparator jSeparator;
    private javax.swing.JTable jTUsuarios;
    // End of variables declaration//GEN-END:variables
}
