package com.annette.cw.utility;

import com.annette.cw.entity.User;

public class Position {
    public static boolean isAdmin ( Result<User> position){
        return position.getResult().getRole().equals("ADMIN");
    }
}
