package view;

import java.awt.Color;
import java.awt.Image;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import static java.util.concurrent.TimeUnit.DAYS;
import javax.swing.ImageIcon;
import model.Evento;
import util.Protocolo;

/**
 *
 * @author melkart
 */
public class JPEventoHistorial extends javax.swing.JPanel implements Protocolo {

    private Evento evento;
    private VentanaPrincipal parent;
    private JPHistorial historial;
    private String mensaje;

    public JPEventoHistorial(Evento evento, VentanaPrincipal parent, JPHistorial historial) {
        initComponents();

        this.setBackground(Color.decode("#012e33"));
        this.jLSuscritosEdit.setBackground(Color.decode("#012e33"));

        this.evento = evento;
        this.parent = parent;
        this.historial = historial;
        this.jLNombre.setText(this.evento.getNombre());
        this.jLNombre.setForeground(Color.decode("#F2F2F2"));
        this.jLDescripcion.setText(this.evento.getDescripcion());
        this.jLDescripcion.setForeground(Color.decode("#F2F2F2"));
        this.jLFechaInicio.setForeground(Color.decode("#F2F2F2"));
        DateTimeFormatter formatterFecha = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String fecha = formatterFecha.format(this.evento.getFecha_evento_LocalDate());
        this.jLFechaIncioEdit1.setText(fecha);
        this.jLFechaIncioEdit1.setForeground(Color.decode("#F2F2F2"));
        this.jLFechaInicio1.setForeground(Color.decode("#F2F2F2"));
        DateTimeFormatter formatterHora = DateTimeFormatter.ofPattern("hh:mm");
        String inicio = formatterHora.format(this.evento.getHora_inicio());
        this.jLFechaIncioEdit.setText(inicio);
        this.jLFechaIncioEdit.setForeground(Color.decode("#F2F2F2"));
        this.jLFechaFin.setForeground(Color.decode("#F2F2F2"));
        String fin = formatterHora.format(this.evento.getHora_fin());
        this.jLFechaFinEdit.setText(fin);
        this.jLFechaFinEdit.setForeground(Color.decode("#F2F2F2"));
        this.jLNumDias.setForeground(Color.decode("#F2F2F2"));
        this.jLNumDias.setForeground(Color.decode("#F2F2F2"));
        this.jLDias.setForeground(Color.decode("#F2F2F2"));
        this.jLNumDiasEdit.setText(String.valueOf(
                Duration.between(this.evento.getFecha_evento_LocalDate().atStartOfDay(),
                        LocalDate.now().atStartOfDay()).toDays()));
        this.jLNumDiasEdit.setForeground(Color.decode("#F2F2F2"));

        this.jLSuscritos.setForeground(Color.decode("#F2F2F2"));
        this.jLSuscritosEdit.setForeground(Color.decode("#F2F2F2"));
        this.jLSuscritosEdit.setOpaque(true);
        this.jLSuscritosEdit.setText(String.valueOf(obtenerSuscritos(evento.getId_evento())));

        Image imgBorrar = new ImageIcon().getImage();
        switch (evento.getTematica().getNombre()) {
            case "Cultura local":
                imgBorrar = new ImageIcon(getClass().getResource("/imagenes/cultura.png")).getImage();
                break;
            case "Espectáculos":
                imgBorrar = new ImageIcon(getClass().getResource("/imagenes/espectaculos.png")).getImage();
                break;
            case "Gastronomía":
                imgBorrar = new ImageIcon(getClass().getResource("/imagenes/gastronomia.png")).getImage();
                break;
            case "Entretenimiento":
                imgBorrar = new ImageIcon(getClass().getResource("/imagenes/entretenimiento.png")).getImage();
                break;
            case "Deporte":
                imgBorrar = new ImageIcon(getClass().getResource("/imagenes/deporte.png")).getImage();
                break;
            case "Tecnología":
                imgBorrar = new ImageIcon(getClass().getResource("/imagenes/tecnologia.png")).getImage();
                break;
            case "Benéficos":
                imgBorrar = new ImageIcon(getClass().getResource("/imagenes/beneficos.png")).getImage();
                break;
            case "Ponencias":
                imgBorrar = new ImageIcon(getClass().getResource("/imagenes/ponencias.png")).getImage();
                break;
        }

        Image imgBorrarResc = imgBorrar.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
        this.jLImagen.setIcon(new ImageIcon(imgBorrarResc));

        if (evento.getUbicacionesList() != null && !evento.getUbicacionesList().isEmpty()) {
            this.jLCalle.setText("Sucedió en: " + this.evento.getUbicacionesList().get(0).getCalle());
        } else {
            this.jLCalle.setText("No se ha especificado ninguna calle.");
        }
        this.jLCalle.setForeground(Color.decode("#F2F2F2"));
        this.setBackground(Color.decode("#012e33"));

    }

