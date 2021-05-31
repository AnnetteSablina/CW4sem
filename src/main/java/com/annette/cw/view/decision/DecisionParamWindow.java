package com.annette.cw.view.decision;

import com.annette.cw.controller.Controller;
import com.annette.cw.entity.Decision;
import com.annette.cw.utility.Result;
import com.annette.cw.utility.Updater;
import com.annette.cw.view.ExceptionWindow;
import com.annette.cw.view.utility.WindowFunction;
import com.annette.cw.view.utility.WindowUtil;
import com.annette.cw.view.utility.validation.DecisionValidator;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class DecisionParamWindow extends JFrame {
    private static JPanel panel = new JPanel();
    private static ArrayList<JTextField> fields = new ArrayList<>();
    private static ArrayList<JComboBox<Integer>> states = new ArrayList<>();
    private static final Integer[] values = {1, 2, 3, 4, 5, 6, 7};
    private static Integer quantity;
    private int mode;

    static {
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 30));
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

    public DecisionParamWindow(int mode) {
        panel.removeAll();
        this.mode = mode;
        fields.clear();
        states.clear();
        this.setTitle("Выбор решения");
        this.setResizable(false);
        this.setSize(600, 400);
        drawUI();
        updateUI();
        this.add(panel);
        this.setVisible(true);
        if (Controller.getInstance().getChangeableDecision() != null) {
            ExceptionWindow.makeLabel("При изменении результаты в БД будут удалены");
        }
    }

    private static void addTextField(String description) {
        JPanel compPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        compPanel.setBackground(new Color(120, 110, 255));
        WindowUtil.addLabel(description, compPanel);
        JTextField field = new JTextField();
        field.setPreferredSize(new Dimension(200, 30));
        field.setBackground(new Color(130, 240, 210));
        fields.add(field);
        compPanel.add(field);
        DecisionParamWindow.panel.add(compPanel);
    }

    private static void addComboBox(String description) {
        JPanel compPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
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

    private void drawUI() {
        addTextField("Название решения");
        addTextField("Коэффициент пессимизма");
        addComboBox("Количество состояний природы");
        addComboBox("Количество стратегий");
        WindowUtil.addSmallWindowButton("Назад", e -> comeBack(), getPanel());
        WindowUtil.addSmallWindowButton("Сохранить", e -> saveAll(), getPanel());

    }

    private static void updateUI() {
        if (Controller.getInstance().getChangeableDecision() != null) {
            fields.get(0).setText(Controller.getInstance().getChangeableDecision().getName());
            fields.get(1).setText(String.valueOf(Controller.getInstance().getChangeableDecision().getPessimismCoefficient()));
            int pos = updateComboBox(states.get(0), Controller.getInstance().getChangeableDecision().getNatureStatesCount());
            states.get(0).setSelectedIndex(pos);
            int pos1 = updateComboBox(states.get(1), Controller.getInstance().getChangeableDecision().getStrategyList().size());
            states.get(1).setSelectedIndex(pos1);

        }
    }

    private static int updateComboBox(JComboBox<Integer> state, int value) {
        int pos = 0;
        int k = 0;
        for (Integer i : values) {
            if (i == value) {
                pos = k;
            }
            k++;
        }
        return pos;
    }

    private void saveAll() {
        if (DecisionValidator.isDecisionValid(DecisionParamWindow.getFields())) {
            quantity = (Integer) states.get(1).getSelectedItem();
            this.dispose();
            new EnterStrategyNameWindow(mode);
        }
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
            Updater.updateCurrentDecision(res.getResult());
            TableWindow.createUI();
        }
    }
}
