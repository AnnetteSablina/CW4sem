package com.annette.cw.view.utility;

import javax.swing.table.AbstractTableModel;

public class TableModel extends AbstractTableModel {
    private static String[][] data = TableDataCreator.createData();
    private static String[] columnNames = TableDataCreator.createColumns();

    @Override
    public int getRowCount() {
        return data.length;
    }

    @Override
    public int getColumnCount() {
        return data[0].length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data[rowIndex][columnIndex];
    }

    public boolean isCellEditable(int row, int col) {
        return col != 0;
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

}
