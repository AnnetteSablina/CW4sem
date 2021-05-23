package com.annette.cw.view;

import com.annette.cw.view.utility.UserWindowNavigation;

import javax.swing.*;
import javax.xml.crypto.URIDereferencer;
import java.awt.*;
import java.awt.event.ActionListener;

public class UserWindow {

    private static JPanel panel = new JPanel();

    static {
        panel.setBackground(new Color(120, 110, 255));
        UserWindow.panel.setLayout(new BoxLayout(UserWindow.panel, BoxLayout.Y_AXIS));
    }

    public static JPanel getPanel() {
        return panel;
    }

    public static void createAdminWindow() {
        createAdminUI();
        Window.getWindow().add(panel);
        Window.getWindow().repaint();
        Window.getWindow().setVisible(true);
    }

    public static void createUserWindow() {
        createUserUI();
        Window.getWindow().add(panel);
        Window.getWindow().repaint();
        Window.getWindow().setVisible(true);
    }

    private static void addButton(String caption, ActionListener actionListener) {
        JButton button = new JButton(caption);
        button.setPreferredSize(new Dimension(150, 25));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(actionListener);
        button.setBackground(new Color(130, 240, 210));
        UserWindow.panel.add(button);
        UserWindow.panel.add(Box.createRigidArea(new Dimension(0, 25)));
    }

    private static void createAdminUI() {
        panel.setBorder(BorderFactory.createTitledBorder("Меню администратора"));
        UserWindow.panel.add(Box.createRigidArea(new Dimension(0, 60)));
        addButton("Зарегистрировать пользователя", e -> UserWindowNavigation.logInUser());
        addButton("Изменить данные пользователя ", e -> UserWindowNavigation.changeUserInfo());
        addButton("    Добавить организацию    ", e -> UserWindowNavigation.addOrganization());
        addButton("    Изменить организацию    ", e -> UserWindowNavigation.changeOrganization());
        addButton("Показать количество сотрудников в организации", e -> UserWindowNavigation.showEmployeeCount());
        addButton("              Принять решение              ", e -> UserWindowNavigation.makeDecision());
        addButton(" Выйти из аккаунта ", e -> UserWindowNavigation.returnToStartWindow());
    }

    private static void createUserUI() {
        panel.setBorder(BorderFactory.createTitledBorder("Меню пользователя"));

    }
}
