package com.annette.cw.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ExceptionWindow extends JFrame {

    public ExceptionWindow (JLabel label){
        this.setTitle("Error");
        this.setResizable(false);
        this.setSize(500, 300);
        JPanel jPanel = new JPanel(new BorderLayout());
        jPanel.setBackground(new Color(255, 0, 50));
        Font font = new Font(null, Font.PLAIN,23);
        label.setFont(font);
        jPanel.add(label,BorderLayout.CENTER);
        this.add(jPanel);
        this.setVisible(true);
    }

}
