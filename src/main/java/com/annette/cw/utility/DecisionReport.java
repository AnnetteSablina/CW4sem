package com.annette.cw.utility;

import com.annette.cw.entity.Decision;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DecisionReport {
    Integer totalRecords;
    Integer strategies;
    Integer natureStates;
    List<Integer> waldFrequency;
    List<Integer> savageFrequency;
    List<Integer> gurvitzFrequency;
    List<Integer> totalFrequency;
    String bestChoiceWald;
    String bestChoiceSavage;
    String bestChoiceGurvitz;
    String bestChoiceTotal;

    public DecisionReport(Decision d) {
        this.totalRecords = d.getRecords().size();
        this.strategies = d.getStrategyList().size();
        this.natureStates = d.getNatureStatesCount();
        this.waldFrequency = waldFrequencyCount(d);
        this.savageFrequency = savageCount(d);
        this.gurvitzFrequency = gurvitzCount(d);
        this.totalFrequency = totalCount(d);
        this.bestChoiceWald = d.getStrategyList().get(waldFrequency.indexOf(waldFrequency.stream().max(Integer::compareTo).get()));
        this.bestChoiceSavage = d.getStrategyList().get(savageFrequency.indexOf(savageFrequency.stream().max(Integer::compareTo).get()));
        this.bestChoiceGurvitz = d.getStrategyList().get(gurvitzFrequency.indexOf(gurvitzFrequency.stream().max(Integer::compareTo).get()));
        this.bestChoiceTotal = d.getStrategyList().get(totalFrequency.indexOf(totalFrequency.stream().max(Integer::compareTo).get()));
    }

    private List<Integer> waldFrequencyCount(Decision d) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < d.getStrategyList().size(); i++) {
            list.add(counterWaldCrit(i, d));
        }
        return list;
    }

    private List<Integer> savageCount(Decision d) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < d.getStrategyList().size(); i++) {
            list.add(counterSavageCrit(i, d));
        }
        return list;
    }

    private List<Integer> gurvitzCount(Decision d) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < d.getStrategyList().size(); i++) {
            list.add(counterGurvitzCrit(i, d));
        }
        return list;
    }

    private List<Integer> totalCount(Decision d) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < d.getStrategyList().size(); i++) {
            int sum = savageFrequency.get(i) + waldFrequency.get(i) + gurvitzFrequency.get(i);
            list.add(sum);
        }
        return list;
    }

    private int counterWaldCrit(final Integer i, Decision d) {
        return (int) d.getRecords().stream().filter(it -> it.getWaldCriterion().equals(i)).count();
    }

    private int counterSavageCrit(final Integer i, Decision d) {
        return (int) d.getRecords().stream().filter(it -> it.getSavageCriterion().equals(i)).count();
    }

    private int counterGurvitzCrit(final Integer i, Decision d) {
        return (int) d.getRecords().stream().filter(it -> it.getGurvitzCriterion().equals(i)).count();
    }
}


