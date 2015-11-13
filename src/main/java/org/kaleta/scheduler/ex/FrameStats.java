package org.kaleta.scheduler.ex;

import org.kaleta.scheduler.backend.entity.Month;

import javax.swing.*;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created with IntelliJ IDEA.
 * User: Stanislav Kaleta
 * Date: 28.7.2014
 * To change this template use File | Settings | File Templates.
 */
public class FrameStats extends JFrame {
    private JPanel panelTop;
    private PanelGraph panelGraph;
    private JLabel labelMonthName;
    private JComboBox<String> comboBoxGraphs;

    public FrameStats(){
        panelTop = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelGraph = new PanelGraph();
        labelMonthName = new JLabel();
        comboBoxGraphs = new JComboBox<String>();

        initGroupLayout();
        initPanelTop();

        setVisible(false);
        //setResizable(false);/*TODO no resizable*/
        setSize(new Dimension(500,500));

    }

    private void initGroupLayout(){
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup()
                    .addGap(10)
                    .addComponent(panelTop)
                    .addComponent(panelGraph)
        );
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                    .addComponent(panelTop,GroupLayout.PREFERRED_SIZE,40,GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelGraph)
        );

    }
    private void initPanelTop(){
        panelTop.add(labelMonthName);
        labelMonthName.setMinimumSize(new Dimension(100, 30));
        labelMonthName.setPreferredSize(new Dimension(100, 30));
        labelMonthName.setFont(new Font("times new roman", 1, 15));
        labelMonthName.setText("- - -");
        panelTop.add(comboBoxGraphs);
        comboBoxGraphs.addItem("vydaje, prjimy"); /*TODO locale,nazov*/
        comboBoxGraphs.addItem("balance"); /*TODO locale,nazov*/
        comboBoxGraphs.addItem("vydaje denne + najvacsie tri polozky"); /*TODO locale,nazov*/
        comboBoxGraphs.addItem("struktorovane vydaje");   /*TODO locale,nazov*/
        comboBoxGraphs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelGraph.setLayout(comboBoxGraphs.getSelectedIndex());
                switch (comboBoxGraphs.getSelectedIndex()){
                    case 0:
                        setSize(new Dimension(1200,750));
                        break;
                    case 1:
                        setSize(new Dimension(1200,750));
                        break;
                    case 2:
                        /*TODO*///setSize(new Dimension(1160,735));
                        break;
                    case 3:
                        /*TODO*/setSize(new Dimension(1200,750));
                        break;
                }
            }
        });
        comboBoxGraphs.setSelectedIndex(-1);
    }

    public void setData(Month month){
        setSize(new Dimension(500,500));
        comboBoxGraphs.setSelectedIndex(-1);
        labelMonthName.setText(month.getName());
        panelGraph.setLayout(-1);
        panelGraph.setData(month.getItems(),month.getDaysNumber());

        /*TODO*/

    }
}
