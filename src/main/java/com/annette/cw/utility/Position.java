package com.annette.cw.utility;


import com.annette.cw.entity.dto.AuthenticationResponse;

public class Position {
    public static boolean isAdmin ( Result<AuthenticationResponse> position){
        return position.getResult().getRole().equals("ADMIN");
    }
}
