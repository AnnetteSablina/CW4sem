package com.annette.cw.view.utility;

import com.annette.cw.controller.Controller;
import com.annette.cw.utility.TokenChecker;
import com.annette.cw.view.*;

public class UserWindowNavigation {
    public static void logInUser() {
        UserWindow.getPanel().removeAll();
        Window.getWindow().remove(UserWindow.getPanel());
        LogInWindow.createLogInWindow();
    }

    public static void returnToStartWindow() {
        UserWindow.getPanel().removeAll();
        Window.getWindow().remove(UserWindow.getPanel());
        TokenChecker.clearFile();
        Controller.getInstance().clear();
        StartWindow.startWindow();
    }

    public static void changeCurrentUserInfo() {
        UserWindow.getPanel().removeAll();
        Window.getWindow().remove(UserWindow.getPanel());
        LogInWindow.createCurrentUserWindow();
    }

    public static void changeUserInfo() {
        new ChooseUserWindow();
    }

    public static void addOrganization() {
        UserWindow.getPanel().removeAll();
        Window.getWindow().remove(UserWindow.getPanel());
        System.out.println("lol");
        OrganizationWindow.createAddOrgWindow();
    }

    public static void changeOrganization() {
        UserWindow.getPanel().removeAll();
        Window.getWindow().remove(UserWindow.getPanel());
        OrganizationWindow.changeOrgWindow();
    }

    public static void showEmployeeCount() {

    }

    public static void makeDecision() {
        if (Controller.getInstance().getSelfUser().getOrganization() == null) {
            ExceptionWindow.makeLabel("Вы не можете принять решение будучи безработным");
            return;
        }
        new ChooseDecisionWindow();
    }
}
