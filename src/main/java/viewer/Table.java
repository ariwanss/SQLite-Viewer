package viewer;

import javax.swing.table.AbstractTableModel;

public class Table extends AbstractTableModel {
    String[] columns = new String[0];
    Object[][] data = new Object[0][0];

    public void setColumns(String[] columns) {
        this.columns = columns;
    }

    public void setData(Object[][] data) {
        this.data = data;
    }

    @Override
    public int getRowCount() {
        return data.length;
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public Object getValueAt(int row, int column) {
        return data[row][column];
    }
}
