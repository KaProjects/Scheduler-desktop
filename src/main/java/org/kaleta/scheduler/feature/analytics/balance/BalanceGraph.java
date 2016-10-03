package org.kaleta.scheduler.feature.analytics.balance;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
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
import java.awt.GridLayout;
import java.util.List;

/**
 * Created by Stanislav Kaleta on 27.09.2016.
 */
public class BalanceGraph extends JFrame {

    public BalanceGraph(){
        super("Balance Graph");
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setLayout(new GridLayout(1,1));

        BalanceSourceOptionsEditor sourceEditor = new BalanceSourceOptionsEditor();
        BalanceViewOptionsEditor viewEditor = new BalanceViewOptionsEditor();
        ChartPanel chartPanel = new ChartPanel(null);

        JTabbedPane pane = new JTabbedPane();
        pane.addTab("Source Options", sourceEditor);
        pane.addTab("View Options", viewEditor);
        pane.addTab("Graph", chartPanel);

        pane.addChangeListener(e -> {
            if (pane.getSelectedIndex() == 2){
                chartPanel.setChart(createChart(createDataset(sourceEditor, viewEditor)));
            }
        });

        this.add(pane);
        this.pack();
    }

    private CategoryDataset createDataset(BalanceSourceOptionsEditor sourceEditor, BalanceViewOptionsEditor viewEditor) {

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
        renderer.setSeriesToolTipGenerator(0, new StandardCategoryToolTipGenerator());

        Color expenseColor = ColorConstants.EXPENSE_RED;
        renderer.setSeriesPaint(1, new Color(expenseColor.getRed(), expenseColor.getGreen(), expenseColor.getBlue(), 200));
        renderer.setSeriesToolTipGenerator(1, new StandardCategoryToolTipGenerator());

        plot.setRenderer(renderer);

        plot.getDomainAxis().setCategoryLabelPositions(CategoryLabelPositions.DOWN_45);

        return chart;
    }



}
