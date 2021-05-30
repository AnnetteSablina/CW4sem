package com.annette.cw.view.utility;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class WindowUtil {
    public static void addLabel(String description, JPanel compPanel) {
        JLabel nameLabel = new JLabel(description);
        nameLabel.setPreferredSize(new Dimension(200, 30));
        compPanel.add(nameLabel);
    }
    public static void addSmallWindowButton(String caption, ActionListener actionListener,JPanel panel) {
        JPanel compPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        compPanel.setBackground(new Color(120, 110, 255));
        JButton button = new JButton(caption);
        button.setPreferredSize(new Dimension(200, 30));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(actionListener);
        button.setBackground(new Color(130, 240, 210));
        compPanel.add(button);
        panel.add(compPanel);
    }
}
