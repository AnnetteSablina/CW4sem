package com.annette.cw.view.utility.validation;

import com.annette.cw.view.ExceptionWindow;

public class AuthorizationValidator {
    public static boolean isAuthCorrect(String login,String pass){
        if (!login.matches("[\\w-.]{3,31}")) {
            ExceptionWindow.makeLabel("Неверный формат логина");
            return false;
        }
        if (!pass.matches("[\\x21-\\x7E]{8,64}|^$")){
            ExceptionWindow.makeLabel("Неверный формат пароля");
            return false;
        }
        return true;
    }
}
