package com.annette.cw.view.utility;

import javax.swing.*;
import java.awt.*;

public class WindowUtil {
    public static void addLabel(String description, JPanel compPanel) {
        JLabel nameLabel = new JLabel(description);
        nameLabel.setPreferredSize(new Dimension(150, 30));
        compPanel.add(nameLabel);
    }
}
