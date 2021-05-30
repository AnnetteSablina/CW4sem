package com.annette.cw.utility;

import com.annette.cw.controller.Controller;
import com.annette.cw.entity.Decision;
import com.annette.cw.entity.User;
import com.annette.cw.service.Provider;
import com.annette.cw.view.ExceptionWindow;

public class Updater {
    public static void updateSelfUser() {
        Provider.getInstance().getUserByToken(new TokenChecker().readToken(), (Result<User> res) -> {
            if (res.getResult() == null) {
                ExceptionWindow.makeLabel(res, "Невозможно обновить пользователя");
            }
            Controller.getInstance().setSelfUser(res.getResult());
        });
    }

    public static void updateCurrentDecision(Decision decision) {
        Controller.getInstance().setChangeableDecision(decision);
        System.out.println(Controller.getInstance().getChangeableDecision());
    }
}

