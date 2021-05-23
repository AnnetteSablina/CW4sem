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
    private final int id;
    private Instant createdDate = Instant.now();
    private int employeeCount;

    @SneakyThrows
    public Organization(OrganizationPayload payload, int id) {
        this.name = payload.getName();
        this.type = payload.getType();
        this.id = id;
    }
}
