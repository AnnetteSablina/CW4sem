package com.annette.cw.view;

import com.annette.cw.controller.Controller;
import com.annette.cw.entity.Organization;
import com.annette.cw.entity.User;
import com.annette.cw.service.Provider;
import com.annette.cw.utility.Result;
import com.annette.cw.utility.Updater;
import com.annette.cw.view.utility.WindowFunction;
import com.annette.cw.view.utility.WindowUtil;
import com.annette.cw.view.utility.validation.OrganizationValidator;

import javax.swing.*;
import java.awt.*;
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
            WindowUtil.addSmallWindowButton("Назад", e -> WindowFunction.returnIntoUserWindow(panel),getPanel());
        }

    }

    private static void updateAddWindowUI() {
        panel.setBorder(BorderFactory.createTitledBorder("Добавление организации"));
        WindowUtil.addSmallWindowButton("Сохранить", e -> saveAddedOrg(),getPanel());
    }

    private static void updateChangeWindowUI() {
        panel.setBorder(BorderFactory.createTitledBorder("Изменение организации"));
        WindowUtil.addSmallWindowButton("Сохранить", e -> saveChangedOrg(),getPanel());
        updateTextFields(Controller.getInstance().getSelfUser());
    }


    private static void updateTextFields(User currentUser) {
        if (currentUser != null) {
            fields.get(0).setText(currentUser.getOrganization().getName());
            fields.get(1).setText(currentUser.getOrganization().getType());
        }
    }

    private static void err(Result<Organization> res) {
        ExceptionWindow.makeLabel(res, "Не удается обновить организацию");
        if (res.getCode() == 400) {
            ExceptionWindow.makeLabel(res, "Ошибка ввода данных");
        }
        if (res.getCode() == 200) {
            Updater.updateSelfUser();
            WindowFunction.returnIntoUserWindow(getPanel());
        }
    }

    private static void saveAddedOrg() {
        if (OrganizationValidator.isOrgInfoValid(fields))
        Provider.getInstance().addOrganization(fields.get(0).getText(), fields.get(1).getText(),
                OrganizationWindow::err);
    }

    private static void saveChangedOrg() {
        if (OrganizationValidator.isOrgInfoValid(fields))
        Provider.getInstance().updateOrganization(fields.get(0).getText(), fields.get(1).getText(),
                Controller.getInstance().getSelfUser().getOrganization().getId(), OrganizationWindow::err);
    }
}
