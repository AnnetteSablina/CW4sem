package com.annette.cw.view.decision;

import com.annette.cw.controller.Controller;
import com.annette.cw.entity.Decision;
import com.annette.cw.service.Provider;
import com.annette.cw.utility.Result;
import com.annette.cw.utility.Searcher;
import com.annette.cw.view.*;
import com.annette.cw.view.Window;
import com.annette.cw.view.utility.WindowFunction;
import com.annette.cw.view.utility.WindowUtil;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class ChooseDecisionWindow extends JFrame {
    private static JPanel panel = new JPanel();
    private static ArrayList<JComboBox<String>> decisionBox = new ArrayList<>();
    private static Map<Integer, String> decisionMap = new TreeMap<>();

    static {
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 90));
        panel.setBackground(new Color(120, 110, 255));
    }

    public ChooseDecisionWindow() {
        panel.removeAll();
        clearAll();
        this.setTitle("Выбор решения");
        this.setResizable(false);
        this.setSize(600, 400);
        drawUI();
        changeChooseUserUI();
        this.add(panel);
        this.setVisible(true);
    }

    private static void clearAll() {
        decisionBox.clear();
        decisionMap.clear();
    }

    public static JPanel getPanel() {
        return panel;
    }

    public static ArrayList<JComboBox<String>> getDecisionBox() {
        return decisionBox;
    }

    private void drawUI() {
        addComboBox();
        WindowUtil.addSmallWindowButton("Назад", e -> comeBack(), getPanel());
        WindowUtil.addSmallWindowButton("Подтвердить", e -> selectCurrentDecision(), getPanel());
    }

    private static void addComboBox() {
        JPanel compPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0));
        compPanel.setBackground(new Color(120, 110, 255));
        WindowUtil.addLabel("Выберите решение", compPanel);
        JComboBox<String> users = new JComboBox<>();
        users.setPreferredSize(new Dimension(250, 30));
        users.setBackground(new Color(130, 240, 210));
        users.setEditable(false);
        compPanel.add(users);
        decisionBox.add(users);
        ChooseDecisionWindow.panel.add(compPanel);
    }

    private static void updateComboBox(List<Decision> decisionList) {
        if (decisionList != null) {
            for (Decision decision : decisionList) {
                decisionMap.put(decision.getId(), decision.getName() + " " + decision.getUser().getUsername());
            }
            decisionMap.put(-1, "Принять новое решение");
            insertValues();
            decisionBox.get(0).setSelectedIndex(0);
        } else ExceptionWindow.makeLabel("Список решений пуст");
    }

    private static void changeChooseUserUI() {
        Provider.getInstance().getDecisions((Result<List<Decision>> res)
                -> {
            if (res.getResult() == null) {
                ExceptionWindow.makeLabel("Невозможно загрузить решения");
                WindowFunction.returnIntoUserWindow(getPanel());
                return;
            }
            Controller.getInstance().setDecisionList(res.getResult());
            System.out.println(res.getResult());
            updateComboBox(res.getResult());
        });

    }

    private void selectCurrentDecision() {
        int decisionId = Searcher.findObjByID(decisionMap, decisionBox.get(0).getSelectedIndex());
        Controller.getInstance().setChangeableDecision(Searcher.findDecisionById(decisionId));
        this.dispose();
        Window.getWindow().remove(UserWindow.getPanel());
        if (Controller.getInstance().getChangeableDecision() == null) {
            new DecisionParamWindow(0);
        } else {
            new QuestionWindow();
        }
    }

    private static void insertValues() {
        for (Map.Entry<Integer, String> map : decisionMap.entrySet()) {
            decisionBox.get(0).insertItemAt(map.getValue(), decisionBox.get(0).getItemCount());
        }
    }

    private void comeBack() {
        this.dispose();
        WindowFunction.returnIntoUserWindow(panel);
    }

}