package view;

import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.Evento;

/**
 *
 * @author melkart
 */
public class EventosTableModel extends AbstractTableModel {

    /* private final String[] _columnNames = {"id_evento", "nombre", "descripcion",
        "calle", "codigo_postal", "fecha_publicacion",
        "fecha_evento", "hora_inicio", "hora_fin", "tematica", "activo", "id_usuario"};*/
    private final String[] _columnNames = {"id_evento", "nombre", "descripcion", "calle"};

    private Object[][] _data = {};

    @Override
    public int getRowCount() {
        return _data.length;
    }

    @Override
    public int getColumnCount() {
        return _columnNames.length;
    }

    @Override
    public Object getValueAt(int row, int col) {
        return _data[row][col];
    }

    @Override
    public String getColumnName(int col) {
        if ((col >= 0) && (col < _columnNames.length)) {
            return _columnNames[col];
        } else {
            return "";
        }
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    @Override
    public Class getColumnClass(int column) {
        return getValueAt(0, column).getClass();
    }

    public void refreshTableModel(List<Evento> listaEventos) {

        DateTimeFormatter formatterFecha = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter formatterHora = DateTimeFormatter.ofPattern("hh:mm");

        List<Evento> listaEventosTabla = listaEventos;

        if (!listaEventosTabla.isEmpty()) {
            int numCol = _columnNames.length + 1;
            int numFilas = listaEventos.size();
            _data = new Object[numFilas][numCol];
            for (int f = 0; f < numFilas; f++) {
                String fecha_publicacion = listaEventosTabla.get(f).getFecha_publicacion_LocalDate().format(formatterFecha);
                //String fecha_evento = listaEventosTabla.get(f).getFecha_evento_LocalDate().format(formatterFecha);

                _data[f][0] = listaEventosTabla.get(f).getId_evento();
                _data[f][1] = listaEventosTabla.get(f).getNombre();
                _data[f][2] = listaEventosTabla.get(f).getDescripcion();
               /* _data[f][3] = listaEventosTabla.get(f).getUbicacionesList().get(0).getCalle();
                 _data[f][4] = listaEventosTabla.get(f).getUbicacionesList().get(0).getCodigo_postal();
                _data[f][5] = fecha_publicacion;
                _data[f][6] = fecha_evento;
                _data[f][7] = listaEventosTabla.get(f).getHora_inicio().format(formatterHora);
                _data[f][8] = listaEventosTabla.get(f).getHora_fin().format(formatterHora);
                _data[f][9] = listaEventosTabla.get(f).getTematica().getNombre();
                _data[f][10] = listaEventosTabla.get(f).isActivo();
                _data[f][11] = listaEventosTabla.get(f).getUsuario().getId_usuario();      */
            }
        } else {
            _data = new Object[0][0];
        }
        // - Se notifica a Swing que el Table Model ha cambiado.
        fireTableDataChanged();
    }
}
