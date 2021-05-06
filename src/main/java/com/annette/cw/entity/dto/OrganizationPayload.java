package com.annette.cw.entity.dto;

import com.annette.cw.entity.Organization;
import lombok.Data;

@Data
public class OrganizationPayload {

    private String name;
    private String type;

    OrganizationPayload(Organization organization) {
        this.name = organization.getName();
        this.type = organization.getType();
    }
}
