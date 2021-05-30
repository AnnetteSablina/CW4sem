package com.annette.cw.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StatisticDecision {

    private int id;
    private String name;
    private User user;
    private String data;

}
