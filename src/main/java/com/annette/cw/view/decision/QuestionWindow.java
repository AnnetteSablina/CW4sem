package com.annette.cw.view.decision;

import com.annette.cw.view.utility.WindowFunction;
import com.annette.cw.view.utility.WindowUtil;

import javax.swing.*;
import java.awt.*;

public class QuestionWindow extends JFrame {
    private static JPanel panel = new JPanel();

    static {
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 90, 60));
        panel.setBackground(new Color(120, 110, 255));
    }

    public static JPanel getPanel() {
        return panel;
    }

    public QuestionWindow() {
        panel.removeAll();
        this.setTitle("Выбор решения");
        this.setResizable(false);
        this.setSize(600, 400);
        drawUI();
        this.add(panel);
        this.setVisible(true);
    }

    private void drawUI() {
        panel.setBorder(BorderFactory.createTitledBorder("Выбор изменения решения"));
        WindowUtil.addSmallWindowButton("Принять решение", e -> makeDecision(), getPanel());
        WindowUtil.addSmallWindowButton("Редактировать решение", e -> editDecision(), getPanel());
        WindowUtil.addSmallWindowButton("Назад", e -> comeBack(), getPanel());
    }

    private void makeDecision() {
        this.dispose();
        TableWindow.createUI();
    }

    private void editDecision() {
        this.dispose();
        new DecisionParamWindow();
    }

    private void comeBack() {
        this.dispose();
        WindowFunction.returnIntoUserWindow(panel);
    }
}
