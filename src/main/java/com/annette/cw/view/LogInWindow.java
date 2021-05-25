package com.annette.cw.view;

import com.annette.cw.entity.Organization;
import com.annette.cw.entity.User;
import com.annette.cw.service.Provider;
import com.annette.cw.utility.Result;
import com.annette.cw.utility.Searcher;
import com.annette.cw.utility.TokenChecker;

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
        LogInWindow.panel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 30));
    }

    public static JPanel getPanel() {
        return panel;
    }

    public static void createCurrentUserWindow() {
        drawUserUI();
        changeCurrentUserUI();
        Window.getWindow().add(panel);
        Window.getWindow().repaint();
        Window.getWindow().setVisible(true);
    }

    public static void createUserChangeWindow() {
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
        fields.get(0).setText(currentUser.getUsername());
        fields.get(1).setText(currentUser.getEmail());
        fields.get(2).setText(currentUser.getFirstName());
        fields.get(3).setText(currentUser.getLastName());
    }

    private static void updateComboBox(List<Organization> organizations, User user) {
        int i = 0;
        for (Organization org : organizations) {
            organization.put(org.getId(), org.getName());
        }
        int pos = 0;
        for (Map.Entry<Integer, String> map : organization.entrySet()) {
            if (map.getValue().equals(user.getOrganization().getName())) {
                pos = i;
            }
            org.get(0).insertItemAt(map.getValue(), org.get(0).getItemCount());
            i++;
        }
        org.get(0).setSelectedIndex(pos);
    }

    private static void addButton(String caption, ActionListener actionListener) {
        JButton button = new JButton(caption);
        button.setPreferredSize(new Dimension(390, 30));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(actionListener);
        button.setBackground(new Color(130, 240, 210));
        LogInWindow.panel.add(button);
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
        panel.setBorder(BorderFactory.createTitledBorder("Регистрирование пользователя"));
        addTextField("Никнейм:", false);
        addTextField("Пароль:", true);
        addTextField("Повторите пароль:", true);
        addTextField("e-Mail:", false);
        addTextField("Имя:", false);
        addTextField("Фамилия:", false);
        addComboBox();
    }

    private static void changeCurrentUserUI() {
        panel.setBorder(BorderFactory.createTitledBorder("Регистрирование пользователя"));
        Provider.getInstance().getUserByToken(TokenChecker.readToken(), (Result<User> res)
                -> {
            updateTextFields(res.getResult());
            Provider.getInstance().getOrganizations((Result<List<Organization>> org) -> {
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
        int orgId = Searcher.findOrgID(organization, org.get(0).getSelectedIndex());
        if (!fields.isEmpty() && !org.isEmpty())
            if (passFields.isEmpty()) {
                Provider.getInstance().updateCurrentUser(fields.get(0).getText(), null, fields.get(1).getText(),
                        fields.get(2).getText(), fields.get(3).getText(), orgId, (Result<User> res)
                                -> {
                            ExceptionWindow.makeLabel(res, "Не удается обновить пользователя");
                        });
            } else {
                Provider.getInstance().updateCurrentUser(fields.get(0).getText(), new String(passFields.get(0).getPassword()),
                        fields.get(1).getText(), fields.get(2).getText(), fields.get(3).getText(), orgId,
                        (Result<User> res) -> {
                            ExceptionWindow.makeLabel(res, "Не удается обновить пользователя");

                        });
            }


    }

}
