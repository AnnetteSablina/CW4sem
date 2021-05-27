package com.annette.cw.utility;

import com.annette.cw.controller.Controller;
import com.annette.cw.entity.User;
import com.annette.cw.service.Provider;
import com.annette.cw.view.ExceptionWindow;

public class Update {
    public static void updateSelfUser() {
        Provider.getInstance().getUserByToken(TokenChecker.readToken(), (Result<User> res) -> {
            if (res.getResult() == null) {
                ExceptionWindow.makeLabel(res,"Невозможно обновить пользователя");
            }
            System.out.println(res.getResult());
            Controller.getInstance().setSelfUser(res.getResult());
        });
    }
}

