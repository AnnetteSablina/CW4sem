package com.annette.cw.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements Cloneable{

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
}
