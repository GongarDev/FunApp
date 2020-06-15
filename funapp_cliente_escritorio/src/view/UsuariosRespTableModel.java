
package view;

import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import model.UsuarioResponsable;

/**
 *
 * @author melkart
 */
public class UsuariosRespTableModel extends AbstractTableModel {

    private final String[] _columnNames = {"id_usuario", "seudonimo", "email",
        "fecha_nacimiento", "fecha_ingreso", "dni", "nombre", "apellido", "telefono"};

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

    public void refreshTableModel(List<UsuarioResponsable> listaUsuarios) {

        DateTimeFormatter formatterFecha = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        List<UsuarioResponsable> listaUsuariosTabla = listaUsuarios;

        if (!listaUsuariosTabla.isEmpty()) {
            int numCol = _columnNames.length + 1;
            int numFilas = listaUsuarios.size();
            _data = new Object[numFilas][numCol];
            for (int f = 0; f < numFilas; f++) {
                String fecha_nacimiento = listaUsuariosTabla.get(f).getFecha_nac_LocalDate().format(formatterFecha);
                String fecha_ingreso = listaUsuariosTabla.get(f).getFecha_ingreso_LocalDate().format(formatterFecha);

                _data[f][0] = listaUsuariosTabla.get(f).getId_usuario();
                _data[f][1] = listaUsuariosTabla.get(f).getSeudonimo();
                _data[f][2] = listaUsuariosTabla.get(f).getEmail();
                _data[f][3] = fecha_nacimiento;
                _data[f][4] = fecha_ingreso;
                _data[f][5] = listaUsuariosTabla.get(f).getSeudonimo();
                _data[f][6] = listaUsuariosTabla.get(f).getEmail();
                _data[f][7] = fecha_nacimiento;
                _data[f][8] = fecha_ingreso;                
              
            }
        } else {
            _data = new Object[0][0];
        }
        // - Se notifica a Swing que el Table Model ha cambiado.
        fireTableDataChanged();
    }
}