package org.example.GUI.charts;

import org.example.fileManager.FileManager;
import org.example.model.Cashbox;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class BarChart extends JFrame {
    private JFreeChart chart;
    public BarChart(){
        initUI();
    }
    private void initUI() {
        CategoryDataset dataset = createDataset(getCashBoxes());

        chart = createChart(dataset);

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);
        add(chartPanel, BorderLayout.CENTER);

        pack();
        setTitle("Гистограмма");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
    private CategoryDataset createDataset(List<Cashbox> cashboxes) {
        var dataset = new DefaultCategoryDataset();
        for (Cashbox c:cashboxes) {
            dataset.setValue(c.getSumm(), "Сумма(руб.)", "Касса №" + c.getId());
        }
        return dataset;
    }
    public List<Cashbox> getCashBoxes(){
        FileManager fileManager = new FileManager();
        return fileManager.getCashBoxes();
    }
    private JFreeChart createChart(CategoryDataset dataset) {
        JFreeChart barChart = ChartFactory.createBarChart(
                "Сумма(руб.) в кассах",
                "",
                "Сумма(руб.)",
                dataset,
                PlotOrientation.VERTICAL,
                false, true, false);
        return barChart;
    }
    public void refreshDataSet(List<Cashbox> cashboxes){
        chart.getCategoryPlot().setDataset(createDataset(cashboxes));
    }
    public void setVisible(){
        setVisible(true);
    }
}
