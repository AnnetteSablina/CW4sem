package com.annette.cw.utility;

import com.annette.cw.entity.User;
import com.annette.cw.service.Provider;
import com.annette.cw.view.UserWindow;
import com.annette.cw.view.ExceptionWindow;
import com.annette.cw.view.StartWindow;

import javax.swing.*;

public class AutoEntering {
    public static void autoEntering(){
        if (TokenChecker.isFileEmpty()) StartWindow.startWindow();
        else Provider.getInstance().getUserByToken(TokenChecker.readToken(),(Result<User> res)
                -> {
            JLabel error = null;
            if (!res.isObjectExist()) {
                error = new JLabel("Пользователь отсутствует", JLabel.CENTER);
                new ExceptionWindow(error);
                TokenChecker.clearFile();
                StartWindow.startWindow();
                return;
            }
            if (res.isServerError()) {
                error = new JLabel("Соединение с сервером отсутствует", JLabel.CENTER);
                new ExceptionWindow(error);
                TokenChecker.clearFile();
                StartWindow.startWindow();
                return;
            }
            if (Position.isAdmin(res)) {
            UserWindow.createAdminWindow();}
        });
    }
}
