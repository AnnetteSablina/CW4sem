package com.annette.cw.view;

import com.annette.cw.entity.dto.AuthenticationResponse;
import com.annette.cw.service.Provider;
import com.annette.cw.utility.*;

import javax.swing.*;
import java.awt.*;

public class StartWindow {

    private static JPanel controlPanel; // для соединения всех частей кнопки + текст + поля
    private static JPanel grid = new JPanel(new GridLayout(3, 0));
    public static Window window = new Window();

    public static void startWindow() {
        grid.removeAll();


        grid.setBackground(new Color(120, 110, 255));

        /* Font + Authorization */
        // подпись "Авторизация"
        JLabel headerLabel = new JLabel("Авторизация", JLabel.CENTER);
        Font font = new Font(null, Font.PLAIN, 25);
        headerLabel.setFont(font);

        /*установка параметров для панели и */
        // для получения сообщений
        JLabel statusLabel = new JLabel("", JLabel.CENTER);

        controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        controlPanel.setBackground(new Color(120, 110, 255));


        grid.add(headerLabel);
        grid.add(controlPanel);
        grid.add(statusLabel);

        window.add(grid);
        window.setVisible(true);
        StartWindow.enterLogPass(window);
    }

    private static void enterLogPass(Window window) {
        JLabel nameLabel = new JLabel("Логин пользователя: ");
        JLabel passwordLabel = new JLabel("Пароль: ", JLabel.CENTER);

        final JTextField userText = new JTextField(7);
        final JPasswordField passwordText = new JPasswordField(7);
        passwordText.setEchoChar('*');

        JButton loginButton = new JButton("Войти");
        loginButton.addActionListener(e -> StartWindow.checkLogPass(userText, passwordText));
        controlPanel.add(nameLabel);
        controlPanel.add(userText);

        controlPanel.add(passwordLabel);
        controlPanel.add(passwordText);

        controlPanel.add(loginButton);
        window.setVisible(true);
    }


    private static void checkLogPass(JTextField userLogin, JPasswordField password) {
        Provider.getInstance().logIn(userLogin.getText(), new String(password.getPassword()),
                (Result<AuthenticationResponse> res) -> {
                    if (!res.isObjectExist()) {
                        JLabel error = new JLabel("Пользователь отсутствует", JLabel.CENTER);
                        new ExceptionWindow(error);
                        return;
                    }
                    if (res.isServerError()) {
                        JLabel error = new JLabel("Соединение с сервером отсутствует", JLabel.CENTER);
                        new ExceptionWindow(error);
                        return;
                    }
                    TokenChecker.writeToken(res.getResult().getAuthenticationToken());
                    ServiceProvider.getInstance().updateToken(res.getResult().getAuthenticationToken());
                    if (Position.isAdmin(res)) {
                        window.remove(grid);
                        AdminWindow.createAdminWindow(window);
                        return;
                    }
                    window.remove(grid);

                    //AdminWindow.createAdminWindow(window);//окно юзера

                }
        );

    }
}

    /*public static void newWindow(Window window) {
        window.remove(grid);
        JButton button = new JButton();
        button.setBounds(50, 50, 250, 150);
        button.addActionListener(e -> System.out.println("meow"));
        button.setText("Котик");
        button.setBackground(new Color(130, 240, 210));
        window.add(button);
        window.repaint();
    }*/

