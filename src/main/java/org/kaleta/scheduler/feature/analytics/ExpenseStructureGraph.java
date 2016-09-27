package org.kaleta.scheduler.feature.analytics;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.kaleta.scheduler.service.Service;

import javax.swing.*;
import java.awt.Font;
import java.util.Map;

/**
 * Created by Stanislav Kaleta on 27.09.2016.
 */
public class ExpenseStructureGraph extends JFrame {
    private ChartPanel chartPanel;

    public ExpenseStructureGraph(){
        super("Expense Structure Graph");
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        JComboBox<String> comboBoxMonths = new JComboBox<>();
        for (Integer id : Service.monthService().getMonthsOrder()){
            comboBoxMonths.addItem(Service.monthService().getMonthName(id));
        }
        comboBoxMonths.setSelectedIndex(0);
        comboBoxMonths.addActionListener(e -> {
            chartPanel.setChart(createChart(createDataset(comboBoxMonths.getSelectedIndex())));
        });

        chartPanel = new ChartPanel(createChart(createDataset(comboBoxMonths.getSelectedIndex())));

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup()
                .addComponent(comboBoxMonths)
                .addComponent(chartPanel));
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(comboBoxMonths)
                .addGap(5)
                .addComponent(chartPanel));
        this.pack();
    }

    private DefaultPieDataset createDataset(Integer monthIndex) {
        Integer id = Service.monthService().getMonthsOrder().get(monthIndex);
        Map<String, Integer> data = Service.itemService().getSortedExpenseItems(id);

        DefaultPieDataset dataset = new DefaultPieDataset();
        for (String key : data.keySet()){
            dataset.setValue(key, data.get(key));
        }
        return dataset;
    }
    private JFreeChart createChart(final DefaultPieDataset dataset) {
        JFreeChart chart = ChartFactory.createPieChart(null,dataset, true,false,false);

        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setLabelFont(new Font("SansSerif", Font.PLAIN, 12));
        plot.setNoDataMessage("No data available");
        plot.setCircular(false);
        plot.setLabelGap(0.02);
        return chart;
    }
}
