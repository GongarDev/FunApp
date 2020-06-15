package view;

import java.awt.Color;

/**
 *
 * @author melkart
 */
public class JPSesionAbierta extends javax.swing.JPanel {

    private VentanaPrincipal parent;
    private JPMisEventos jPMisEventos;
    private JPCrearEvento jPCrearEvento;
    private JPHistorial jPHistorial;
    private JPCuenta jPCuenta;

    public JPSesionAbierta(VentanaPrincipal parent) {
        initComponents();
        this.parent = parent;
        configurarJPanels();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPMenu = new view.JPMenu();
        jPBarraVentana = new view.JPBarraVentana();
        jPContenido = new javax.swing.JPanel();

        setMinimumSize(new java.awt.Dimension(920, 600));
        setPreferredSize(new java.awt.Dimension(920, 600));

        jPBarraVentana.setMaximumSize(new java.awt.Dimension(33291, 25));

        javax.swing.GroupLayout jPContenidoLayout = new javax.swing.GroupLayout(jPContenido);
        jPContenido.setLayout(jPContenidoLayout);
        jPContenidoLayout.setHorizontalGroup(
            jPContenidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPContenidoLayout.setVerticalGroup(
            jPContenidoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPBarraVentana, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jPContenido, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPBarraVentana, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPMenu, javax.swing.GroupLayout.DEFAULT_SIZE, 575, Short.MAX_VALUE)
                    .addComponent(jPContenido, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    public void setParent(VentanaPrincipal parent) {
        this.parent = parent;
    }

    public void configurarJPanels() {

        this.jPContenido.setBackground(Color.GRAY);

        this.jPMisEventos = new JPMisEventos(this.parent, this);
        this.jPMisEventos.setParent(this.parent);
        this.jPMisEventos.setJPSesionAbierta(this);
        this.jPMisEventos.setVisible(true);
        this.jPMisEventos.setSize(726, 475);
        this.jPContenido.add(this.jPMisEventos);

        this.jPHistorial = new JPHistorial(this.parent, this);
        this.jPHistorial.setParent(this.parent);
        this.jPHistorial.setJPSesionAbierta(this);
        this.jPHistorial.setVisible(true);
        this.jPHistorial.setSize(726, 475);
        this.jPContenido.add(this.jPHistorial);

        this.jPCrearEvento = new JPCrearEvento();
        this.jPCrearEvento.setVisible(false);
        this.jPCrearEvento.setSize(726, 475);
        this.jPContenido.add(this.jPCrearEvento);
        this.jPCrearEvento.setParent(this.parent);
        this.jPCrearEvento.setJPSesionAbierta(this);

        this.jPCuenta = new JPCuenta(this.parent, this, this.jPMisEventos);
        this.jPCuenta.setParent(this.parent);
        this.jPCuenta.setJPSesionAbierta(this);
        this.jPCuenta.setVisible(true);
        this.jPCuenta.setSize(726, 475);
        this.jPContenido.add(this.jPCuenta);

        //primera lectura del flujo del socket.
        this.jPCrearEvento.llenarCBTematica();

        this.jPContenido.revalidate();
        this.jPContenido.repaint();

        this.jPMenu.setParent(this);
        this.jPBarraVentana.setParent(this.parent);

    }

    public VentanaPrincipal getJFrame() {
        return this.parent;
    }

    public JPMisEventos getJPMisEventos() {
        return this.jPMisEventos;
    }

    public void vistaCrearEvento() {
        this.jPMisEventos.setVisible(false);
        this.jPHistorial.setVisible(false);
        this.jPCuenta.setVisible(false);
        this.jPCrearEvento.setVisible(true);
    }

    public void vistaMisEventos() {
        this.jPCrearEvento.setVisible(false);
        this.jPHistorial.setVisible(false);
        this.jPCuenta.setVisible(false);
        this.jPMisEventos.setVisible(true);
        this.jPMisEventos.refrescarTablaTareas();
    }

    public void vistaHistorial() {
        this.jPCrearEvento.setVisible(false);
        this.jPMisEventos.setVisible(false);
        this.jPCuenta.setVisible(false);
        this.jPHistorial.setVisible(true);
        this.jPHistorial.refrescarTablaTareas();
    }

    public void vistaCuenta() {
        this.jPCrearEvento.setVisible(false);
        this.jPMisEventos.setVisible(false);
        this.jPHistorial.setVisible(false);
        this.jPCuenta.setVisible(true);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private view.JPBarraVentana jPBarraVentana;
    private javax.swing.JPanel jPContenido;
    private view.JPMenu jPMenu;
    // End of variables declaration//GEN-END:variables
}
