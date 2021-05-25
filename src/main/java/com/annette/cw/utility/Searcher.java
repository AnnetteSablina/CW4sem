package com.annette.cw.utility;

import com.annette.cw.controller.Controller;
import com.annette.cw.entity.User;

import java.util.List;
import java.util.Map;

public class Searcher {
    public static int findObjByID(Map<Integer, String> organization, int pos){
        int i = 0;
        for (Map.Entry<Integer, String> map : organization.entrySet()) {
            if (i == pos) return map.getKey();
            i++;
        }
        return 0;
    }
    public static User findUserBuID(int id){
        List<User> users = Controller.getInstance().getUserList();
        for (User user:users){
            if (user.getId() == id) return user;
        }
        return null;
    }
}
