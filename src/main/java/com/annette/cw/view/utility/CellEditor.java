package com.annette.cw.view.utility;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;

public class CellEditor extends AbstractCellEditor implements TableCellEditor {

    JComponent component = new JTextField();

    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected,
                                                 int rowIndex, int vColIndex) {

        ((JTextField) component).setText((String) value);

        return component;
    }

    public Object getCellEditorValue() {
        return ((JTextField) component).getText();
    }
}

