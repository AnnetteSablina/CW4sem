package com.annette.cw.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@Builder
public class User implements Cloneable {

    private Integer id;
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private Organization organization;
    private String role;
    private Instant createdDate;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public User(Integer id, String username, String password, String email, String firstName, String lastName,
                Organization organization, String role, Instant createdDate) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.organization = organization;
        this.role = role;
        this.createdDate = createdDate;
    }
}
