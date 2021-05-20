package com.annette.cw.entity;


import com.annette.cw.entity.dto.OrganizationPayload;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.SneakyThrows;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class Organization implements Cloneable {
    private String name;
    private String type;
    private List<User> users = new ArrayList<>();
    private final int id;
    private Instant createdDate = Instant.now();
    private int employeeCount;

    @SneakyThrows
    public Organization(OrganizationPayload payload, int id, List<User> users) {
        this.name = payload.getName();
        this.type = payload.getType();
        this.id = id;
        this.users = new ArrayList<>(users.size()); //TODO сделай глубокое копирование
        for (User orgUsers : users) {
            this.users.add((User) orgUsers.clone());
        }
    }

    public int getEmployeeCount() {
        return users.size();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Organization clone = (Organization) super.clone();
        clone.users = new ArrayList<>(users.size());
        for (User orgUsers : users) {
            clone.users.add((User) orgUsers.clone());
        }
        return clone;
    }
}
