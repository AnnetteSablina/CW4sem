package com.annette.cw.view;

import com.annette.cw.controller.Controller;
import com.annette.cw.entity.Organization;
import com.annette.cw.entity.User;
import com.annette.cw.service.Provider;
import com.annette.cw.utility.Result;
import com.annette.cw.utility.Update;
import com.annette.cw.view.utility.WindowFunction;
import com.annette.cw.view.utility.WindowUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class OrganizationWindow extends JFrame {
    private static JPanel panel = new JPanel();
    private static ArrayList<JTextField> fields = new ArrayList<>();

    static {
        panel.setBackground(new Color(120, 110, 255));
        OrganizationWindow.panel.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 50));
    }

    public static JPanel getPanel() {
        return panel;
    }

    public static void createAddOrgWindow() {
        panel.removeAll();
        fields.clear();
        drawUserUI();
        updateAddWindowUI();
        WindowFunction.util(getPanel());
    }

    public static void changeOrgWindow() {
        panel.removeAll();
        fields.clear();
        drawUserUI();
        updateChangeWindowUI();
        WindowFunction.util(getPanel());
    }

    private static void addTextField(String description) {
        JPanel compPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 0));
        compPanel.add(Box.createRigidArea(new Dimension(150, 0)));
        compPanel.setBackground(new Color(120, 110, 255));
        WindowUtil.addLabel(description, compPanel);
        JTextField field = new JTextField();
        field.setPreferredSize(new Dimension(200, 30));
        field.setBackground(new Color(130, 240, 210));
        fields.add(field);
        compPanel.add(field);
        compPanel.add(Box.createRigidArea(new Dimension(150, 0)));
        OrganizationWindow.panel.add(compPanel);
    }

    private static void drawUserUI() {
        if (Controller.getInstance().getSelfUser().getOrganization().getId() == null) {
            ExceptionWindow.makeLabel("Вас нет ни в одной организации");
            WindowFunction.returnIntoUserWindow(panel);
        } else {
            addTextField("Название организации:");
            addTextField("Тип организации:");
            addButton("Назад", e -> WindowFunction.returnIntoUserWindow(panel));
        }

    }

    private static void updateAddWindowUI() {
        panel.setBorder(BorderFactory.createTitledBorder("Добавление организации"));
        addButton("Сохранить", e -> saveAddedOrg());
    }

    private static void updateChangeWindowUI() {
        panel.setBorder(BorderFactory.createTitledBorder("Изменение организации"));
        addButton("Сохранить", e -> saveChangedOrg());
        updateTextFields(Controller.getInstance().getSelfUser());
    }

    private static void addButton(String caption, ActionListener actionListener) {
        JButton button = new JButton(caption);
        button.setPreferredSize(new Dimension(200, 30));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(actionListener);
        button.setBackground(new Color(130, 240, 210));
        OrganizationWindow.panel.add(button);
    }

    private static void updateTextFields(User currentUser) {
        if (currentUser != null) {
            fields.get(0).setText(currentUser.getOrganization().getName());
            fields.get(1).setText(currentUser.getOrganization().getType());
        }
    }

    private static void err(Result<Organization> res) {
        ExceptionWindow.makeLabel(res, "Не удается обновить пользователя");
        if (res.getCode() == 400) {
            ExceptionWindow.makeLabel(res, "Ошибка ввода данных");
        }
        if (res.getCode() == 200) {
            Update.updateSelfUser();
            WindowFunction.returnIntoUserWindow(getPanel());
        }
    }

    private static void saveAddedOrg() {
        Provider.getInstance().addOrganization(fields.get(0).getText(), fields.get(1).getText(),
                OrganizationWindow::err);
    }

    private static void saveChangedOrg() {
        Provider.getInstance().updateOrganization(fields.get(0).getText(), fields.get(1).getText(),
                Controller.getInstance().getSelfUser().getOrganization().getId(), OrganizationWindow::err);
    }
}
