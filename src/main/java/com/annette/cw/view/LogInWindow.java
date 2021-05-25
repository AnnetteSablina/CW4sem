package com.annette.cw.view;

import com.annette.cw.controller.Controller;
import com.annette.cw.entity.Organization;
import com.annette.cw.entity.User;
import com.annette.cw.service.Provider;
import com.annette.cw.utility.Result;
import com.annette.cw.utility.Searcher;
import com.annette.cw.utility.TokenChecker;
import com.annette.cw.view.utility.WindowFunction;

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

    static {
        panel.setBackground(new Color(120, 110, 255));
        LogInWindow.panel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 25));
    }

    public static JPanel getPanel() {
        return panel;
    }

    public static ArrayList<JTextField> getFields() {
        return fields;
    }

    public static ArrayList<JPasswordField> getPassFields() {
        return passFields;
    }

    public static Map<Integer, String> getOrganization() {
        return organization;
    }

    public static void clearAllFields() {
        fields.clear();
        passFields.clear();
        org.clear();
        organization.clear();
    }

    public static void createCurrentUserWindow() {
        panel.removeAll();
        clearAllFields();
        drawUserUI();
        changeCurrentUserUI();
        WindowFunction.util(getPanel());
    }

    public static void createUserChangeWindow() {
        panel.removeAll();
        drawUserUI();
    }

    private static void addTextField(String description, boolean isPasswordField) {
        JPanel compPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 0));
        compPanel.add(Box.createRigidArea(new Dimension(150, 0)));
        compPanel.setBackground(new Color(120, 110, 255));
        addLabel(description, compPanel);
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
            pos = getCurrentPos(user,pos);
            org.get(0).setSelectedIndex(pos);
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

    private static Integer getCurrentPos(User user, Integer pos){
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
        addLabel("Выберите организацию", compPanel);
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
        addButton("Назад",e->WindowFunction.returnIntoUserWindow(panel));
    }

    private static void changeCurrentUserUI() {
        panel.setBorder(BorderFactory.createTitledBorder("Изменение текущего пользователя"));
        Provider.getInstance().getUserByToken(TokenChecker.readToken(), (Result<User> res)
                -> {
            updateTextFields(res.getResult());
            Provider.getInstance().getOrganizations((Result<List<Organization>> org) -> {
                if (org.getResult() == null) {
                    ExceptionWindow.makeLabel("Невозможно загрузить список организаций");
                    WindowFunction.returnIntoUserWindow(getPanel());
                }
                updateComboBox(org.getResult(), res.getResult());
            });

        });
        addButton("Сохранить", e -> LogInWindow.saveCurrentUserAll());
    }

    private static void addLabel(String description, JPanel compPanel) {
        JLabel nameLabel = new JLabel(description);
        nameLabel.setPreferredSize(new Dimension(150, 30));
        compPanel.add(nameLabel);
    }

    private static void saveCurrentUserAll() {
        Integer orgId = Searcher.findOrgID(organization, org.get(0).getSelectedIndex());
        if (orgId == -1) orgId = null;
        if (passFields.get(0).getPassword().length == 0) {
            Provider.getInstance().updateCurrentUser(fields.get(0).getText(), null, fields.get(1).getText(),
                    fields.get(2).getText(), fields.get(3).getText(), orgId, (Result<User> res)
                            -> {
                        ExceptionWindow.makeLabel(res, "Не удается обновить пользователя");
                        if (res.getCode() == 200) {
                            Controller.getInstance().setSelfUser(res.getResult());
                            WindowFunction.returnIntoUserWindow(getPanel());
                        }
                    });
        } else {
            Provider.getInstance().updateCurrentUser(fields.get(0).getText(), new String(passFields.get(0).getPassword()),
                    fields.get(1).getText(), fields.get(2).getText(), fields.get(3).getText(), orgId,
                    (Result<User> res) -> {
                        ExceptionWindow.makeLabel(res, "Не удается обновить пользователя");
                        if (res.getCode() == 200) {
                            Controller.getInstance().setSelfUser(res.getResult());
                            WindowFunction.returnIntoUserWindow(getPanel());
                        }
                    });
        }
    }

}
