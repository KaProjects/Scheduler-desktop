package org.kaleta.scheduler.feature.analytics.structure;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.kaleta.scheduler.backend.entity.Item;
import org.kaleta.scheduler.backend.entity.Month;

import javax.swing.*;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Stanislav Kaleta on 27.09.2016.
 */
public class StructureGraph extends JFrame {

    public StructureGraph(){
        super("Structure Graph");
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setLayout(new GridLayout(1,1));



        StructureSourceOptionsEditor sourceEditor = new StructureSourceOptionsEditor();
        StructureViewOptionsEditor viewEditor = new StructureViewOptionsEditor();
        ChartPanel chartPanel = new ChartPanel(null);
        StructureList structureList = new StructureList();
        JScrollPane structureListPane = new JScrollPane(structureList);

        JTabbedPane pane = new JTabbedPane();
        pane.addTab("Source Options", sourceEditor);
        pane.addTab("View Options", viewEditor);
        pane.addTab("Graph", chartPanel);
        pane.addTab("List", structureListPane);

        pane.addChangeListener(e -> {
            if (pane.getSelectedIndex() == 2){
                chartPanel.setChart(createChart(createDataset(sourceEditor, viewEditor)));
            }
            if (pane.getSelectedIndex() == 3){
                structureList.update(sourceEditor, viewEditor);
            }
        });

        this.add(pane);
        this.pack();
    }

    private DefaultPieDataset createDataset(StructureSourceOptionsEditor sourceEditor, StructureViewOptionsEditor viewEditor) {
        DefaultPieDataset dataset = new DefaultPieDataset();

        Map<String, Integer> data = new HashMap<>();
        if (viewEditor.isAllTypes()){
            for (Month month : sourceEditor.getSelectedMonths()){
                for (Item item :month.getItems()){
                    if (item.getIncome().equals(viewEditor.isIncome())){
                        if (data.keySet().contains(item.getType())){
                            data.put(item.getType(), data.get(item.getType()) + item.getAmount().intValue());
                        } else {
                            data.put(item.getType(), item.getAmount().intValue());
                        }
                    }
                }
            }
        } else {
            for (Month month : sourceEditor.getSelectedMonths()){
                for (Item item :month.getItems()){
                    if (item.getIncome().equals(viewEditor.isIncome())
                            && item.getType().equals(viewEditor.getSelectedType().getName())){
                        if (data.keySet().contains(item.getDescription())){
                            data.put(item.getDescription(), data.get(item.getDescription()) + item.getAmount().intValue());
                        } else {
                            data.put(item.getDescription(), item.getAmount().intValue());
                        }
                    }
                }
            }
        }

        for (String key : data.keySet()){
            dataset.setValue(key, data.get(key));
        }

        return dataset;
    }
    private JFreeChart createChart(final DefaultPieDataset dataset) {
        JFreeChart chart = ChartFactory.createPieChart(null,dataset, false,true,false);

        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setLabelFont(new Font("SansSerif", Font.PLAIN, 12));
        plot.setNoDataMessage("No data available");
        plot.setCircular(false);
        plot.setLabelGap(0.02);
        return chart;
    }
}
