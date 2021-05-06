package com.annette.cw.view;


import com.annette.cw.controller.UserService;
import com.annette.cw.utility.ServiceProvider;
import javax.swing.*;
import java.awt.*;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Window extends JFrame {
    private JLabel headerLabel;
    private JLabel statusLabel;
    private JPanel controlPanel;

    Window() {
        this.setTitle("CW 4 sem");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(800, 600);
        this.setVisible(true);
        ImageIcon image = new ImageIcon("logo.png");
        this.setIconImage(image.getImage().getScaledInstance(100, 100, 50));
        this.getContentPane().setBackground(new Color(120, 110, 255));
        this.setLayout(new GridLayout(3,1));

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                System.exit(0);
            }
        });
        headerLabel = new JLabel("", JLabel.CENTER);
        statusLabel = new JLabel("", JLabel.CENTER);
        statusLabel.setSize(350, 100);

        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());
        controlPanel.setBackground(new Color(120,110,255));


        this.add(headerLabel);
        this.add(controlPanel);
        this.add(statusLabel);
        this.setVisible(true);
    }

    public void showPasswordFieldDemo() {
        headerLabel.setText("Authorization");
        Font font = new Font(null, Font.PLAIN,25);
        headerLabel.setFont(font);
        JLabel namelabel = new JLabel("User Login: ");
        JLabel passwordLabel = new JLabel("Password: ",JLabel.CENTER);
        JLabel s = new JLabel("           ");
        JLabel w = new JLabel("     ");
        final JTextField userText = new JTextField(7);
        final JPasswordField passwordText = new JPasswordField(7);
        passwordText.setEchoChar('*');

       JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> {
           /* String data = "Username " + userText.getText();
            data += ", Password: " + new String(passwordText.getPassword());
            statusLabel.setText(data);*/
            ServiceProvider.getInstance().getService().execute(()->{
                UserService userService = ServiceProvider.getInstance().getUserService().;
                //String token = service.login(request).execute().body().authenticationToken;});
        });
        controlPanel.add(namelabel);
        controlPanel.add(userText);
        controlPanel.add(s);
        controlPanel.add(passwordLabel);
        controlPanel.add(passwordText);
        controlPanel.add(w);
        controlPanel.add(loginButton);
        this.setVisible(true);
    }


    public void drawButton() {

    }

    public static void main(String... args) {
        Window w = new Window();
        w.showPasswordFieldDemo();

    }
}
