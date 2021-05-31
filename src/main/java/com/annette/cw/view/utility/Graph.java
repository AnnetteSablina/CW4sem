package com.annette.cw.view.utility;

import com.annette.cw.controller.Controller;
import com.annette.cw.utility.DecisionReport;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.title.TextTitle;
import org.jfree.chart.ui.HorizontalAlignment;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import javax.swing.*;
import java.awt.*;

public class Graph extends JFrame {

    private static final long serialVersionUID = 1L;
    private PieDataset dataset;
    private JFreeChart chart;

    public Graph(String title, DecisionReport report) {
        super(title);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dataset = createPieDataset(report);
        chart = createChart(dataset);
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(600, 400));
        setContentPane(chartPanel);
    }

    private static PieDataset createPieDataset(DecisionReport report) {
        DefaultPieDataset dataset = new DefaultPieDataset();
        for (int i = 0; i < Controller.getInstance().getChangeableDecision().getStrategyList().size(); i++)
            dataset.setValue(Controller.getInstance().getChangeableDecision().getStrategyList().get(i),
                    ((report.getTotalFrequency().get(i))));
        return dataset;
    }

    private JFreeChart createChart(PieDataset dataset) {
        JFreeChart chart = ChartFactory.createPieChart(
                Controller.getInstance().getChangeableDecision().getName(), dataset, true, true, false
        );

        chart.setBackgroundPaint(new GradientPaint(new Point(0, 0),
                new Color(20, 20, 20),
                new Point(400, 200),
                Color.DARK_GRAY));
        TextTitle t = chart.getTitle();
        t.setHorizontalAlignment(HorizontalAlignment.LEFT);
        t.setPaint(new Color(240, 240, 240));
        t.setFont(new Font("Arial", Font.BOLD, 18));

        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setBackgroundPaint(null);
        plot.setInteriorGap(0.04);
        plot.setOutlineVisible(false);

        plot.setOutlinePaint(Color.WHITE);
        plot.setSectionOutlinesVisible(true);
        plot.setOutlineStroke((new BasicStroke(2.0f)));

        // Настройка меток названий секций
        plot.setLabelFont(new Font("Courier New", Font.BOLD, 15));
        plot.setLabelLinkPaint(Color.WHITE);
        plot.setLabelLinkStroke(new BasicStroke(2.0f));
        plot.setLabelOutlineStroke(null);
        plot.setLabelPaint(Color.WHITE);
        plot.setLabelBackgroundPaint(null);
        return chart;
    }

}
