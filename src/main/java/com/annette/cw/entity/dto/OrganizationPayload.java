package com.annette.cw.entity.dto;

import com.annette.cw.entity.Organization;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrganizationPayload {

    private String name;
    private String type;

   public OrganizationPayload(Organization organization) {
        this.name = organization.getName();
        this.type = organization.getType();
    }
}
