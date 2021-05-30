package com.annette.cw.view;

import com.annette.cw.controller.Controller;
import com.annette.cw.entity.Organization;
import com.annette.cw.entity.User;
import com.annette.cw.entity.dto.AuthenticationResponse;
import com.annette.cw.service.Provider;
import com.annette.cw.utility.AutoEntering;
import com.annette.cw.utility.Result;
import com.annette.cw.utility.Searcher;
import com.annette.cw.utility.TokenChecker;
import com.annette.cw.view.utility.validation.UserChecker;
import com.annette.cw.view.utility.UserWindowNavigation;
import com.annette.cw.view.utility.WindowFunction;
import com.annette.cw.view.utility.WindowUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class LogInWindow {

    private static JPanel panel = new JPanel();
    private static ArrayList<JTextField> fields = new ArrayList<>();
    private static ArrayList<JPasswordField> passFields = new ArrayList<>();
    private static ArrayList<JComboBox<String>> org = new ArrayList<>();
    private static Map<Integer, String> organization = new TreeMap<>();
    private static boolean isNeedReauthorize = false;

    static {
        panel.setBackground(new Color(120, 110, 255));
        LogInWindow.panel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 25));
    }

    public static JPanel getPanel() {
        return panel;
    }

    public static void clearAllFields() {
        fields.clear();
        passFields.clear();
        org.clear();
        organization.clear();
    }

    public static void createCurrentUserWindow() {
        isNeedReauthorize = false;
        panel.removeAll();
        clearAllFields();
        drawUserUI();
        changeCurrentUserUI();
        WindowFunction.util(getPanel());
    }

    public static void createUserChangeWindow() {
        panel.removeAll();
        clearAllFields();
        drawUserUI();
        changeOtherUserUI();
        WindowFunction.util(getPanel());
    }

    public static void createLogInWindow() {
        panel.removeAll();
        clearAllFields();
        drawUserUI();
        changeLogInUserUI();
        WindowFunction.util(getPanel());
    }

    private static void addTextField(String description, boolean isPasswordField) {
        JPanel compPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 0));
        compPanel.add(Box.createRigidArea(new Dimension(150, 0)));
        compPanel.setBackground(new Color(120, 110, 255));
        WindowUtil.addLabel(description, compPanel);
        if (!isPasswordField) {
            JTextField field = new JTextField();
            field.setPreferredSize(new Dimension(200, 30));
            field.setBackground(new Color(130, 240, 210));
            fields.add(field);
            compPanel.add(field);
        } else {
            JPasswordField field = new JPasswordField();
            field.setPreferredSize(new Dimension(200, 30));
            field.setBackground(new Color(130, 240, 210));
            field.setEchoChar('*');
            passFields.add(field);
            compPanel.add(field);
        }
        compPanel.add(Box.createRigidArea(new Dimension(150, 0)));
        LogInWindow.panel.add(compPanel);
    }

    private static void updateTextFields(User currentUser) {
        if (currentUser != null) {
            fields.get(0).setText(currentUser.getUsername());
            fields.get(1).setText(currentUser.getEmail());
            fields.get(2).setText(currentUser.getFirstName());
            fields.get(3).setText(currentUser.getLastName());
        }
    }

    private static void updateComboBox(List<Organization> organizations, User user) {
        if (organizations != null && user != null) {
            for (Organization org : organizations) {
                organization.put(org.getId(), org.getName());
            }
            organization.put(-1, "Не выбрано");
            Integer pos = 0;
            pos = getCurrentPos(user, pos);
            org.get(0).setSelectedIndex(pos);
        }
    }

    private static void updateComboBox(List<Organization> organizations) {
        if (organizations != null) {
            for (Organization org : organizations) {
                organization.put(org.getId(), org.getName());
            }
            organization.put(-1, "Не выбрано");
            for (Map.Entry<Integer, String> map : organization.entrySet()) {
                org.get(0).insertItemAt(map.getValue(), org.get(0).getItemCount());
            }
            org.get(0).setSelectedIndex(0);
        }
    }

    private static void addButton(String caption, ActionListener actionListener) {
        JButton button = new JButton(caption);
        button.setPreferredSize(new Dimension(390, 30));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(actionListener);
        button.setBackground(new Color(130, 240, 210));
        LogInWindow.panel.add(button);
    }

    private static Integer getCurrentPos(User user, Integer pos) {
        int i = 0;
        for (Map.Entry<Integer, String> map : organization.entrySet()) {
            if (user.getOrganization() != null) {
                if (map.getValue().equals(user.getOrganization().getName())) {
                    pos = i;
                }
            }
            org.get(0).insertItemAt(map.getValue(), org.get(0).getItemCount());
            i++;
        }
        return pos;
    }

    private static void addComboBox() {
        JPanel compPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 0));
        compPanel.add(Box.createRigidArea(new Dimension(150, 0)));
        compPanel.setBackground(new Color(120, 110, 255));
        WindowUtil.addLabel("Выберите организацию", compPanel);
        JComboBox<String> organizations = new JComboBox<>();
        organizations.setPreferredSize(new Dimension(200, 30));
        organizations.setBackground(new Color(130, 240, 210));
        organizations.setEditable(false);
        compPanel.add(organizations);
        compPanel.add(Box.createRigidArea(new Dimension(150, 0)));
        org.add(organizations);
        LogInWindow.panel.add(compPanel);
    }

    private static void drawUserUI() {
        addTextField("Никнейм:", false);
        addTextField("Пароль:", true);
        addTextField("Повторите пароль:", true);
        addTextField("e-Mail:", false);
        addTextField("Имя:", false);
        addTextField("Фамилия:", false);
        addComboBox();
        if (new TokenChecker().isFileEmpty()) addButton("Назад", e -> returnToStartWindow());
        else addButton("Назад", e -> WindowFunction.returnIntoUserWindow(panel));
    }

    private static void changeCurrentUserUI() {
        panel.setBorder(BorderFactory.createTitledBorder("Изменение текущего пользователя"));
        Provider.getInstance().getUserByToken(new TokenChecker().readToken(), (Result<User> res)
                -> {
            updateTextFields(res.getResult());
            Provider.getInstance().getOrganizations((Result<List<Organization>> org) -> {
                if (org.getResult() == null) {
                    ExceptionWindow.makeLabel("Невозможно загрузить список организаций");
                    WindowFunction.returnIntoUserWindow(getPanel());
                }
                Controller.getInstance().setOrganizationList(org.getResult());
                updateComboBox(org.getResult(), res.getResult());
            });

        });
        addButton("Сохранить", e -> LogInWindow.saveCurrentUserAll());
    }

    private static void changeOtherUserUI() {
        panel.setBorder(BorderFactory.createTitledBorder("Изменение пользователя"));
        updateTextFields(Controller.getInstance().getChangeableUser());
        Provider.getInstance().getOrganizations((Result<List<Organization>> org) -> {
            if (org.getResult() == null) {
                ExceptionWindow.makeLabel("Невозможно загрузить список организаций");
                WindowFunction.returnIntoUserWindow(getPanel());
                return;
            }
            updateComboBox(org.getResult(), Controller.getInstance().getChangeableUser());
        });
        addButton("Сохранить", e -> LogInWindow.saveOtherUserAll());
    }

    private static void changeLogInUserUI() {
        panel.setBorder(BorderFactory.createTitledBorder("Регистрация пользователя"));
        Provider.getInstance().getOrganizations((Result<List<Organization>> org) -> {
            if (org.getResult() == null) {
                ExceptionWindow.makeLabel("Невозможно загрузить список организаций");
                WindowFunction.returnIntoUserWindow(getPanel());
                return;
            }
            updateComboBox(org.getResult());
        });
        addButton("Завершить регистрацию", e -> LogInWindow.saveLogInUserAll());
    }

    private static void saveCurrentUserAll() {
        Integer orgId = Searcher.findObjByID(organization, org.get(0).getSelectedIndex());
        if (orgId == -1) orgId = null;
        if (UserChecker.isCurrentUserNameChanged(fields)) isNeedReauthorize = true;
        if (passFields.get(0).getPassword().length == 0) {
            if (UserChecker.checkUser(fields))
                Provider.getInstance().updateCurrentUser(fields.get(0).getText(), null, fields.get(1).getText(),
                        fields.get(2).getText(), fields.get(3).getText(), orgId, LogInWindow::err);
        } else {
            if (UserChecker.checkUser(fields) && UserChecker.checkUserPass(passFields))
                Provider.getInstance().updateCurrentUser(fields.get(0).getText(), new String(passFields.get(0).getPassword()),
                        fields.get(1).getText(), fields.get(2).getText(), fields.get(3).getText(), orgId, LogInWindow::err);
        }
    }

    private static void err(Result<User> res) {
        ExceptionWindow.makeLabel(res, "Не удается обновить пользователя");
        if (res.getCode() == 400) {
            ExceptionWindow.makeLabel(res, "Ошибка ввода данных");
        }
        if (res.getCode() == 200) {
            if (isNeedReauthorize) {
                Window.getWindow().remove(panel);
                UserWindowNavigation.returnToStartWindow();
                return;
            }
            Controller.getInstance().setSelfUser(res.getResult());
            WindowFunction.returnIntoUserWindow(getPanel());
        }
    }

    private static void saveOtherUserAll() {
        Integer orgId = Searcher.findObjByID(organization, org.get(0).getSelectedIndex());
        if (orgId == -1) orgId = null;
        if (passFields.get(0).getPassword().length == 0) {
            if (UserChecker.checkUser(fields))
            Provider.getInstance().updateUser(fields.get(0).getText(), null, fields.get(1).getText(),
                    fields.get(2).getText(), fields.get(3).getText(), orgId, Controller.getInstance().getChangeableUser().getId(),
                    LogInWindow::err);
        } else {
            if (UserChecker.checkUser(fields) && UserChecker.checkUserPass(passFields))
            Provider.getInstance().updateUser(fields.get(0).getText(), new String(passFields.get(0).getPassword()),
                    fields.get(1).getText(), fields.get(2).getText(), fields.get(3).getText(), orgId,
                    Controller.getInstance().getChangeableUser().getId(), LogInWindow::err);
        }
    }

    private static void saveLogInUserAll() {
        Integer orgId = Searcher.findObjByID(organization, org.get(0).getSelectedIndex());
        if (orgId == -1) orgId = null;
        if (UserChecker.checkUser(fields) && UserChecker.checkUserPass(passFields))
        Provider.getInstance().signUp(fields.get(0).getText(), new String(passFields.get(0).getPassword()),
                fields.get(1).getText(), fields.get(2).getText(), fields.get(3).getText(), orgId,
                (Result<AuthenticationResponse> res) -> {
                    ExceptionWindow.makeLabel(res, "Не удается зарегистрировать пользователя");
                    if (res.getCode() == 400) {
                        ExceptionWindow.makeLabel(res, "Ошибка ввода данных");
                    }
                    if (res.getCode() == 200) {
                        if (Controller.getInstance().getSelfUser() != null &&
                                Controller.getInstance().getSelfUser().getRole().equals("ADMIN")) {
                            WindowFunction.returnIntoUserWindow(getPanel());
                        } else {
                            new TokenChecker();
                            if (TokenChecker.isFileEmpty()) {
                                new TokenChecker();
                                TokenChecker.writeToken(res.getResult().getAuthenticationToken());
                                panel.removeAll();
                                Window.getWindow().remove(panel);
                                AutoEntering.autoEntering();
                            }
                        }
                    }
                }


        );
    }

    private static void returnToStartWindow() {
        panel.removeAll();
        Window.getWindow().remove(panel);
        StartWindow.startWindow();
    }
}

