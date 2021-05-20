package com.annette.cw.entity;

import com.annette.cw.entity.Decision;
import lombok.Data;

import java.time.Instant;

@Data
public class DecisionRecord {
    private int waldCriterion;
    private int savageCriterion;
    private int gurvitzCriterion;
    private Decision decision;
    private long id;
    private final Instant createdDate= Instant.now();



}
