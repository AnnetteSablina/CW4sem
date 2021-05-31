package com.annette.cw.entity;

import lombok.Data;

import java.time.Instant;

@Data
public class DecisionRecord {
    private Integer waldCriterion;
    private Integer savageCriterion;
    private Integer gurvitzCriterion;
    private Decision decision;
    private long id;
    private final Instant createdDate = Instant.now();
}
