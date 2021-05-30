package com.annette.cw.view.utility;

import com.annette.cw.controller.Controller;
import com.annette.cw.view.ExceptionWindow;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;

public class UserChecker {

    public static boolean checkUser(ArrayList<JTextField> fields) {
        if (!fields.get(0).getText().matches("[\\w-.]{3,31}")) {
            ExceptionWindow.makeLabel("Неверно введен логин");
            return false;
        }
        if (!fields.get(1).getText().matches("[\\w-.]{3,16}@[\\w]{2,10}.[\\D]{2,5}")) {
            ExceptionWindow.makeLabel("Неверно введен почтовый адрес");
            return false;
        }
        if (!fields.get(2).getText().matches("[A-ZА-Я][a-zа-я]{1,30}")) {
            ExceptionWindow.makeLabel("Имя имеет неверный формат");
            return false;
        }
        if (!fields.get(3).getText().matches("[A-ZА-Я][a-zа-я]{1,30}")) {
            ExceptionWindow.makeLabel("Фамилия имеет неверный формат");
            return false;
        }
        return true;
    }

    public static boolean isCurrentUserNameChanged(ArrayList<JTextField> fields) {
        return !Controller.getInstance().getSelfUser().getUsername().equals(fields.get(0).getText());

    }

    public static boolean checkUserPass(ArrayList<JPasswordField> passwordFields) {
        if (!new String(passwordFields.get(0).getPassword()).matches("[\\x21-\\x7E]{8,64}|^$")) {
            ExceptionWindow.makeLabel("Неверный формат пароля");
            return false;
        }
        if (!Arrays.equals(passwordFields.get(0).getPassword(), passwordFields.get(1).getPassword())) {
            ExceptionWindow.makeLabel("Пароли не совпадают");
            return false;
        }
        return true;
    }
}
