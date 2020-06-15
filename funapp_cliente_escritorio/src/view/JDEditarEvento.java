/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import com.google.gson.reflect.TypeToken;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import model.Evento;
import model.Tematica;
import model.Ubicacion;
import model.UsuarioResponsable;
import util.Protocolo;
import static util.Protocolo.CREAR_EDITAR_EVENTO;
import util.TextPrompt;

/**
 *
 * @author melkart
 */
public class JDEditarEvento extends javax.swing.JDialog implements Protocolo {

    private VentanaPrincipal parent;
    private JPSesionAbierta vistaSesionAbierta;
    private TextPrompt pHNombreEvento;
    private TextPrompt pHDescripcion;
    private TextPrompt pHCalle;
    private TextPrompt pHCodigoPostal;
    private TextPrompt pHDuracion;
    private String mensaje;
    private Evento evento;

    public JDEditarEvento(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        this.parent = (VentanaPrincipal)parent;
        initComponents();
        iniciarLayout();
    }

    private void iniciarLayout() {

        this.setBackground(Color.decode("#012326"));
        this.jPPrincipal.setBackground(Color.decode("#012326"));
        this.jLCrearEvento.setForeground(Color.decode("#ffffff"));
        this.jBActualizar.setBackground(Color.decode("#94c8d6"));
        this.jBActualizar.setOpaque(true);
        this.jBActualizar.setHorizontalAlignment(SwingConstants.CENTER);
        this.jBActualizar.setBorder(BorderFactory.createLineBorder(Color.decode("#94c8d6")));
        this.jBActualizar.setForeground(Color.decode("#f2f2f2"));
        this.jBActualizar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        this.jBActualizar.setFocusPainted(false);

        this.jBCancelar.setBackground(Color.decode("#94c8d6"));
        this.jBCancelar.setOpaque(true);
        this.jBCancelar.setHorizontalAlignment(SwingConstants.CENTER);
        this.jBCancelar.setBorder(BorderFactory.createLineBorder(Color.decode("#94c8d6")));
        this.jBCancelar.setForeground(Color.decode("#f2f2f2"));
        this.jBCancelar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        this.jBCancelar.setFocusPainted(false);

        this.jSeparator.setBackground(Color.decode("#d64d55"));
        this.jSeparator.setForeground(Color.decode("#d64d55"));

        this.jLFecha.setForeground(Color.decode("#f2f2f2"));
        this.jLHora1.setForeground(Color.decode("#f2f2f2"));
        this.jLHora2.setForeground(Color.decode("#f2f2f2"));
        this.jLTematica.setForeground(Color.decode("#f2f2f2"));
        this.jCBTematica.setBorder(BorderFactory.createLineBorder(Color.decode("#ffffff")));
        this.jDCFecha.setBorder(BorderFactory.createLineBorder(Color.decode("#ffffff")));
        this.jTPHoraInicio.setBorder(BorderFactory.createLineBorder(Color.decode("#ffffff")));
        this.jTPHoraFin.setBorder(BorderFactory.createLineBorder(Color.decode("#ffffff")));
        this.jCBCodigoQR.setForeground(Color.decode("#f2f2f2"));
        this.jCBCodigoQR.setBackground(Color.decode("#012e33"));
        this.jCBCodigoQR.setContentAreaFilled(false);
        this.jCBCodigoQR.setFocusPainted(false);

        this.jLInfo1.setForeground(Color.decode("#f2f2f2"));
        this.jLInfo2.setForeground(Color.decode("#f2f2f2"));

        this.jDCFecha.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        this.jTPHoraInicio.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        this.jTPHoraFin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        this.jCBTematica.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        //Configuración de los campos de textos y agregar placehorder
        this.jTFnombreEvento.setBorder(BorderFactory.createLineBorder(Color.decode("#ffffff")));
        this.pHNombreEvento = new TextPrompt("Nombre del evento", this.jTFnombreEvento);
        this.pHNombreEvento.changeAlpha(0.75f);
        this.pHNombreEvento.changeStyle(Font.ITALIC);

        this.jSPDescripcion.setBorder(BorderFactory.createLineBorder(Color.decode("#ffffff")));
        this.jTADescripcion.setBorder(BorderFactory.createLineBorder(Color.decode("#ffffff")));
        this.pHDescripcion = new TextPrompt("Descripción", this.jTADescripcion);
        this.pHDescripcion.changeAlpha(0.75f);
        this.pHDescripcion.changeStyle(Font.ITALIC);

        this.jTFCalle.setBorder(BorderFactory.createLineBorder(Color.decode("#ffffff")));
        this.pHCalle = new TextPrompt("Calle/vía", this.jTFCalle);
        this.pHCalle.changeAlpha(0.75f);
        this.pHCalle.changeStyle(Font.ITALIC);

        this.jTFCodigoPostal.setBorder(BorderFactory.createLineBorder(Color.decode("#ffffff")));
        this.pHCodigoPostal = new TextPrompt("Código postal", this.jTFCodigoPostal);
        this.pHCodigoPostal.changeAlpha(0.75f);
        this.pHCodigoPostal.changeStyle(Font.ITALIC);
    }

