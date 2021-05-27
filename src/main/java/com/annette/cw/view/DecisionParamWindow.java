package com.annette.cw.view;

import com.annette.cw.controller.Controller;
import com.annette.cw.entity.Decision;
import com.annette.cw.utility.Result;
import com.annette.cw.view.utility.WindowFunction;
import com.annette.cw.view.utility.WindowUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class DecisionParamWindow extends JFrame {
    private static JPanel panel = new JPanel();
    private static ArrayList<JTextField> fields = new ArrayList<>();
    private static ArrayList<JComboBox<Integer>> states = new ArrayList<>();
    private static final Integer[] values = {1,2,3,4};
    private static Integer quantity;

    static {
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 90, 50));
        panel.setBackground(new Color(120, 110, 255));
    }

    public static JPanel getPanel() {
        return panel;
    }

    public static Integer getQuantity() {
        return quantity;
    }

    public static ArrayList<JTextField> getFields() {
        return fields;
    }

    public static ArrayList<JComboBox<Integer>> getStates() {
        return states;
    }

    public DecisionParamWindow() {
        panel.removeAll();
        this.setTitle("Выбор решения");
        this.setResizable(false);
        this.setSize(600, 400);
        drawUI();
        //changeChooseUserUI();
        this.add(panel);
        this.setVisible(true);
        ExceptionWindow.makeLabel("При изменении результаты в БД будут удалены");
    }

    private static void addTextField(String description) {
        JPanel compPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 0));
        compPanel.setBackground(new Color(120, 110, 255));
        WindowUtil.addLabel(description, compPanel);
        JTextField field = new JTextField();
        field.setText(Controller.getInstance().getChangeableDecision().getName());
        field.setPreferredSize(new Dimension(200, 30));
        field.setBackground(new Color(130, 240, 210));
        fields.add(field);
        compPanel.add(field);
        DecisionParamWindow.panel.add(compPanel);
    }
    private static void addComboBox(String description) {
        JPanel compPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 0));
        compPanel.setBackground(new Color(120, 110, 255));
        WindowUtil.addLabel(description, compPanel);
        JComboBox<Integer> state = new JComboBox<>(values);
        state.setPreferredSize(new Dimension(200, 30));
        state.setBackground(new Color(130, 240, 210));
        state.setEditable(false);
        compPanel.add(state);
        states.add(state);
        DecisionParamWindow.panel.add(compPanel);
    }
    private static void addButton(String caption, ActionListener actionListener) {
        JButton button = new JButton(caption);
        button.setPreferredSize(new Dimension(200, 30));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(actionListener);
        button.setBackground(new Color(130, 240, 210));
        DecisionParamWindow.panel.add(button);
    }
    private void drawUI() {
        addTextField("Название решения");
        addComboBox("Количество состояний природы");
        addComboBox("Количество стратегий");
        addButton("Назад",e->comeBack());
        addButton("Сохранить",e->saveAll());

    }
    private void saveAll() {
        quantity = (Integer) states.get(1).getSelectedItem();
        new EnterStrategyNameWindow();
    }
    private void comeBack() {
        this.dispose();
        WindowFunction.returnIntoUserWindow(panel);
    }
    public static void err(Result<Decision> res) {
        ExceptionWindow.makeLabel(res, "Не удается обновить решение");
        if (res.getCode() == 400) {
            ExceptionWindow.makeLabel(res, "Ошибка ввода данных");
        }
        if (res.getCode() == 200) {
            WindowFunction.returnIntoUserWindow(getPanel());
        }
    }
}