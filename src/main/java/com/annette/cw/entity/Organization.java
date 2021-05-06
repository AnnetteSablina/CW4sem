package com.annette.cw.entity;


import com.annette.cw.entity.dto.OrganizationPayload;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class Organization  implements Cloneable{
    private String name;
    private String type;
    private List<User> users = new ArrayList<>();
    private final int id;

    Organization(OrganizationPayload payload, int id, List<User> users) {
        this.name = payload.getName();
        this.type = payload.getType();
        this.id = id;
        this.users = users; //TODO сделай глубокое копирование
    }

   @Override
    protected Object clone() throws CloneNotSupportedException {
        Organization clone = (Organization) super.clone();
        clone.users = new ArrayList<>(users.size());
        for (User orgUsers : users){
            clone.users.add((User)orgUsers.clone());
        }
        return clone;
    }
}
