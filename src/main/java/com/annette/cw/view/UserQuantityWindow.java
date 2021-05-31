package com.annette.cw.view;

import com.annette.cw.controller.Controller;
import com.annette.cw.view.decision.TableWindow;
import com.annette.cw.view.utility.WindowUtil;

import javax.swing.*;
import java.awt.*;

public class UserQuantityWindow extends JFrame {

    private static JPanel panel = new JPanel();

    static {
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 70));
        panel.setBackground(new Color(120, 110, 255));
    }

    public static JPanel getPanel() {
        return panel;
    }

    public UserQuantityWindow() {
        panel.removeAll();
        this.setTitle("Количество сотрудников");
        this.setResizable(false);
        this.setSize(600, 300);
        this.add(panel);
        addText();
        WindowUtil.addSmallWindowButton("Окей", e -> goToUserMenu(), panel);
        this.setVisible(true);
    }
    private static void addText() {
        JPanel compPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        compPanel.setBackground(new Color(120, 110, 255));
        JLabel nameLabel = new JLabel("Количество сотрудников в вашей организации " +
                Controller.getInstance().getSelfUser().getOrganization().getEmployeeCount());
        nameLabel.setPreferredSize(new Dimension(450, 30));

        compPanel.add(nameLabel);
        UserQuantityWindow.panel.add(compPanel);
    }
    private void goToUserMenu() {
        this.dispose();
        TableWindow.goToUserMenu();
    }

}
