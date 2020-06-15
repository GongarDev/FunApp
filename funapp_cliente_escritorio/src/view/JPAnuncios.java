package view;

import com.google.gson.reflect.TypeToken;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import model.Anuncio;
import model.UsuarioAdmin;
import util.Protocolo;
import util.TextPrompt;

/**
 *
 * @author melkart
 */
public class JPAnuncios extends javax.swing.JPanel implements Protocolo {

    private VentanaPrincipal parent;
    private JPSesionAdmin vistaSesionAdmin;
    private List<Anuncio> listaAnuncios;
    private UsuarioAdmin admin;
    private AnunciosTableModel anunciosTM;
    private TextPrompt pHNombre;
    private TextPrompt pHDescripcion;
    private String mensaje;
    private ListSelectionModel _lsm;

    public JPAnuncios(VentanaPrincipal parent, JPSesionAdmin vistaSesionAdmin) {
        this.anunciosTM = new AnunciosTableModel();
        initComponents();
        this.parent = parent;
        this.vistaSesionAdmin = vistaSesionAdmin;
        refrescarTablaAnuncios();
        this.jTAnuncios.setModel(this.anunciosTM);
        this.jTAnuncios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        iniciarLayout();

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPPrincipal = new javax.swing.JPanel();
        jLMisEventos = new javax.swing.JLabel();
        jSeparator = new javax.swing.JSeparator();
        jSPAnuncios = new javax.swing.JScrollPane();
        jTAnuncios = new javax.swing.JTable();
        jTFNombreAnuncio = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTADescripcionAnuncio = new javax.swing.JTextArea();
        jBInsertarAnuncio = new javax.swing.JButton();
        jBBorrarSeleccion = new javax.swing.JButton();

        jPPrincipal.setMinimumSize(new java.awt.Dimension(0, 0));
        jPPrincipal.setPreferredSize(new java.awt.Dimension(726, 625));

        jLMisEventos.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        jLMisEventos.setText("Anuncios");

        jSPAnuncios.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jSPAnuncios.setMinimumSize(new java.awt.Dimension(803, 70));

        jTAnuncios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jSPAnuncios.setViewportView(jTAnuncios);

        jTADescripcionAnuncio.setColumns(20);
        jTADescripcionAnuncio.setRows(5);
        jScrollPane2.setViewportView(jTADescripcionAnuncio);

        jBInsertarAnuncio.setText("Insertar Anuncio");
        jBInsertarAnuncio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBInsertarAnuncioActionPerformed(evt);
            }
        });

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
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPPrincipalLayout.createSequentialGroup()
                        .addGroup(jPPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTFNombreAnuncio, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPPrincipalLayout.createSequentialGroup()
                                .addGap(79, 79, 79)
                                .addComponent(jBInsertarAnuncio, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 384, Short.MAX_VALUE))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 202, Short.MAX_VALUE)
                .addComponent(jBBorrarSeleccion, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(61, 61, 61)
                .addGroup(jPPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPPrincipalLayout.createSequentialGroup()
                        .addComponent(jTFNombreAnuncio)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jBInsertarAnuncio, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
            .addGroup(jPPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPPrincipalLayout.createSequentialGroup()
                    .addContainerGap(48, Short.MAX_VALUE)
                    .addComponent(jSPAnuncios, javax.swing.GroupLayout.PREFERRED_SIZE, 195, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(206, 206, 206)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPPrincipal, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 449, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jBBorrarSeleccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBBorrarSeleccionActionPerformed
        int id_anuncio = (int)this.jTAnuncios.getModel().getValueAt(this.jTAnuncios.getSelectedRow(), 0);
 
        int confirmado = JOptionPane.showConfirmDialog(
                this,
                "¿Confirmas eliminar el anuncio seleccionado?", "Atención", JOptionPane.YES_NO_OPTION);
        if (JOptionPane.OK_OPTION == confirmado) {
            eliminarAnuncio(id_anuncio);
            refrescarTablaAnuncios();
        }
    }//GEN-LAST:event_jBBorrarSeleccionActionPerformed

    private void jBInsertarAnuncioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBInsertarAnuncioActionPerformed
        Anuncio anuncio = new Anuncio(0, this.jTFNombreAnuncio.getText(),
                this.jTADescripcionAnuncio.getText(), null, this.parent.getAdmin().getId_usuario());
        int confirmado = JOptionPane.showConfirmDialog(
                this,
                "¿Estás seguro de insertar el anuncio?", "Atención", JOptionPane.YES_NO_OPTION);
        if (JOptionPane.OK_OPTION == confirmado) {
            insertarAnuncio(anuncio);
            refrescarTablaAnuncios();
        }
    }//GEN-LAST:event_jBInsertarAnuncioActionPerformed

    public void refrescarTablaAnuncios() {
        llenarListAnuncios();
        this.anunciosTM.refreshTableModel(this.listaAnuncios);
    }

    public void actualizar() {
        //vistaSesionAdmin.vistaCrearEvento();
        vistaSesionAdmin.vistaAnuncios();
    }

    public void insertarAnuncio(Anuncio anuncio) {

        try {
            Integer protocolo = ADMIN_INSERTAR_ANUNCIOS;
            this.mensaje = this.parent.getGson().toJson(protocolo);
            this.parent.getSalida().writeUTF(this.mensaje);
            this.mensaje = this.parent.getGson().toJson(anuncio);
            this.parent.getSalida().writeUTF(this.mensaje);
            this.mensaje = (String) this.parent.getEntrada().readUTF();
        } catch (IOException ex) {
            Logger.getLogger(JPCrearEvento.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void eliminarAnuncio(int id_anuncio) {

        try {
            Integer protocolo = ADMIN_ELIMINAR_ANUNCIOS;
            this.mensaje = this.parent.getGson().toJson(protocolo);
            this.parent.getSalida().writeUTF(this.mensaje);
            this.mensaje = this.parent.getGson().toJson(id_anuncio);
            this.parent.getSalida().writeUTF(this.mensaje);
            this.mensaje = (String) this.parent.getEntrada().readUTF();
        } catch (IOException ex) {
            Logger.getLogger(JPCrearEvento.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void llenarListAnuncios() {
        try {
            Integer protocolo = ADMIN_VER_ANUNCIOS;
            this.mensaje = this.parent.getGson().toJson(protocolo);
            this.parent.getSalida().writeUTF(this.mensaje);
            this.mensaje = (String) this.parent.getEntrada().readUTF();
        } catch (IOException ex) {
            Logger.getLogger(JPCrearEvento.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.listaAnuncios
                = (ArrayList) this.parent.getGson().fromJson(
                        this.mensaje, new TypeToken<ArrayList<Anuncio>>() {
                        }.getType());
    }

    public void iniciarLayout() {

        this.jPPrincipal.setBackground(Color.decode("#012326"));
        this.jLMisEventos.setForeground(Color.decode("#F2F2F2"));
        this.jSeparator.setBackground(Color.decode("#d64d55"));
        this.jSeparator.setForeground(Color.decode("#d64d55"));
        this.jTFNombreAnuncio.setBorder(BorderFactory.createLineBorder(Color.decode("#ffffff")));
        this.pHNombre = new TextPrompt("Título del anuncio", this.jTFNombreAnuncio);
        this.pHNombre.changeAlpha(0.75f);
        this.pHNombre.changeStyle(Font.ITALIC);
        this.jTADescripcionAnuncio.setBorder(BorderFactory.createLineBorder(Color.decode("#ffffff")));
        this.pHDescripcion = new TextPrompt("Inserta una descripción", this.jTADescripcionAnuncio);
        this.pHDescripcion.changeAlpha(0.75f);
        this.pHDescripcion.changeStyle(Font.ITALIC);
        this.jBInsertarAnuncio.setBackground(Color.decode("#94c8d6"));
        this.jBInsertarAnuncio.setOpaque(true);
        this.jBInsertarAnuncio.setHorizontalAlignment(SwingConstants.CENTER);
        this.jBInsertarAnuncio.setBorder(BorderFactory.createLineBorder(Color.decode("#94c8d6")));
        this.jBInsertarAnuncio.setForeground(Color.decode("#f2f2f2"));
        this.jBInsertarAnuncio.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        this.jBInsertarAnuncio.setFocusPainted(false);
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
    private javax.swing.JButton jBInsertarAnuncio;
    private javax.swing.JLabel jLMisEventos;
    private javax.swing.JPanel jPPrincipal;
    private javax.swing.JScrollPane jSPAnuncios;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator;
    private javax.swing.JTextArea jTADescripcionAnuncio;
    private javax.swing.JTable jTAnuncios;
    private javax.swing.JTextField jTFNombreAnuncio;
    // End of variables declaration//GEN-END:variables
}
