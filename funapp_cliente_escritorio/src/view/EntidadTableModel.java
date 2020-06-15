
package view;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.Entidad;

/**
 *
 * @author melkart
 */
public class EntidadTableModel extends AbstractTableModel {

    private final String[] _columnNames = {"id_entidad", "id_usuario", "nombre", "nif",
        "calle", "provincia", "localidad", "codigo_postal", "telefono"};

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

    public void refreshTableModel(List<Entidad> listaEntidades) {

        List<Entidad> listaEntidadesTabla = listaEntidades;

        if (!listaEntidadesTabla.isEmpty()) {
            int numCol = _columnNames.length + 1;
            int numFilas = listaEntidades.size();
            _data = new Object[numFilas][numCol];
            for (int f = 0; f < numFilas; f++) {

                _data[f][0] = listaEntidadesTabla.get(f).getId_entidad();                
                _data[f][1] = listaEntidadesTabla.get(f).getId_usuario();
                _data[f][2] = listaEntidadesTabla.get(f).getNombre();
                _data[f][3] = listaEntidadesTabla.get(f).getNif();
                _data[f][4] = listaEntidadesTabla.get(f).getCalle();
                _data[f][5] = listaEntidadesTabla.get(f).getProvincia();
                _data[f][6] = listaEntidadesTabla.get(f).getLocalidad();
                _data[f][7] = listaEntidadesTabla.get(f).getCodigo_postal();
                _data[f][8] = listaEntidadesTabla.get(f).getTelefono();               
              
            }
        } else {
            _data = new Object[0][0];
        }
        // - Se notifica a Swing que el Table Model ha cambiado.
        fireTableDataChanged();
    }
}