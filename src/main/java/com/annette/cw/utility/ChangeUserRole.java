package com.annette.cw.utility;

import com.annette.cw.controller.Controller;
import com.annette.cw.entity.User;
import com.annette.cw.service.Provider;
import com.annette.cw.view.ExceptionWindow;
import com.annette.cw.view.UserWindow;
import com.annette.cw.view.utility.WindowFunction;

public class ChangeUserRole {
    public static void changeRole() {
        System.out.println(Controller.getInstance().getChangeableUser().getId());
        Provider.getInstance().promoteUser(Controller.getInstance().getChangeableUser().getId(), (Result<User> res) -> {
            if (res.getCode() == 404) {
                ExceptionWindow.makeLabel(res, "Ошибка");
            }
            if (res.getCode() == 200) {
                WindowFunction.returnIntoUserWindow(UserWindow.getPanel());
            }
        });
    }

}
