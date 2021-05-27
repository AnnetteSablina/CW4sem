package com.annette.cw.view;

import com.annette.cw.utility.Result;

import javax.swing.*;
import java.awt.*;

public class ExceptionWindow extends JFrame {

    public static <T> void makeLabel(Result<T> res, String text) {

        if (res.isServerError()) {
            JLabel error = new JLabel("Соединение с сервером отсутствует", JLabel.CENTER);
            new ExceptionWindow(error);
            return;
        }
        if (!res.isObjectExist()) {
            JLabel error = new JLabel(text, JLabel.CENTER);
            new ExceptionWindow(error);

        }

    }

    public static void makeLabel(String text) {
        JLabel error = new JLabel(text, JLabel.CENTER);
        new ExceptionWindow(error);
    }

    public ExceptionWindow(JLabel label) {
        this.setTitle("Error");
        this.setResizable(false);
        this.setSize(600, 300);
        JPanel jPanel = new JPanel(new BorderLayout());
        jPanel.setBackground(new Color(255, 0, 50));
        Font font = new Font(null, Font.PLAIN, 23);
        label.setFont(font);
        jPanel.add(label, BorderLayout.CENTER);
        this.add(jPanel);
        this.setVisible(true);
    }

}
