package com.annette.cw.view.decision;

import com.annette.cw.controller.Controller;
import com.annette.cw.entity.DecisionRecord;
import com.annette.cw.service.Provider;
import com.annette.cw.utility.Result;
import com.annette.cw.view.ExceptionWindow;
import com.annette.cw.view.ResultWindow;
import com.annette.cw.view.utility.CellEditor;
import com.annette.cw.view.utility.TableModel;
import com.annette.cw.view.utility.WindowFunction;
import com.annette.cw.view.utility.WindowUtil;

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
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 150, 90));
    }

    public static JPanel getPanel() {
        return panel;
    }

    private static void createTable() {
        panel.removeAll();
        table = new JTable(new TableModel());
        columnModel = table.getColumnModel();
        Enumeration<TableColumn> e = columnModel.getColumns();
        int pos = 0;
        while (e.hasMoreElements()) {
            TableColumn column = e.nextElement();
            column.setCellEditor(new CellEditor());
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

    public static void createUI() {
        createTable();
        WindowUtil.addSmallWindowButton("В главное меню",e->goToUserMenu(),getPanel());
        WindowUtil.addSmallWindowButton("Сохранить", e -> saveAll(), getPanel());
    }

    private static List<List<Double>> readInfoFromTable() {
        List<List<Double>> dataFromTable = new ArrayList<>();
        try {
            int index = 0;
            for (int i = 1; i < table.getColumnCount(); i++) {
                System.out.println(table.getColumnCount());
                List<Double> columnData = new ArrayList<>();
                int columnIndex = 0;
                for (int k = 0; k < table.getRowCount(); k++) {
                    System.out.println(table.getModel().getValueAt(k, i));
                    columnData.add(columnIndex, Double.valueOf(table.getModel().getValueAt(k, i).toString()));
                    columnIndex++;
                }
                dataFromTable.add(index, columnData);
                index++;
            }
        } catch (NullPointerException e) {
            ExceptionWindow.makeLabel("Все поля должны быть заполнены");
        } catch (NumberFormatException e) {
            ExceptionWindow.makeLabel("Проверьте формат данных в ячейках");
        }
        System.out.println(dataFromTable);
        return dataFromTable;
    }

    private static void saveAll() {
        List<List<Double>> data = readInfoFromTable();
        Provider.getInstance().makeDecisionById(Controller.getInstance().getChangeableDecision().getId(), data,
                (Result<DecisionRecord> res) -> {
                    if (res.getCode() == 400) ExceptionWindow.makeLabel("Ошибка ввода данных");
                    if (res.getCode() == 200) { new ResultWindow().createResult(res); }
                });
    }
    public static void goToUserMenu(){
        WindowFunction.returnIntoUserWindow(panel);
    }

}