    public int obtenerSuscritos(int id_evento) {

        int suscritos = 0;
        try {
            this.mensaje = this.parent.getGson().toJson(SUSCRITOS_EVENTO);
            this.parent.getSalida().writeUTF(this.mensaje);
            this.mensaje = this.parent.getGson().toJson(id_evento);
            this.parent.getSalida().writeUTF(this.mensaje);
            this.mensaje = (String) this.parent.getEntrada().readUTF();
            suscritos = (Integer) this.parent.getGson().fromJson(this.mensaje, Integer.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return suscritos;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLNombre = new javax.swing.JLabel();
        jLFechaIncioEdit = new javax.swing.JLabel();
        jLFechaInicio = new javax.swing.JLabel();
        jLNumDias = new javax.swing.JLabel();
        jLDescripcion = new javax.swing.JLabel();
        jLFechaFinEdit = new javax.swing.JLabel();
        jLFechaFin = new javax.swing.JLabel();
        jLNumDiasEdit = new javax.swing.JLabel();
        jLSuscritos = new javax.swing.JLabel();
        jLSuscritosEdit = new javax.swing.JLabel();
        jLDias = new javax.swing.JLabel();
        jLFechaIncioEdit1 = new javax.swing.JLabel();
        jLFechaInicio1 = new javax.swing.JLabel();
        jLImagen = new javax.swing.JLabel();
        jLCalle = new javax.swing.JLabel();

        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(254, 254, 254)));
        setMinimumSize(new java.awt.Dimension(500, 90));
        setPreferredSize(new java.awt.Dimension(682, 110));

        jLNombre.setBackground(new java.awt.Color(242, 242, 242));
        jLNombre.setFont(new java.awt.Font("DejaVu Sans Mono", 1, 18)); // NOI18N
        jLNombre.setText("Nombre");

        jLFechaIncioEdit.setBackground(new java.awt.Color(242, 242, 242));
        jLFechaIncioEdit.setFont(new java.awt.Font("DejaVu Serif", 0, 12)); // NOI18N
        jLFechaIncioEdit.setText("---");

        jLFechaInicio.setBackground(new java.awt.Color(242, 242, 242));
        jLFechaInicio.setFont(new java.awt.Font("DejaVu Sans Mono", 1, 16)); // NOI18N
        jLFechaInicio.setText("Inicio");

        jLNumDias.setBackground(new java.awt.Color(242, 242, 242));
        jLNumDias.setFont(new java.awt.Font("DejaVu Sans Mono", 1, 16)); // NOI18N
        jLNumDias.setText("Hace");

        jLDescripcion.setBackground(new java.awt.Color(242, 242, 242));
        jLDescripcion.setFont(new java.awt.Font("DejaVu Serif", 0, 14)); // NOI18N
        jLDescripcion.setText("Descripcion");
        jLDescripcion.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        jLDescripcion.setMaximumSize(new java.awt.Dimension(500, 500));
        jLDescripcion.setMinimumSize(new java.awt.Dimension(0, 0));
        jLDescripcion.setPreferredSize(new java.awt.Dimension(0, 0));

        jLFechaFinEdit.setBackground(new java.awt.Color(242, 242, 242));
        jLFechaFinEdit.setFont(new java.awt.Font("DejaVu Serif", 0, 12)); // NOI18N
        jLFechaFinEdit.setText("---");

        jLFechaFin.setBackground(new java.awt.Color(242, 242, 242));
        jLFechaFin.setFont(new java.awt.Font("DejaVu Sans Mono", 1, 16)); // NOI18N
        jLFechaFin.setText("Fin");

        jLNumDiasEdit.setBackground(new java.awt.Color(242, 242, 242));
        jLNumDiasEdit.setFont(new java.awt.Font("DejaVu Sans Mono", 0, 12)); // NOI18N
        jLNumDiasEdit.setText("---");

        jLSuscritos.setBackground(new java.awt.Color(242, 242, 242));
        jLSuscritos.setFont(new java.awt.Font("DejaVu Sans Mono", 1, 16)); // NOI18N
        jLSuscritos.setText("Suscritos");

        jLSuscritosEdit.setFont(new java.awt.Font("DejaVu Sans Mono", 0, 12)); // NOI18N

        jLDias.setBackground(new java.awt.Color(242, 242, 242));
        jLDias.setFont(new java.awt.Font("DejaVu Sans Mono", 0, 12)); // NOI18N
        jLDias.setText("días");

        jLFechaIncioEdit1.setBackground(new java.awt.Color(242, 242, 242));
        jLFechaIncioEdit1.setFont(new java.awt.Font("DejaVu Serif", 0, 12)); // NOI18N
        jLFechaIncioEdit1.setText("---");

        jLFechaInicio1.setBackground(new java.awt.Color(242, 242, 242));
        jLFechaInicio1.setFont(new java.awt.Font("DejaVu Sans Mono", 1, 16)); // NOI18N
        jLFechaInicio1.setText("Fecha");

        jLImagen.setText("jLabel1");

        jLCalle.setText("Sucedió en la calle:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLCalle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLFechaInicio1)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLNumDiasEdit)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLDias))
                                    .addComponent(jLFechaIncioEdit1, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jLNumDias))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(12, 12, 12)
                                        .addComponent(jLFechaIncioEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLFechaInicio))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 58, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLFechaFin)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(12, 12, 12)
                                        .addComponent(jLFechaFinEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLSuscritosEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLSuscritos)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLImagen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLFechaInicio1)
                                    .addComponent(jLFechaInicio))
                                .addGap(2, 2, 2)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLFechaIncioEdit1)
                                    .addComponent(jLFechaIncioEdit)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(3, 3, 3)
                                .addComponent(jLFechaFin)
                                .addGap(0, 0, 0)
                                .addComponent(jLFechaFinEdit)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jLNumDias)
                                .addGap(0, 0, 0)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLNumDiasEdit)
                                    .addComponent(jLDias)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLSuscritosEdit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLSuscritos, javax.swing.GroupLayout.Alignment.TRAILING))))))
                .addGap(0, 0, 0)
                .addComponent(jLCalle))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLCalle;
    private javax.swing.JLabel jLDescripcion;
    private javax.swing.JLabel jLDias;
    private javax.swing.JLabel jLFechaFin;
    private javax.swing.JLabel jLFechaFinEdit;
    private javax.swing.JLabel jLFechaIncioEdit;
    private javax.swing.JLabel jLFechaIncioEdit1;
    private javax.swing.JLabel jLFechaInicio;
    private javax.swing.JLabel jLFechaInicio1;
    private javax.swing.JLabel jLImagen;
    private javax.swing.JLabel jLNombre;
    private javax.swing.JLabel jLNumDias;
    private javax.swing.JLabel jLNumDiasEdit;
    private javax.swing.JLabel jLSuscritos;
    private javax.swing.JLabel jLSuscritosEdit;
    // End of variables declaration//GEN-END:variables
}
