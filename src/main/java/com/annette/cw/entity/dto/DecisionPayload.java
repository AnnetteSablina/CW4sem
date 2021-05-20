package com.annette.cw.entity.dto;

import com.annette.cw.entity.Decision;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor

public class DecisionPayload {
    private String name;
    private String description;
    private List<String> strategyList;
    private int natureStatesCount;
    private int organizationId;
    private Instant createdDate;
    private double pessimismCoefficient;

    DecisionPayload(Decision decision) {
        this.name = decision.getName();
        this.description = decision.getDescription();
        this.strategyList = decision.getStrategyList(); //TODO сделай глубокое копирование
        this.natureStatesCount = decision.getNatureStatesCount();
        this.organizationId = decision.getId();
        this.createdDate = decision.getCreatedDate();
        this.pessimismCoefficient = decision.getPessimismCoefficient();
    }

}
