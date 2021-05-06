package com.annette.cw.entity;

import com.annette.cw.entity.dto.DecisionPayload;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Builder
@Data
public class Decision {

    private String name;
    private String description;
    private List<String> strategyList;
    private Organization organization;
    private Instant createdDate;
    private int natureStatesCount;
    private final int id ;
    Decision (DecisionPayload payload, Organization organization, int id){
        this.name = payload.getName();
        this.description = payload.getDescription();
        this.strategyList = payload.getStrategyList();//TODO сделай глубокое копирование
        this.organization = organization;
        this.createdDate = payload.getCreatedDate();
        this.natureStatesCount = payload.getNatureStatesCount();
        this.id = id;
    }
}
