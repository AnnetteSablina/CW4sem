package com.annette.cw.view.utility.validation;

import com.annette.cw.view.ExceptionWindow;

import javax.swing.*;
import java.util.ArrayList;

public class OrganizationValidator {
    public static boolean isOrgInfoValid(ArrayList<JTextField> orgInfo) {
        if (!orgInfo.get(0).getText().matches("[a-zA-Zа-яА-Я\\s\\d]{2,30}")) {
            ExceptionWindow.makeLabel("Название организации не соответсвует стандарту");
            return false;
        }
        if (!orgInfo.get(1).getText().matches("[a-zA-Zа-яА-Я\\s]{2,30}")) {
            ExceptionWindow.makeLabel("Описание организации имеет недопустимые символы");
            return false;
        }
        return true;
    }
}
