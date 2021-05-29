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
        JButton button = new JButton(caption);
        button.setPreferredSize(new Dimension(200, 30));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addActionListener(actionListener);
        button.setBackground(new Color(130, 240, 210));
        panel.add(button);
    }
}
