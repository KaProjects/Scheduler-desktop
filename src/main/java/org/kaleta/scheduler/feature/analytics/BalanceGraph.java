package org.kaleta.scheduler.feature.analytics;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.AreaRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.kaleta.scheduler.backend.entity.Item;
import org.kaleta.scheduler.backend.entity.Month;
import org.kaleta.scheduler.frontend.common.ColorConstants;
import org.kaleta.scheduler.service.Service;

import javax.swing.*;
import java.awt.Color;
import java.util.List;

/**
 * Created by Stanislav Kaleta on 27.09.2016.
 */
public class BalanceGraph extends JFrame {

    public BalanceGraph(){
        super("Balance Graph");
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        ChartPanel chartPanel = new ChartPanel(createChart(createDataset()));
        setContentPane(chartPanel);
        this.pack();
    }

    private CategoryDataset createDataset() {

        List<Month> monthList = Service.monthService().retrieveAllMonths();

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (Month month : monthList){
            int income = 0;
            int expense = 0;
            for (Item item : month.getItems()){
                if (item.getIncome()){
                    income += item.getAmount().intValue();
                } else {
                    expense += item.getAmount().intValue();
                }
            }
            dataset.addValue(income, "Income", month.getName());
            dataset.addValue(expense, "Expense", month.getName());
        }

        return dataset;

    }
    private JFreeChart createChart(final CategoryDataset dataset) {
        JFreeChart chart = ChartFactory.createAreaChart(null,null,null,dataset,PlotOrientation.VERTICAL, true,true,false);

        chart.setBackgroundPaint(Color.white);
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);

        CategoryItemRenderer renderer = new AreaRenderer();
        Color incomeColor = ColorConstants.INCOME_GREEN;
        renderer.setSeriesPaint(0, new Color(incomeColor.getRed(), incomeColor.getGreen(), incomeColor.getBlue(), 200));
        Color expenseColor = ColorConstants.EXPENSE_RED;
        renderer.setSeriesPaint(1, new Color(expenseColor.getRed(), expenseColor.getGreen(), expenseColor.getBlue(), 200));
        plot.setRenderer(renderer);



        return chart;

    }



}
