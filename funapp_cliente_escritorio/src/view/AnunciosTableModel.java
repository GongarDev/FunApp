
package view;

import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.Anuncio;

/**
 *
 * @author melkart
 */
public class AnunciosTableModel extends AbstractTableModel {

    private final String[] _columnNames = {"id_anuncio", "nombre", "descripcion", "Fecha", "id_usuario"};

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

    public void refreshTableModel(List<Anuncio> listaAnuncios) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        List<Anuncio> listaAnunciosTabla = listaAnuncios;

        if (!listaAnunciosTabla.isEmpty()) {
            int numCol = _columnNames.length + 1;
            int numFilas = listaAnuncios.size();
            _data = new Object[numFilas][numCol];
            for (int f = 0; f < numFilas; f++) {
                String fecha = listaAnunciosTabla.get(f).getFecha_publicacion_LocalDate().format(formatter);

                _data[f][0] = listaAnunciosTabla.get(f).getId_anuncio();
                _data[f][1] = listaAnunciosTabla.get(f).getNombre();
                _data[f][2] = listaAnunciosTabla.get(f).getMensaje();
                _data[f][3] = fecha;
                _data[f][4] = listaAnunciosTabla.get(f).getId_usuario();

            }
        } else {
            _data = new Object[0][0];
        }
        // - Se notifica a Swing que el Table Model ha cambiado.
        fireTableDataChanged();
    }
}