package com.annette.cw.view;

import com.annette.cw.controller.Controller;
import com.annette.cw.entity.User;
import com.annette.cw.entity.dto.AuthenticationResponse;
import com.annette.cw.service.Provider;
import com.annette.cw.utility.*;

import javax.swing.*;
import java.awt.*;

public class StartWindow {

    private static JPanel controlPanel; // для соединения всех частей кнопки + текст + поля
    private static JPanel grid = new JPanel(new GridLayout(3, 0));

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

        Window.getWindow().add(grid);
        Window.getWindow().setVisible(true);
        StartWindow.enterLogPass();
    }

    private static void enterLogPass() {
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
        Window.getWindow().setVisible(true);
    }


    private static void checkLogPass(JTextField userLogin, JPasswordField password) {
        Provider.getInstance().logIn(userLogin.getText(), new String(password.getPassword()),
                (Result<AuthenticationResponse> res) -> {
                   ExceptionWindow.makeLabel(res,"Такой комбинации логина и пароля не сущетвует");
                    TokenChecker.writeToken(res.getResult().getAuthenticationToken());
                    ServiceProvider.getInstance().updateToken(res.getResult().getAuthenticationToken());
                    Provider.getInstance().getUserByToken(res.getResult().getAuthenticationToken(), (Result<User> user) -> {
                        Controller.getInstance().setSelfUser(user.getResult());
                        if (Position.isAdmin(user)) {
                            Window.getWindow().remove(grid);
                            UserWindow.createAdminWindow();
                        }

                    });

                    Window.getWindow().remove(grid);

                    //UserWindow.createAdminWindow(window);//окно юзера

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

