package com.annette.cw.view;

import com.annette.cw.view.utility.UserWindowNavigation;
import com.annette.cw.view.utility.WindowFunction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class UserWindow {

    private static JPanel panel = new JPanel();

    static {
        panel.setBackground(new Color(120, 110, 255));
        UserWindow.panel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));
    }

    public static JPanel getPanel() {
        return panel;
    }

    public static void createAdminWindow() {
        panel.removeAll();
        createAdminUI();
        WindowFunction.util(panel);
    }

    public static void createUserWindow() {
        panel.removeAll();
        createUserUI();
        WindowFunction.util(panel);
    }

    private static void addButton(String caption, ActionListener actionListener) {
        JButton button = new JButton(caption);
        JPanel compPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        compPanel.setBackground(new Color(120, 110, 255));
        compPanel.add(Box.createRigidArea(new Dimension(50,0)));
        button.setPreferredSize(new Dimension(350,30));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(actionListener);
        button.setBackground(new Color(130, 240, 210));
        compPanel.add(button);
        compPanel.add(Box.createRigidArea(new Dimension(50,0)));
        UserWindow.panel.add(compPanel);
    }

    private static void createAdminUI() {
        panel.setBorder(BorderFactory.createTitledBorder("Меню администратора"));
        addButton("Зарегистрировать пользователя", e -> UserWindowNavigation.logInUser());
        addButton("Изменить свои данные",e->UserWindowNavigation.changeCurrentUserInfo());
        addButton("Изменить данные пользователя ", e -> UserWindowNavigation.changeUserInfo());
        addButton("Повысить пользователя", e -> UserWindowNavigation.promoteEmployee());
        addButton("Добавить организацию", e -> UserWindowNavigation.addOrganization());
        addButton("Изменить организацию", e -> UserWindowNavigation.changeOrganization());
        addButton("Показать количество сотрудников в организации", e -> UserWindowNavigation.showEmployeeCount());
        addButton("Принять решение", e -> UserWindowNavigation.makeDecision());
        addButton("Создать отчет",e->UserWindowNavigation.createReport());
        addButton("Выйти из аккаунта", e -> UserWindowNavigation.returnToStartWindow());
    }

    private static void createUserUI() {
        panel.setBorder(BorderFactory.createTitledBorder("Меню пользователя"));
        addButton("Изменить свои данные",e->UserWindowNavigation.changeCurrentUserInfo());
        addButton("Добавить организацию",e->UserWindowNavigation.addOrganization());
        addButton("Принять решение", e -> UserWindowNavigation.makeDecision());
        addButton("Создать отчет",e->UserWindowNavigation.createReport());
        addButton(" Выйти из аккаунта ", e -> UserWindowNavigation.returnToStartWindow());
    }
}
