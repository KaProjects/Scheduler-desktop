package org.kaleta.scheduler.ex;

import org.kaleta.scheduler.backend.entity.Item;
import org.kaleta.scheduler.ex.graphs.Graph0;
import org.kaleta.scheduler.ex.graphs.Graph1;
import org.kaleta.scheduler.ex.graphs.Graph2;
import org.kaleta.scheduler.ex.graphs.Graph3;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Stanislav Kaleta
 * Date: 28.7.2014
 * To change this template use File | Settings | File Templates.
 */
public class PanelGraph extends JPanel {
    private List<Item> items;
    private int numberOfDays;
    private GroupLayout layout;
    private JPanel panelGraphNothing = new JPanel();
    private JPanel panelGraphNothingInfo = new JPanel();
    private JPanel panelGraph0 = new JPanel(new FlowLayout(FlowLayout.LEFT));
    private JPanel panelGraph0Info = new JPanel();
    private JPanel panelGraph1 = new JPanel();
    private JPanel panelGraph1Info = new JPanel();
    private JPanel panelGraph2 = new JPanel();
    private JPanel panelGraph2Info = new JPanel();
    private JPanel panelGraph3 = new JPanel();
    private JPanel panelGraph3Info = new JPanel();
    private JPanel actualGraph;
    private JPanel actualGraphInfo;

    public PanelGraph(){
        items = null;
        numberOfDays = 0;

        initLayout();
    }
    private void initLayout(){
        layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addComponent(panelGraphNothing)
                        .addComponent(panelGraphNothingInfo, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup()
                .addComponent(panelGraphNothing)
                .addComponent(panelGraphNothingInfo)
        );
        actualGraph = panelGraphNothing;
        actualGraphInfo = panelGraphNothingInfo;
    }
    private void setNothing(){
        layout.replace(actualGraph, panelGraphNothing);
        actualGraph = panelGraphNothing;
        layout.replace(actualGraphInfo, panelGraphNothingInfo);
        actualGraphInfo = panelGraphNothingInfo;
    }
    private void setGraph0(){
        /*graphLayout settings*/
        Graph0 graph = new Graph0();
        graph.setPreferredSize(new Dimension(30 * numberOfDays + 95, 650));
        graph.setData(numberOfDays, items);
        panelGraph0.removeAll();
        panelGraph0.setBorder(BorderFactory.createTitledBorder("Graph"));  /*TODO locale*/
        panelGraph0.setLayout(new BoxLayout(panelGraph0, BoxLayout.PAGE_AXIS));
        panelGraph0.add(graph);
        layout.replace(actualGraph, panelGraph0);
        actualGraph = panelGraph0;
        /*graphInfoLayout settings*/
        JLabel labelGraph0InfoCosts = new JLabel();
        JLabel labelGraph0InfoIncome = new JLabel();
        labelGraph0InfoIncome.setText("income"); /*TODO locale*/
        labelGraph0InfoCosts.setText("costs"); /*TODO locale*/
        JComponent compCosts = new JComponent() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawRect(0, 0, 25, 15);
                g.setColor(Color.red);
                g.fillRect(1, 1, 24, 14);
            }
        };
        JComponent compIncome = new JComponent() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawRect(0, 0, 25, 15);
                g.setColor(Color.green);
                g.fillRect(1, 1, 24, 14);
            }
        };
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.PAGE_AXIS));
        panel.add(Box.createVerticalGlue());
        panel.add(labelGraph0InfoCosts);
        panel.add(compCosts);
        panel.add(labelGraph0InfoIncome);
        panel.add(compIncome);
        panel.setPreferredSize(new Dimension(50, 150));
        panel.setMaximumSize(new Dimension(50,150));
        panelGraph0Info.removeAll();
        panelGraph0Info.setLayout(new BoxLayout(panelGraph0Info, BoxLayout.PAGE_AXIS));
        panelGraph0Info.add(Box.createRigidArea(new Dimension(50, 0)));
        panelGraph0Info.add(panel);
        panelGraph0Info.add(Box.createVerticalGlue());
        panelGraph0Info.setBorder(BorderFactory.createTitledBorder("Graph Info"));  /*TODO locale*/
        layout.replace(actualGraphInfo, panelGraph0Info);
        actualGraphInfo = panelGraph0Info;
    }
    private void setGraph1(){
        /*layout settings*/
        Graph1 graph = new Graph1();
        graph.setPreferredSize(new Dimension(30 * numberOfDays + 95, 650));
        graph.setData(numberOfDays, items);
        panelGraph1.removeAll();
        panelGraph1.setBorder(BorderFactory.createTitledBorder("Graph"));  /*TODO locale*/
        panelGraph1.setLayout(new BoxLayout(panelGraph1, BoxLayout.PAGE_AXIS));
        panelGraph1.add(graph);
        layout.replace(actualGraph, panelGraph1);
        actualGraph = panelGraph1;
        /*graphInfoLayout settings*/
        /*TODO*/
        layout.replace(actualGraphInfo, panelGraph1Info);
        actualGraphInfo = panelGraph1Info;

    }
    private void setGraph2(){
        /*layout settings*/
        Graph2 graph = new Graph2();
        graph.setPreferredSize(new Dimension(30 * numberOfDays + 95, 650));
        graph.setData(numberOfDays, items);
        panelGraph2.removeAll();
        panelGraph2.setBorder(BorderFactory.createTitledBorder("Graph"));  /*TODO locale*/
        panelGraph2.setLayout(new BoxLayout(panelGraph2, BoxLayout.PAGE_AXIS));
        panelGraph2.add(graph);
        layout.replace(actualGraph, panelGraph2);
        actualGraph = panelGraph2;
        /*graphInfoLayout settings*/
        /*TODO*/
        layout.replace(actualGraphInfo, panelGraph2Info);
        actualGraphInfo = panelGraph2Info;
    }
    private void setGraph3(){
        /*layout settings*/
        Graph3 graph = new Graph3();
        graph.setPreferredSize(new Dimension(30 * numberOfDays + 95, 650));
        graph.setData(numberOfDays, items);
        panelGraph3.removeAll();
        panelGraph3.setBorder(BorderFactory.createTitledBorder("Graph"));  /*TODO locale*/
        panelGraph3.setLayout(new BoxLayout(panelGraph3, BoxLayout.PAGE_AXIS));
        panelGraph3.add(graph);
        layout.replace(actualGraph, panelGraph3);
        actualGraph = panelGraph3;
        /*graphInfoLayout settings*/
        /*TODO*/
        layout.replace(actualGraphInfo, panelGraph3Info);
        actualGraphInfo = panelGraph3Info;
    }

    public void setLayout(int type){
        switch (type){
            case -1:
                setNothing();
                break;
            case 0:
                setGraph0();
                break;
            case 1:
                setGraph1();
                break;
            case 2:
                setGraph2();
                break;
            case 3:
                setGraph3();
                break;
            /*TODO dalsie grafy*/
        }
    }
    public void setData(java.util.List<Item> items, int numberOfDays){
        this.items = items;
        this.numberOfDays = numberOfDays;
    }




}
