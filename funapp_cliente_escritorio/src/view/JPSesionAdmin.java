package view;

import java.awt.Color;

/**
 *
 * @author melkart
 */
public class JPSesionAdmin extends javax.swing.JPanel {

    private VentanaPrincipal parent;
    private JPAnuncios jPAnuncios;
    private JPIncidencias JPIncidencias;
    private JPEventos jPEventos;
    private JPUsuariosEstandar jPUsuariosEstandar;
    private JPUsuariosResp jPUsuarioResp;

    public JPSesionAdmin(VentanaPrincipal parent) {
        initComponents();
        this.parent = parent;
        configurarJPanels();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPBarraVentana = new view.JPBarraVentana();
        jPContenido = new javax.swing.JPanel();
        jPMenuAdmin1 = new view.JPMenuAdmin();

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
            .addGap(0, 575, Short.MAX_VALUE)
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
                        .addComponent(jPMenuAdmin1, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jPContenido, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPBarraVentana, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPContenido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, 0)
                        .addComponent(jPMenuAdmin1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
    }// </editor-fold>//GEN-END:initComponents

    public void setParent(VentanaPrincipal parent) {
        this.parent = parent;
    }

    public void configurarJPanels() {

        //this.jPContenido.setBackground(Color.GRAY);

        this.jPEventos = new JPEventos(this.parent, this);
        this.jPEventos.setParent(this.parent);
        this.jPEventos.setJPSesionAdmin(this);
        this.jPEventos.setVisible(false);
        this.jPEventos.setSize(726, 475);
        this.jPContenido.add(this.jPEventos);

        this.jPAnuncios = new JPAnuncios(this.parent, this);
        this.jPAnuncios.setParent(this.parent);
        this.jPAnuncios.setJPSesionAdmin(this);
        this.jPAnuncios.setVisible(true);
        this.jPAnuncios.setSize(726, 475);
        this.jPContenido.add(this.jPAnuncios);

        this.jPUsuarioResp = new JPUsuariosResp(this.parent, this);
        this.jPUsuarioResp.setParent(this.parent);
        this.jPUsuarioResp.setJPSesionAdmin(this);
        this.jPUsuarioResp.setVisible(false);
        this.jPUsuarioResp.setSize(726, 475);
        this.jPContenido.add(this.jPUsuarioResp);

        this.jPUsuariosEstandar = new JPUsuariosEstandar(this.parent, this);
        this.jPUsuariosEstandar.setParent(this.parent);
        this.jPUsuariosEstandar.setJPSesionAdmin(this);
        this.jPUsuariosEstandar.setVisible(false);
        this.jPUsuariosEstandar.setSize(726, 475);
        this.jPContenido.add(this.jPUsuariosEstandar);

        this.JPIncidencias = new JPIncidencias(this.parent, this);
        this.JPIncidencias.setParent(this.parent);
        this.JPIncidencias.setJPSesionAdmin(this);
        this.JPIncidencias.setVisible(true);
        this.JPIncidencias.setSize(726, 475);
        this.jPContenido.add(this.JPIncidencias);

        this.jPContenido.revalidate();
        this.jPContenido.repaint();

        this.jPMenuAdmin1.setParent(this);
        this.jPBarraVentana.setParent(this.parent);

    }

    public VentanaPrincipal getJFrame() {
        return this.parent;
    }

    public void vistaAnuncios() {
        this.JPIncidencias.setVisible(false);
        this.jPEventos.setVisible(false);
        this.jPUsuarioResp.setVisible(false);
        this.jPUsuariosEstandar.setVisible(false);
        this.jPAnuncios.setVisible(true);
    }

    public void vistaIncidencias() {
        this.jPAnuncios.setVisible(false);
        this.jPUsuariosEstandar.setVisible(false);
        this.jPUsuarioResp.setVisible(false);
        this.jPEventos.setVisible(false);
        this.JPIncidencias.setVisible(true);
    }

    public void vistaEventos() {
        this.jPAnuncios.setVisible(false);
        this.jPUsuariosEstandar.setVisible(false);
        this.jPUsuarioResp.setVisible(false);
        this.JPIncidencias.setVisible(false);
        this.jPEventos.setVisible(true);
    }

    public void vistaUsuariosEstandar() {
        this.jPAnuncios.setVisible(false);
        this.JPIncidencias.setVisible(false);
        this.jPUsuarioResp.setVisible(false);
        this.jPEventos.setVisible(false);
        this.jPUsuariosEstandar.setVisible(true);
    }

    public void vistaUsuariosResponsable() {
        this.jPAnuncios.setVisible(false);
        this.JPIncidencias.setVisible(false);
        this.jPEventos.setVisible(false);
        this.jPUsuariosEstandar.setVisible(false);
        this.jPUsuarioResp.setVisible(true);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private view.JPBarraVentana jPBarraVentana;
    private javax.swing.JPanel jPContenido;
    private view.JPMenuAdmin jPMenuAdmin1;
    // End of variables declaration//GEN-END:variables
}
