package com.annette.cw.entity.dto;

import com.annette.cw.entity.Decision;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
@AllArgsConstructor
public class DecisionPayload {
    private String name;
    private String description;
    private List<String> strategyList;
    private Integer natureStatesCount;
    private double pessimismCoefficient;
    private int id;
    private Instant createdDate;

   public DecisionPayload(Decision decision) {
        this.name = decision.getName();
        this.description = decision.getDescription();
        this.strategyList = decision.getStrategyList();
        this.natureStatesCount = decision.getNatureStatesCount();
        this.id = decision.getId();
        this.createdDate = decision.getCreatedDate();
        this.pessimismCoefficient = decision.getPessimismCoefficient();
    }

}
