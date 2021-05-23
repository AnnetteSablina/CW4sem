package com.annette.cw.entity.dto;

import com.annette.cw.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class UserPayload {

    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private int organizationId;

    public UserPayload(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.organizationId = user.getOrganization().getId();
    }
}
