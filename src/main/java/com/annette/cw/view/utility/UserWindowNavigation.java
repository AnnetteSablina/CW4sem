package com.annette.cw.view.utility;

import com.annette.cw.controller.Controller;
import com.annette.cw.utility.ServiceProvider;
import com.annette.cw.utility.TokenChecker;
import com.annette.cw.view.*;
import com.annette.cw.view.decision.ChooseDecisionWindow;

public class UserWindowNavigation {
    public static void logInUser() {
        UserWindow.getPanel().removeAll();
        Window.getWindow().remove(UserWindow.getPanel());
        LogInWindow.createLogInWindow();
    }

    public static void returnToStartWindow() {
        UserWindow.getPanel().removeAll();
        Window.getWindow().remove(UserWindow.getPanel());
        new TokenChecker().clearFile();
        ServiceProvider.getInstance().updateToken(null);
        Controller.getInstance().setSelfUser(null);
        StartWindow.startWindow();
    }

    public static void changeCurrentUserInfo() {
        UserWindow.getPanel().removeAll();
        Window.getWindow().remove(UserWindow.getPanel());
        LogInWindow.createCurrentUserWindow();
    }

    public static void changeUserInfo() {
        UserWindow.getPanel().removeAll();
        Window.getWindow().remove(UserWindow.getPanel());
        new ChooseUserWindow();
    }

    public static void addOrganization() {
        UserWindow.getPanel().removeAll();
        Window.getWindow().remove(UserWindow.getPanel());
        OrganizationWindow.createAddOrgWindow();
    }

    public static void changeOrganization() {
        if (Controller.getInstance().getSelfUser().getOrganization() == null) {
            ExceptionWindow.makeLabel("Вас нет ни в одной организации");
            return;
        } else {
            UserWindow.getPanel().removeAll();
            Window.getWindow().remove(UserWindow.getPanel());
            UserWindow.getPanel().removeAll();
            Window.getWindow().remove(UserWindow.getPanel());
            OrganizationWindow.changeOrgWindow();
        }
    }

    public static void showEmployeeCount() {

    }

    public static void createReport() {
        UserWindow.getPanel().removeAll();
        Window.getWindow().remove(UserWindow.getPanel());
    }

    public static void makeDecision() {
        if (Controller.getInstance().getSelfUser().getOrganization() == null) {
            ExceptionWindow.makeLabel("Вы не можете принять решение будучи безработным");
            return;
        }
        new ChooseDecisionWindow();
    }
}
