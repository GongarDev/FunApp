package view;

import com.google.gson.reflect.TypeToken;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Evento;
import model.UsuarioResponsable;
import util.FechaInicioCompare;
import util.Protocolo;

/**
 *
 * @author melkart
 */
public class JPMisEventos extends javax.swing.JPanel implements Protocolo {

    private VentanaPrincipal parent;
    private JPSesionAbierta vistaSesionAbierta;
    private List<Evento> listaEventos;
    private UsuarioResponsable usuario;
    private String mensaje;

    public JPMisEventos(VentanaPrincipal parent, JPSesionAbierta vistaSesionAbierta) {
        initComponents();
        this.parent = parent;
        this.vistaSesionAbierta = vistaSesionAbierta;
        this.jPPrincipal.setBackground(Color.decode("#012326"));
        this.jLMisEventos.setForeground(Color.decode("#F2F2F2"));
        this.jPTabla.setBackground(Color.decode("#012326"));
        this.jLSinEventos.setForeground(Color.decode("#F2F2F2"));
        this.jLSinEventos.setVisible(false);
        this.jSeparator.setBackground(Color.decode("#d64d55"));
        this.jSeparator.setForeground(Color.decode("#d64d55"));
        refrescarTablaTareas();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPPrincipal = new javax.swing.JPanel();
        jLMisEventos = new javax.swing.JLabel();
        jSeparator = new javax.swing.JSeparator();
        jLSinEventos = new javax.swing.JLabel();
        jSPTareas = new javax.swing.JScrollPane();
        jPTabla = new javax.swing.JPanel();

        jPPrincipal.setMinimumSize(new java.awt.Dimension(0, 0));
        jPPrincipal.setPreferredSize(new java.awt.Dimension(726, 625));

        jLMisEventos.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        jLMisEventos.setText("Mis eventos");

        jLSinEventos.setFont(new java.awt.Font("DejaVu Sans Mono", 0, 24)); // NOI18N
        jLSinEventos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLSinEventos.setText("No hay eventos creados para futuras fechas.");
        jLSinEventos.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        jSPTareas.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jSPTareas.setMinimumSize(new java.awt.Dimension(803, 70));

        jPTabla.setBackground(new java.awt.Color(242, 242, 242));
        jPTabla.setMinimumSize(new java.awt.Dimension(0, 0));
        jPTabla.setPreferredSize(new java.awt.Dimension(726, 525));
        jPTabla.setLayout(new java.awt.GridBagLayout());
        jSPTareas.setViewportView(jPTabla);

        javax.swing.GroupLayout jPPrincipalLayout = new javax.swing.GroupLayout(jPPrincipal);
        jPPrincipal.setLayout(jPPrincipalLayout);
        jPPrincipalLayout.setHorizontalGroup(
            jPPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPPrincipalLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator)
                    .addComponent(jLSinEventos, javax.swing.GroupLayout.DEFAULT_SIZE, 702, Short.MAX_VALUE)
                    .addGroup(jPPrincipalLayout.createSequentialGroup()
                        .addComponent(jLMisEventos)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPPrincipalLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jSPTareas, javax.swing.GroupLayout.PREFERRED_SIZE, 702, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPPrincipalLayout.setVerticalGroup(
            jPPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPPrincipalLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLMisEventos)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLSinEventos)
                .addContainerGap(356, Short.MAX_VALUE))
            .addGroup(jPPrincipalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPPrincipalLayout.createSequentialGroup()
                    .addGap(111, 111, 111)
                    .addComponent(jSPTareas, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(13, Short.MAX_VALUE)))
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

    public void refrescarTablaTareas() {
        this.jPTabla.removeAll();
        this.jPTabla.setPreferredSize(new Dimension(0, 0));

        this.usuario = this.parent.getUsuario();
        llenarListEventos();
        this.jLSinEventos.setVisible(false);
        this.jSPTareas.setVisible(true);
        this.jPTabla.setVisible(true);
        if (this.listaEventos.size() > 0) {
            FechaInicioCompare fechaInicioCompare = new FechaInicioCompare();
            Collections.sort(this.listaEventos, fechaInicioCompare);
            this.jPTabla.setPreferredSize(new Dimension(660, 100 * this.listaEventos.size()));
            GridBagConstraints c = new GridBagConstraints();
            c.fill = GridBagConstraints.BOTH;
            c.gridy = 0;
            c.gridwidth = 0;
            c.weightx = 1;

            int count = 1;
            for (Evento e : this.listaEventos) {
                this.jPTabla.add(new JPEvento(e, this.parent, this), c);
                c.gridy = count;
                count++;
            }
        } else {
            this.jPTabla.setVisible(false);
            this.jSPTareas.setVisible(false);
            this.jLSinEventos.setVisible(true);
        }
    }

    public void actualizar() {
        vistaSesionAbierta.vistaCrearEvento();
        vistaSesionAbierta.vistaMisEventos();
    }

    public void llenarListEventos() {
        try {
            Integer protocolo = VER_EVENTOS_RESPONSABLE;
            this.mensaje = this.parent.getGson().toJson(protocolo);
            this.parent.getSalida().writeUTF(this.mensaje);
            this.mensaje = this.parent.getGson().toJson(this.usuario.getId_usuario());
            this.parent.getSalida().writeUTF(this.mensaje);
            this.mensaje = (String) this.parent.getEntrada().readUTF();
        } catch (IOException ex) {
            Logger.getLogger(JPCrearEvento.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.listaEventos
                = (ArrayList) this.parent.getGson().fromJson(
                        this.mensaje, new TypeToken<ArrayList<Evento>>() {
                        }.getType());
    }

    public void setParent(VentanaPrincipal parent) {
        this.parent = parent;
    }

    public void setJPSesionAbierta(JPSesionAbierta jPSesionAbierta) {
        this.vistaSesionAbierta = jPSesionAbierta;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLMisEventos;
    private javax.swing.JLabel jLSinEventos;
    private javax.swing.JPanel jPPrincipal;
    private javax.swing.JPanel jPTabla;
    private javax.swing.JScrollPane jSPTareas;
    private javax.swing.JSeparator jSeparator;
    // End of variables declaration//GEN-END:variables
}
