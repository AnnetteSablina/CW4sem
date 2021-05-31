package com.annette.cw.view.utility.validation;

import com.annette.cw.view.ExceptionWindow;

import javax.swing.*;
import java.util.ArrayList;

public class DecisionValidator {
    public static boolean isDecisionValid(ArrayList<JTextField> fields) {
        if (!fields.get(0).getText().matches("[a-zA-Zа-яА-Я\\s\\d]{3,63}")) {
            ExceptionWindow.makeLabel("Не верный формат названия");
            return false;
        }
        try {
            if (!(Double.parseDouble(fields.get(1).getText()) >= 0 && Double.parseDouble(fields.get(1).getText()) <= 1)) {
                ExceptionWindow.makeLabel("Коэффициент имеет значение от 0 до 1");
                return false;
            }
        } catch (NumberFormatException e) {
            ExceptionWindow.makeLabel("Неверный формат коэффициента");
            return false;
        }
        return true;
    }
}
