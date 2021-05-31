package com.annette.cw.view.decision;

import com.annette.cw.controller.Controller;
import com.annette.cw.service.Provider;
import com.annette.cw.view.ExceptionWindow;
import com.annette.cw.view.utility.WindowFunction;
import com.annette.cw.view.utility.WindowUtil;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class EnterStrategyNameWindow extends JFrame {
    private static JPanel panel = new JPanel();
    private static ArrayList<JTextField> fields = new ArrayList<>();
    private int mode ;

    static {
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 15));
        panel.setBackground(new Color(120, 110, 255));
    }

    public static JPanel getPanel() {
        return panel;
    }

    public EnterStrategyNameWindow(int mode) {
        panel.removeAll();
        fields.clear();
        this.mode = mode;
        this.setTitle("Ввод названий стратегий");
        this.setResizable(false);
        this.setSize(600, 400);
        drawUI();
        this.add(panel);
        this.setVisible(true);
    }

    private static void addTextField(String description) {
        JPanel compPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 0));
        compPanel.setBackground(new Color(120, 110, 255));
        WindowUtil.addLabel(description, compPanel);
        JTextField field = new JTextField();
        field.setPreferredSize(new Dimension(200, 30));
        field.setBackground(new Color(130, 240, 210));
        fields.add(field);
        compPanel.add(field);
        EnterStrategyNameWindow.panel.add(compPanel);
    }

    private void drawUI() {
        for (int i = 0; i < DecisionParamWindow.getQuantity(); i++) {
            addTextField("Название стратегии");
        }
        WindowUtil.addSmallWindowButton("Назад", e -> comeBack(), getPanel());
        WindowUtil.addSmallWindowButton("Сохранить", e -> saveAll(), getPanel());
    }

    private void comeBack() {
        this.dispose();
        WindowFunction.returnIntoUserWindow(panel);
    }

    private void saveAll() {
        List<String> strategyList = new ArrayList<>();
        for (JTextField strategyName : fields) {
            strategyList.add(strategyName.getText());
        }
        String coefficient = DecisionParamWindow.getFields().get(1).getText();
        Double coeff = null;
        try {
            coeff = Double.parseDouble(coefficient);
        } catch (NumberFormatException e) {
            ExceptionWindow.makeLabel("Неверный формат коэффициента");
        }

        if (Controller.getInstance().getChangeableDecision() != null && mode == 1) {
            Provider.getInstance().updateDecisionById(DecisionParamWindow.getFields().get(0).getText(), strategyList,
                    (Integer) DecisionParamWindow.getStates().get(0).getSelectedItem(), coeff,Controller.getInstance().getSelfUser().getId(),
                    Controller.getInstance().getChangeableDecision().getId(), DecisionParamWindow::err);
            return;
        }
        if (mode == 0)
        Provider.getInstance().addDecision(DecisionParamWindow.getFields().get(0).getText(), strategyList,
                (Integer) DecisionParamWindow.getStates().get(0).getSelectedItem(), coeff,
                Controller.getInstance().getSelfUser().getId(), DecisionParamWindow::err);

        this.dispose();

    }
}
