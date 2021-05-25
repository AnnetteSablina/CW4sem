package com.annette.cw.view.utility;

import com.annette.cw.controller.Controller;
import com.annette.cw.view.UserWindow;
import com.annette.cw.view.Window;

import javax.swing.*;

public class WindowFunction {
    public static void returnIntoUserWindow(JPanel panel){
        if (Controller.getInstance().getSelfUser().getRole().equals("ADMIN")){
            Window.getWindow().remove(panel);
            UserWindow.createAdminWindow();
        }
        else {
            Window.getWindow().remove(panel);
            UserWindow.createUserWindow();
        }
    }
    public static void util(JPanel panel){
        Window.getWindow().add(panel);
        Window.getWindow().repaint();
        Window.getWindow().setVisible(true);
    }
}
