package org.example.GUI.charts;

import org.example.fileManager.FileManager;
import org.example.model.Cashbox;
import org.example.model.DataSet;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.time.*;
import org.jfree.data.xy.XYDataset;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class LineChart extends JFrame {
    private JFreeChart chart;
    private TimeSeriesCollection dataset;
    private TimeSeries income;
    private TimeSeries costs;
    private final FileManager fileManager = new FileManager();
    private Cashbox cashbox;
    public LineChart(Cashbox cashbox) {
        this.cashbox = cashbox;
        initUI();
    }

    private void initUI() {
        createDataset();
        chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);

        add(chartPanel);

        pack();
        setTitle("Линейный график");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    private void createDataset() {
        Date now = Calendar.getInstance().getTime();
        income  = new TimeSeries("Доходы");
        costs = new TimeSeries("Расходы");
        income.add(new Second(now), 0);
        costs.add(new Second(now), 0);
        dataset = new TimeSeriesCollection();
        dataset.addSeries(income);
        dataset.addSeries(costs);
    }

    private JFreeChart createChart(XYDataset dataset) {

        JFreeChart chart = ChartFactory.createTimeSeriesChart (
                "График доходов и расходов",
                "Дата",
                "Руб.",
                dataset,
                true,
                true,
                false
        );

        XYPlot plot = chart.getXYPlot();

        var renderer = new XYLineAndShapeRenderer();

        renderer.setSeriesPaint(0, Color.GREEN);
        renderer.setSeriesStroke(0, new BasicStroke(2.0f));
        renderer.setSeriesPaint(1, Color.RED);
        renderer.setSeriesStroke(1, new BasicStroke(2.0f));

        plot.setRenderer(renderer);
        plot.setBackgroundPaint(Color.white);
        plot.setRangeGridlinesVisible(false);
        plot.setDomainGridlinesVisible(false);
        // Определение временной оси
        DateAxis axis = (DateAxis) plot.getDomainAxis();
        // Формат отображения осевых меток
        //HH:MM:SS
        axis.setDateFormatOverride(new SimpleDateFormat("MM:ss"));

        chart.getLegend().setFrame(BlockBorder.NONE);

        chart.setTitle(new TextTitle("График доходов и расходов.",
                        new Font("Serif", Font.BOLD, 18)
                )
        );
        return chart;
    }
    public void refreshTitle(){
        chart.setTitle(new TextTitle("График доходов и расходов.Кассы №" + cashbox.getId(),
                        new Font("Serif", Font.BOLD, 18)
                )
        );
    }
    public void refreshDataSet(){
        List<DataSet> data = fileManager.getData(cashbox.getId());
        if(data != null && !data.isEmpty()){
            for (DataSet d:data) {
                if(d.getValue() < 0){
                    costs.addOrUpdate(new Second(d.getDate()), d.getValue() * -1);
                }else {
                    income.addOrUpdate(new Second(d.getDate()), d.getValue());
                }
            }
            final TimeSeriesCollection newDataset = new TimeSeriesCollection();
            newDataset.addSeries(income);
            newDataset.addSeries(costs);

            chart.getXYPlot().setDataset(newDataset);
        }
    }
    public void setCashbox(Cashbox cashbox) {
        this.cashbox = cashbox;
        refreshTitle();
        refreshDataSet();
    }

    public Cashbox getCashbox() {
        return cashbox;
    }
}
