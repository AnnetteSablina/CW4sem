package com.annette.cw.view.utility;

import com.annette.cw.view.ExceptionWindow;

import javax.swing.*;
import java.util.ArrayList;

public class UserChecker {

    public static void checkUser(ArrayList<JTextField> fields, ArrayList<JPasswordField> passwordFields) {
        if (!fields.get(0).getText().matches("[\\w-.]{3,31}")) ExceptionWindow.makeLabel("Неверно введен логин");
        if (!fields.get(1).getText().matches("[\\w-.]{3,16}@[\\w]{3,10}.[\\D]{2,5}")) {
            ExceptionWindow.makeLabel("Неверно введен почтовый адрес");
        }


    }
}
