package com.annette.cw.view.utility;

import com.annette.cw.controller.Controller;

import java.util.ArrayList;
import java.util.List;

public class TableDataCreator {
    private static List<List<String>> data = new ArrayList<>();
    private static List<String> colunmNames = new ArrayList<>();
    private static List<String> rowData = new ArrayList<>();

    public static String[] createColumns() {
        colunmNames.clear();
        colunmNames.add("Название стратегии");
        for (int i = 0; i < Controller.getInstance().getChangeableDecision().getNatureStatesCount(); i++) {
            int k = i;
            colunmNames.add("П " + (++k));
        }
        String[] arr = new String[colunmNames.size()];
        for (int i = 0; i < arr.length; ++i)
            arr[i] = colunmNames.get(i);
        return arr;
    }

    public static String[][] createData() {
        data.clear();
        rowData.clear();
        for (int i = 0; i < Controller.getInstance().getChangeableDecision().getStrategyList().size(); i++) {
            rowData.add(Controller.getInstance().getChangeableDecision().getStrategyList().get(i));
            for (int k = 0; k < Controller.getInstance().getChangeableDecision().getNatureStatesCount(); k++) {
                rowData.add(null);
            }
            data.add(rowData);
            rowData = new ArrayList<>();
        }
        String[][] arr = new String[data.size()][data.get(0).size()];
        for (int i = 0; i < data.size(); ++i)
            for (int j = 0; j < data.get(i).size(); j++)
                arr[i][j] = data.get(i).get(j);
        return arr;
    }
}