    public boolean textFieldsVacios() {

        boolean[] fields = new boolean[8];
        boolean vacio = false;
        fields[0] = this.jTFnombreEvento.getText().isEmpty();
        fields[1] = this.jTADescripcion.getText().isEmpty();
        fields[2] = this.jTFCalle.getText().isEmpty();
        fields[3] = this.jTFCodigoPostal.getText().isEmpty();

        for (int i = 0; fields.length < i; i++) {
            if (fields[i]) {
                vacio = true;
            }
        }
        return vacio;
    }

    public void llenarCBTematica() {
        try {
            Integer protocolo = CREAR_EDITAR_EVENTO;
            this.mensaje = this.parent.getGson().toJson(protocolo);
            this.parent.getSalida().writeUTF(this.mensaje);
            this.mensaje = (String) this.parent.getEntrada().readUTF();
        } catch (IOException ex) {
            Logger.getLogger(JPCrearEvento.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.parent.setTematicas(
                (ArrayList) this.parent.getGson().fromJson(
                        this.mensaje, new TypeToken<ArrayList<Tematica>>() {
                        }.getType()));
        for (int i = 0; i < this.parent.getTematicas().size(); i++) {
            this.jCBTematica.addItem((Tematica) this.parent.getTematicas().get(i));
        }
    }

    public void setTextFields(){
        
        this.jTFnombreEvento.setText(evento.getNombre());
        this.jTADescripcion.setText(evento.getDescripcion());
        this.jTFCalle.setText(evento.getUbicacionesList().get(0).getCalle());
        this.jTFCodigoPostal.setText(evento.getUbicacionesList().get(0).getCodigo_postal());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        this.jDCFecha.setText(evento.getFecha_evento_LocalDate().format(formatter));
        this.jTPHoraInicio.setTime(evento.getHora_inicio());
        this.jTPHoraFin.setTime(evento.getHora_fin());        
        
    }

    public JPSesionAbierta getVistaSesionAbierta() {
        return vistaSesionAbierta;
    }

    public void setVistaSesionAbierta(JPSesionAbierta vistaSesionAbierta) {
        this.vistaSesionAbierta = vistaSesionAbierta;
    }
    
    
    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPPrincipal = new javax.swing.JPanel();
        jLCrearEvento = new javax.swing.JLabel();
        jSeparator = new javax.swing.JSeparator();
        jBActualizar = new javax.swing.JButton();
        jTFnombreEvento = new javax.swing.JTextField();
        jSPDescripcion = new javax.swing.JScrollPane();
        jTADescripcion = new javax.swing.JTextArea();
        jCBTematica = new javax.swing.JComboBox<>();
        jDCFecha = new datechooser.beans.DateChooserCombo();
        jCBCodigoQR = new javax.swing.JCheckBox();
        jTFCodigoPostal = new javax.swing.JTextField();
        jTFCalle = new javax.swing.JTextField();
        jTPHoraInicio = new com.github.lgooddatepicker.components.TimePicker();
        jTPHoraFin = new com.github.lgooddatepicker.components.TimePicker();
        jLHora1 = new javax.swing.JLabel();
        jLHora2 = new javax.swing.JLabel();
        jLFecha = new javax.swing.JLabel();
        jLTematica = new javax.swing.JLabel();
        jLInfo1 = new javax.swing.JLabel();
        jLInfo2 = new javax.swing.JLabel();
        jBCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPPrincipal.setMinimumSize(new java.awt.Dimension(726, 625));

        jLCrearEvento.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        jLCrearEvento.setText("Editar evento");

        jBActualizar.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jBActualizar.setText("Actualizar");
        jBActualizar.setPreferredSize(new java.awt.Dimension(726, 625));
        jBActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBActualizarActionPerformed(evt);
            }
        });

        jTFnombreEvento.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N

        jTADescripcion.setColumns(20);
        jTADescripcion.setRows(5);
        jSPDescripcion.setViewportView(jTADescripcion);

        jCBTematica.setFont(new java.awt.Font("Ubuntu", 1, 14)); // NOI18N
        jCBTematica.setBorder(null);
        jCBTematica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCBTematicaActionPerformed(evt);
            }
        });

        jDCFecha.setCalendarBackground(new java.awt.Color(254, 254, 254));
        jDCFecha.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jDCFecha.setWeekStyle(datechooser.view.WeekDaysStyle.SHORT);
        jDCFecha.setFieldFont(new java.awt.Font("Ubuntu", java.awt.Font.BOLD, 12));

        jCBCodigoQR.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N
        jCBCodigoQR.setText("Bonificaciones por código QR");

        jTFCodigoPostal.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N

        jTFCalle.setFont(new java.awt.Font("Ubuntu", 0, 14)); // NOI18N

        jTPHoraInicio.setBorder(null);
        jTPHoraInicio.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N

        jTPHoraFin.setBorder(null);
        jTPHoraFin.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jTPHoraFin.setOpaque(false);

        jLHora1.setText("desde");

        jLHora2.setText("hasta");

        jLFecha.setText("Fecha ");

        jLTematica.setText("Temática");

        jLInfo1.setFont(new java.awt.Font("Ubuntu", 2, 15)); // NOI18N
        jLInfo1.setText("*Recuerda que desde nuestra aplicación móvil podrás tener una experiencia más cercana con los usuarios  ");

        jLInfo2.setFont(new java.awt.Font("Ubuntu", 2, 15)); // NOI18N
        jLInfo2.setText("asistentes mediante las publicaciones de evento y las valoraciones de los usuarios.");

        jBCancelar.setFont(new java.awt.Font("Ubuntu", 1, 15)); // NOI18N
        jBCancelar.setText("Cancelar");
        jBCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBCancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPPrincipalLayout = new javax.swing.GroupLayout(jPPrincipal);
        jPPrincipal.setLayout(jPPrincipalLayout);
        jPPrincipalLayout.setHorizontalGroup(
            jPPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPPrincipalLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSPDescripcion, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparator)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPPrincipalLayout.createSequentialGroup()
                        .addComponent(jTFCalle)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTFCodigoPostal, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPPrincipalLayout.createSequentialGroup()
                        .addComponent(jLTematica)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCBTematica, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPPrincipalLayout.createSequentialGroup()
                        .addGroup(jPPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLCrearEvento)
                            .addComponent(jTFnombreEvento, javax.swing.GroupLayout.PREFERRED_SIZE, 548, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLInfo1)
                            .addComponent(jLInfo2))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPPrincipalLayout.createSequentialGroup()
                        .addGroup(jPPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPPrincipalLayout.createSequentialGroup()
                                .addComponent(jLFecha)
                                .addGap(6, 6, 6)
                                .addComponent(jDCFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLHora1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTPHoraInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jBActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPPrincipalLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLHora2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTPHoraFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 65, Short.MAX_VALUE)
                                .addComponent(jCBCodigoQR))
                            .addGroup(jPPrincipalLayout.createSequentialGroup()
                                .addGap(93, 93, 93)
                                .addComponent(jBCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPPrincipalLayout.setVerticalGroup(
            jPPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPPrincipalLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLCrearEvento)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jTFnombreEvento, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSPDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTFCodigoPostal, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTFCalle, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(jPPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCBTematica, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLTematica))
                .addGap(18, 18, 18)
                .addGroup(jPPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTPHoraInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLHora2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTPHoraFin, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jCBCodigoQR, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jDCFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLFecha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLHora1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(jPPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jBCancelar))
                .addGap(18, 18, 18)
                .addComponent(jLInfo1)
                .addGap(0, 0, 0)
                .addComponent(jLInfo2)
                .addGap(164, 164, 164))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBActualizarActionPerformed

        if (textFieldsVacios()) {
            JOptionPane.showMessageDialog(null, "Debes completar todos los campos.");
        } else if (this.jDCFecha.getSelectedDate().before(Calendar.getInstance())) {
            JOptionPane.showMessageDialog(null, "Hay que señalar una fecha posterior a la de hoy.");
        } else {

            try {

                Integer protocolo = ACTUALIZAR_EVENTO;
                this.mensaje = this.parent.getGson().toJson(protocolo);
                this.parent.getSalida().writeUTF(this.mensaje);

                Tematica tematica = (Tematica) this.jCBTematica.getSelectedItem();
                Ubicacion ubicacion = new Ubicacion(
                        0, this.jTFCalle.getText(),
                        this.jTFCodigoPostal.getText(), 0.0, 0.0);

                HashSet<Ubicacion> ubicaciones = new HashSet<Ubicacion>();
                ubicaciones.add(ubicacion);
                Evento evento = new Evento(
                        this.evento.getId_evento(), this.jTFnombreEvento.getText(), this.jTADescripcion.getText(), null, this.jDCFecha.getSelectedDate().getTime(),
                        this.jTPHoraInicio.getTime(), this.jTPHoraFin.getTime(),
                        ubicaciones, null, tematica, (UsuarioResponsable) this.parent.getUsuario(), true
                );

                this.mensaje = this.parent.getGson().toJson(evento);
                this.parent.getSalida().writeUTF(this.mensaje);
                this.mensaje = (String) this.parent.getEntrada().readUTF();
                this.parent.setEstadoSesion((Integer) this.parent.getGson().fromJson(this.mensaje, Integer.class));
                if (this.parent.getEstadoSesion() == ACTUALIZAR_EXITO) {
                    JOptionPane.showMessageDialog(null, "El evento se ha actualizado con éxito.");
                    this.parent.setEstadoSesion(SESION_ABIERTA_RESPONSABLE);
                    this.vistaSesionAbierta.getJPMisEventos().refrescarTablaTareas();
                    this.vistaSesionAbierta.getJPMisEventos().actualizar();
                    //this.vistaSesionAbierta.vistaMisEventos();
                    this.setVisible(false);
                } else if (this.parent.getEstadoSesion() == ACTUALIZAR_FALLIDO) {
                    JOptionPane.showMessageDialog(null, "No se ha podido actualizar el evento.");
                }

            } catch (IOException ex) {
            }
        }
    }//GEN-LAST:event_jBActualizarActionPerformed

    private void jCBTematicaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCBTematicaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCBTematicaActionPerformed

    private void jBCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBCancelarActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_jBCancelarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JDEditarEvento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDEditarEvento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDEditarEvento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDEditarEvento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JDEditarEvento dialog = new JDEditarEvento(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBActualizar;
    private javax.swing.JButton jBCancelar;
    private javax.swing.JCheckBox jCBCodigoQR;
    private javax.swing.JComboBox<Tematica> jCBTematica;
    private datechooser.beans.DateChooserCombo jDCFecha;
    private javax.swing.JLabel jLCrearEvento;
    private javax.swing.JLabel jLFecha;
    private javax.swing.JLabel jLHora1;
    private javax.swing.JLabel jLHora2;
    private javax.swing.JLabel jLInfo1;
    private javax.swing.JLabel jLInfo2;
    private javax.swing.JLabel jLTematica;
    private javax.swing.JPanel jPPrincipal;
    private javax.swing.JScrollPane jSPDescripcion;
    private javax.swing.JSeparator jSeparator;
    private javax.swing.JTextArea jTADescripcion;
    private javax.swing.JTextField jTFCalle;
    private javax.swing.JTextField jTFCodigoPostal;
    private javax.swing.JTextField jTFnombreEvento;
    private com.github.lgooddatepicker.components.TimePicker jTPHoraFin;
    private com.github.lgooddatepicker.components.TimePicker jTPHoraInicio;
    // End of variables declaration//GEN-END:variables
}
