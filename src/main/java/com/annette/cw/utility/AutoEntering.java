package com.annette.cw.utility;

import com.annette.cw.controller.Controller;
import com.annette.cw.entity.User;
import com.annette.cw.service.Provider;
import com.annette.cw.view.StartWindow;
import com.annette.cw.view.UserWindow;

public class AutoEntering {
    public static void autoEntering(){
        if (new TokenChecker().isFileEmpty()) StartWindow.startWindow();
        else Provider.getInstance().getUserByToken(new TokenChecker().readToken(),(Result<User> res) -> {
            if (res.getResult() == null){
                new TokenChecker().clearFile();
                StartWindow.startWindow();
                return;
            }
            Controller.getInstance().setSelfUser(res.getResult());
            if (Position.isAdmin(res)) UserWindow.createAdminWindow();
            else UserWindow.createUserWindow();
        });
    }
}
