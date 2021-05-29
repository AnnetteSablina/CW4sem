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
    private Double pessimismCoefficient;
    private Integer userId;
    private Instant createdDate;

   public DecisionPayload(Decision decision) {
        this.name = decision.getName();
        this.description = decision.getDescription();
        this.strategyList = decision.getStrategyList();
        this.natureStatesCount = decision.getNatureStatesCount();
        this.userId = decision.getUser().getId();
        this.createdDate = decision.getCreatedDate();
        this.pessimismCoefficient = decision.getPessimismCoefficient();
    }

}
