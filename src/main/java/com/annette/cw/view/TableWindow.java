package com.annette.cw.view;

import com.annette.cw.view.utility.TableModel;
import com.annette.cw.view.utility.WindowFunction;

import javax.swing.*;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class TableWindow {
    private static JPanel panel = new JPanel();
    private static TableColumnModel columnModel;
    private static JTable table;

    static {
        panel.setBackground(new Color(120, 110, 255));
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 40));
    }

    public static JPanel getPanel() {
        return panel;
    }

    public static void createTable() {
        table = new JTable(new TableModel());
        columnModel = table.getColumnModel();
        Enumeration<TableColumn> e = columnModel.getColumns();
        int pos = 0;
        while (e.hasMoreElements()) {
            TableColumn column = e.nextElement();
            if (pos == 0) {
                column.setPreferredWidth(200);
            } else column.setPreferredWidth(30);
            table.setRowHeight(table.getRowHeight() + 5);
            pos++;
        }
        table.getColumnModel().setColumnSelectionAllowed(true);
        panel.setBackground(new Color(120, 110, 255));
        table.getTableHeader().setBackground(new Color(200, 50, 255));
        table.setBackground(new Color(130, 240, 210));
        JPanel jpanel = new JPanel(new BorderLayout());
        jpanel.add(table.getTableHeader(), BorderLayout.PAGE_START);
        jpanel.add(table, BorderLayout.CENTER);
        panel.add(jpanel);
        WindowFunction.util(panel);
    }

    public void createUI() {
        createTable();
       // WindowUtil.addSmallWindowButton("Назад",);
        // Window

    }

    private List<List<Double>> readInfoFromTable() {
        List<List<Double>> dataFromTable = new ArrayList<>();
        try {
            String str;
            Object ob;
            int index = 0;
            List<Double> columnData = new ArrayList<>();
            for (int i = 1; i < table.getColumnCount(); i++) {
                int columnIndex = 0;
                for (int k = 1; k < table.getRowCount(); k++) {
                    ob = table.getModel().getValueAt(k, i);
                    columnData.add(columnIndex, Double.valueOf(ob.toString()));
                    columnIndex++;
                }
                dataFromTable.add(index, columnData);
                index++;
            }

        } catch (NullPointerException e) {
            ExceptionWindow.makeLabel("Все поля должны быть заполнены");
        } catch (ClassCastException e) {
            ExceptionWindow.makeLabel("Проверьте формат данных в ячейках");
        }
        return dataFromTable;
    }

    private void saveAll() {

    }

}
