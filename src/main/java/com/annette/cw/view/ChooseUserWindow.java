package com.annette.cw.view;

import com.annette.cw.controller.Controller;
import com.annette.cw.entity.User;
import com.annette.cw.service.Provider;
import com.annette.cw.utility.Result;
import com.annette.cw.utility.Searcher;
import com.annette.cw.view.utility.WindowFunction;
import com.annette.cw.view.utility.WindowUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ChooseUserWindow extends JFrame {
    private static JPanel panel = new JPanel();
    private static ArrayList<JComboBox<String>> userBox = new ArrayList<>();
    private static Map<Integer, String> userMap = new TreeMap<>();

    static {
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 80));
        panel.setBackground(new Color(120, 110, 255));
    }

    public ChooseUserWindow() {
        panel.removeAll();
        clearAll();
        this.setTitle("Выбор пользователя");
        this.setResizable(false);
        this.setSize(600, 400);
        drawUI();
        changeChooseUserUI();
        this.add(panel);
        this.setVisible(true);
    }

    private static void clearAll() {
        userBox.clear();
        userMap.clear();
    }

    public static ArrayList<JComboBox<String>> getUsers() {
        return userBox;
    }

    public static JPanel getPanel() {
        return panel;
    }

    private void drawUI() {
        addComboBox();
        addButton("Назад", e ->comeBack());
        addButton("Подтвердить", e -> selectCurrentUser());
    }

    private static void addComboBox() {
        JPanel compPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0));
        compPanel.setBackground(new Color(120, 110, 255));
        WindowUtil.addLabel("Выберите пользователя", compPanel);
        JComboBox<String> users = new JComboBox<>();
        users.setPreferredSize(new Dimension(350, 30));
        users.setBackground(new Color(130, 240, 210));
        users.setEditable(false);
        compPanel.add(users);
        userBox.add(users);
        ChooseUserWindow.panel.add(compPanel);
    }

    private static void addButton(String caption, ActionListener actionListener) {
        JButton button = new JButton(caption);
        button.setPreferredSize(new Dimension(200, 30));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(actionListener);
        button.setBackground(new Color(130, 240, 210));
        ChooseUserWindow.panel.add(button);
    }

    private static void updateComboBox(List<User> userList) {
        if (userList != null) {
            for (User user : userList) {
                userMap.put(user.getId(), user.getUsername() + " " + user.getFirstName() + " " + user.getLastName());
            }
            insertValues();
            userBox.get(0).setSelectedIndex(0);
        }
    }

    private static void changeChooseUserUI() {
        Provider.getInstance().getUsers((Result<List<User>> res)
                -> {
            if (res.getResult() == null) {
                ExceptionWindow.makeLabel("Невозможно загрузить пользователей");
                WindowFunction.returnIntoUserWindow(getPanel());
                return;
            }
            Controller.getInstance().setUserList(res.getResult());
            updateComboBox(res.getResult());
        });

    }

    private void selectCurrentUser() {
        int userId = Searcher.findObjByID(userMap, userBox.get(0).getSelectedIndex());
        Controller.getInstance().setChangeableUser(Searcher.findUserByID(userId));
        this.dispose();
        Window.getWindow().remove(UserWindow.getPanel());
        LogInWindow.createUserChangeWindow();
    }

    private static void insertValues() {
        for (Map.Entry<Integer, String> map : userMap.entrySet()) {
            userBox.get(0).insertItemAt(map.getValue(), userBox.get(0).getItemCount());
        }
    }

    private void comeBack() {
        this.dispose();
        WindowFunction.returnIntoUserWindow(panel);
    }
}


