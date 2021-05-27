package com.annette.cw.entity;

import com.annette.cw.entity.dto.DecisionPayload;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
public class Decision {

    private String name;
    private String description;
    private List<String> strategyList;
    private List<DecisionRecord> records;
    private User user;
    private Instant createdDate;
    private int natureStatesCount;
    private double pessimismCoefficient;
    private final int id;

    Decision(DecisionPayload payload, User user, int id) {
        this.name = payload.getName();
        this.description = payload.getDescription();
        this.strategyList = payload.getStrategyList();
        this.records = Collections.emptyList();
        this.user = user;
        this.createdDate = payload.getCreatedDate();
        this.natureStatesCount = payload.getNatureStatesCount();
        this.id = id;
        this.pessimismCoefficient = payload.getPessimismCoefficient();
    }

}
