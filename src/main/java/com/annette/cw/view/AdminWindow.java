package com.annette.cw.view;

import com.annette.cw.view.utility.AdminWindowFunction;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class AdminWindow {
    private static final JPanel panel = new JPanel();

    public static JPanel getPanel() {
        return panel;
    }

    public static void createAdminWindow(Window window) {
        panel.setBackground(new Color(120, 110, 255));
        createUI();
        window.add(panel);
        window.repaint();
        window.setVisible(true);
    }

    private static void addButton(String caption, ActionListener actionListener) {
        JButton button = new JButton(caption);
        button.setPreferredSize(new Dimension(150,25));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(actionListener);
        AdminWindow.panel.add(button);
        AdminWindow.panel.add (Box.createRigidArea(new Dimension(0,25)));
    }

    private static void createUI() {
        panel.setBorder(BorderFactory.createTitledBorder("Меню администратора"));
        AdminWindow.panel.setLayout(new BoxLayout(AdminWindow.panel, BoxLayout.Y_AXIS));
        AdminWindow.panel.add (Box.createRigidArea(new Dimension(0,50)));
        addButton("Зарегистрировать пользователя",e->AdminWindowFunction.logInUser());
        //addButton("Изменить данные пользователя ");
        //addButton("    Добавить организацию    ");
        //addButton("    Изменить организацию    ");
       // addButton("Показать количество сотрудников в организации");
       // addButton("              Принять решение              ");
        addButton(" Выйти из аккаунта ",e->StartWindow.startWindow());
    }
}
