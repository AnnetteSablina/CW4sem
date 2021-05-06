package com.annette.cw.ditch;

public class User {
    public Integer id;
    public String username;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                '}';
    }
}
