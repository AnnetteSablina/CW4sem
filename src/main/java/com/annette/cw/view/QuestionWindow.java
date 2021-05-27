package com.annette.cw.view;

import com.annette.cw.view.utility.WindowFunction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class QuestionWindow extends JFrame {
    private static JPanel panel = new JPanel();

    static {
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 90, 60));
        panel.setBackground(new Color(120, 110, 255));
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
        addButton("Принять решение", e-> comeBack());
        addButton("Редактировать решение", e -> new DecisionParamWindow());
        addButton("Назад", e -> comeBack());

    }

    private static void addButton(String caption, ActionListener actionListener) {
        JButton button = new JButton(caption);
        button.setPreferredSize(new Dimension(200, 30));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(actionListener);
        button.setBackground(new Color(130, 240, 210));
        QuestionWindow.panel.add(button);
    }

    private void comeBack() {
        this.dispose();
        WindowFunction.returnIntoUserWindow(panel);
    }
}
