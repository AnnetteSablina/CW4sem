package com.annette.cw.view;

import com.annette.cw.utility.AutoEntering;

import javax.swing.*;
import java.awt.*;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Objects;

public class Window extends JFrame {
    private volatile static Window window;

    private Window() {
        this.setTitle("CW 4 sem");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(800, 600);
        this.setVisible(true);
        ImageIcon image = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("logo.png")));
        this.setIconImage(image.getImage().getScaledInstance(100, 100, 50));
        this.getContentPane().setBackground(new Color(120, 110, 255));
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });

    }

    public static Window getWindow() {
        if (window == null) {
            synchronized (Window.class) {
                if (window == null) {
                    window = new Window();
                }
            }
        }
        return window;
    }


    public static void main(String... args) {
        AutoEntering.autoEntering();
        //StartWindow.startWindow();
        //UserWindow.createAdminWindow();
    }

}
