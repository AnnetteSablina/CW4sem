package com.annette.cw.view;

import com.annette.cw.controller.Controller;
import com.annette.cw.entity.dto.DecisionPayload;
import com.annette.cw.service.Provider;
import com.annette.cw.view.utility.WindowFunction;
import com.annette.cw.view.utility.WindowUtil;
import com.google.gson.Gson;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class EnterStrategyNameWindow extends JFrame {
    private static JPanel panel = new JPanel();
    private static ArrayList<JTextField> fields = new ArrayList<>();

    static {
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 90, 40));
        panel.setBackground(new Color(120, 110, 255));
    }

    public EnterStrategyNameWindow() {
        panel.removeAll();
        this.setTitle("Ввод названий стритегий");
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
        if(Controller.getInstance().getChangeableDecision() != null)
        field.setText(Controller.getInstance().getChangeableDecision().getName());
        field.setPreferredSize(new Dimension(200, 30));
        field.setBackground(new Color(130, 240, 210));
        fields.add(field);
        compPanel.add(field);
        EnterStrategyNameWindow.panel.add(compPanel);
    }

    private static void addButton(String caption, ActionListener actionListener) {
        JButton button = new JButton(caption);
        button.setPreferredSize(new Dimension(200, 30));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(actionListener);
        button.setBackground(new Color(130, 240, 210));
        EnterStrategyNameWindow.panel.add(button);
    }

    private void drawUI() {
        for (int i = 0; i < DecisionParamWindow.getQuantity(); i++) {
            addTextField("Название стратегии");
        }
        addButton("Назад", e -> comeBack());
        addButton("Сохранить", e -> saveAll());

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
        System.out.println(Controller.getInstance().getSelfUser().getId());
        String coefficient = DecisionParamWindow.getFields().get(1).getText();
        Double coeff = Double.parseDouble(coefficient);

        Gson gson = new Gson();
        DecisionPayload emp = new DecisionPayload(DecisionParamWindow.getFields().get(0).getText(),"", strategyList,
                (Integer) DecisionParamWindow.getStates().get(0).getSelectedItem(), coeff,
                Controller.getInstance().getSelfUser().getId(), Instant.now());

        String jsonString = gson.toJson(emp);

        System.out.println(gson.fromJson(jsonString, DecisionPayload.class));

        if (Controller.getInstance().getChangeableDecision() == null) {
            Provider.getInstance().addDecision(DecisionParamWindow.getFields().get(0).getText(), strategyList,
                    (Integer) DecisionParamWindow.getStates().get(0).getSelectedItem(), coeff,
                    Controller.getInstance().getSelfUser().getId(),
                    DecisionParamWindow::err);
            this.dispose();
        } else {
            Provider.getInstance().updateDecisionById(DecisionParamWindow.getFields().get(0).getText(), strategyList,
                    (Integer) DecisionParamWindow.getStates().get(0).getSelectedItem(),coeff,
                    Controller.getInstance().getSelfUser().getId(), DecisionParamWindow::err);
            this.dispose();
        }
    }
}
