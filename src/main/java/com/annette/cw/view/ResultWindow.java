package com.annette.cw.view;

import com.annette.cw.controller.Controller;
import com.annette.cw.entity.DecisionRecord;
import com.annette.cw.utility.Result;
import com.annette.cw.view.utility.WindowUtil;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ResultWindow extends JFrame {
    private static JPanel panel = new JPanel();

    static {
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 25));
        panel.setBackground(new Color(120, 110, 255));
    }

    public static JPanel getPanel() {
        return panel;
    }

    public ResultWindow() {
        panel.removeAll();
        this.setTitle("Результат");
        this.setResizable(false);
        this.setSize(600, 400);
        this.add(panel);
        this.setVisible(true);
    }

    public void createResult(Result<DecisionRecord> res) {
        int waldCriterion = res.getResult().getWaldCriterion();
        int savageCriterion = res.getResult().getSavageCriterion();
        int gurvitzCrioterion = res.getResult().getGurvitzCriterion();
        List<Integer> popularity = new ArrayList<>();
        List<String> strategyList = Controller.getInstance().getChangeableDecision().getStrategyList();
        int size = Controller.getInstance().getChangeableDecision().getStrategyList().size();
        System.out.println(size);
        for (int i = 0; i < size; i++) {
            if (i == waldCriterion) {
                addText("По критерию Вальда выберите " + strategyList.get(i));
                popularity.add(i);
            }
            if (i == savageCriterion) {
                addText("По критерию Сэвиджа выберите " + strategyList.get(i));
                popularity.add(i);
            }
            if (i == gurvitzCrioterion) {
                addText("По критерию Гурвица выберите " + strategyList.get(i));
                popularity.add(i);
            }
        }

        addText("Итого: " + strategyList.get(mostCommon(popularity)));
        WindowUtil.addSmallWindowButton("Окей", e -> goToUserMenu(), panel);
    }

    private static void addText(String description) {
        JPanel compPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        compPanel.setBackground(new Color(120, 110, 255));
        JLabel nameLabel = new JLabel(description);
        nameLabel.setPreferredSize(new Dimension(450, 30));
        compPanel.add(nameLabel);
        ResultWindow.panel.add(compPanel);
    }

    public static Integer mostCommon(List<Integer> list) {
        Integer maxVal = list.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream().max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey).orElse(list.get(0));
        return maxVal;
    }

    private void goToUserMenu() {
        this.dispose();
        TableWindow.goToUserMenu();
    }

}
