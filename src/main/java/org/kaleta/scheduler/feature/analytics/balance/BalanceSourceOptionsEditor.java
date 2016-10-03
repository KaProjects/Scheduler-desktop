package org.kaleta.scheduler.feature.analytics.balance;

import org.kaleta.scheduler.backend.entity.Month;
import org.kaleta.scheduler.service.Service;

import javax.swing.*;
import java.util.List;

/**
 * Created by Stanislav Kaleta on 03.10.2016.
 */
public class BalanceSourceOptionsEditor extends JPanel {
    private List<Month> months;
    private int fromIndex;
    private int toIndex;

    public BalanceSourceOptionsEditor(){
        months = Service.monthService().retrieveAllMonths();
        initComponents();
    }

    private void initComponents(){
        JRadioButton allMonthsRadioButton = new JRadioButton("All Months");
        JRadioButton intervalMonthsRadioButton = new JRadioButton("Interval of Months");
        JRadioButton singleMonthRadioButton = new JRadioButton("Single Month");
        ButtonGroup group = new ButtonGroup();
        group.add(allMonthsRadioButton);
        group.add(intervalMonthsRadioButton);
        group.add(singleMonthRadioButton);

        JComboBox<Month> comboBoxFromMonth = new JComboBox<>();
        JLabel labelFromMonth = new JLabel("From Month:");
        JPanel panelFromMonth = new JPanel();
        GroupLayout panelFromMonthLayout = new GroupLayout(panelFromMonth);
        panelFromMonth.setLayout(panelFromMonthLayout);
        panelFromMonthLayout.setHorizontalGroup(panelFromMonthLayout.createSequentialGroup()
                .addComponent(labelFromMonth,100,100,100).addGap(5).addComponent(comboBoxFromMonth));
        panelFromMonthLayout.setVerticalGroup(panelFromMonthLayout.createParallelGroup()
                .addComponent(labelFromMonth,30,30,30).addComponent(comboBoxFromMonth,30,30,30));

        JComboBox<Month> comboBoxToMonth = new JComboBox<>();
        JLabel labelToMonth = new JLabel("To Month:");
        JPanel panelToMonth = new JPanel();
        GroupLayout panelToMonthLayout = new GroupLayout(panelToMonth);
        panelToMonth.setLayout(panelToMonthLayout);
        panelToMonthLayout.setHorizontalGroup(panelToMonthLayout.createSequentialGroup()
                .addComponent(labelToMonth,100,100,100).addGap(5).addComponent(comboBoxToMonth));
        panelToMonthLayout.setVerticalGroup(panelToMonthLayout.createParallelGroup()
                .addComponent(labelToMonth,30,30,30).addComponent(comboBoxToMonth,30,30,30));

        final boolean[] comboBoxSyncAllowed = {true};

        JComboBox<Month> comboBoxSingleMonth = new JComboBox<>();

        allMonthsRadioButton.addActionListener(e -> {
            panelFromMonth.setVisible(false);
            panelToMonth.setVisible(false);
            comboBoxSingleMonth.setVisible(false);
            fromIndex = 0;
            toIndex = months.size() - 1;
        });

        intervalMonthsRadioButton.addActionListener(e -> {
            panelFromMonth.setVisible(true);
            panelToMonth.setVisible(true);
            comboBoxSingleMonth.setVisible(false);
            fromIndex = 0;
            toIndex = months.size() - 1;
            comboBoxSyncAllowed[0] = false;
            ((DefaultComboBoxModel)comboBoxFromMonth.getModel()).removeAllElements();
            for (Month month : months){
                ((DefaultComboBoxModel<Month>)comboBoxFromMonth.getModel()).addElement(month);
            }
            comboBoxFromMonth.setSelectedIndex(fromIndex);
            ((DefaultComboBoxModel)comboBoxToMonth.getModel()).removeAllElements();
            for (Month month : months){
                ((DefaultComboBoxModel<Month>)comboBoxToMonth.getModel()).addElement(month);
            }
            comboBoxToMonth.setSelectedIndex(toIndex);
            comboBoxSyncAllowed[0] = true;
        });

        singleMonthRadioButton.addActionListener(e -> {
            panelFromMonth.setVisible(false);
            panelToMonth.setVisible(false);
            comboBoxSingleMonth.setVisible(true);
            fromIndex = 0;
            toIndex = 0;
            ((DefaultComboBoxModel)comboBoxSingleMonth.getModel()).removeAllElements();
            for (Month month : months){
                ((DefaultComboBoxModel<Month>)comboBoxSingleMonth.getModel()).addElement(month);
            }
            comboBoxSingleMonth.setSelectedIndex(0);
        });

        comboBoxSingleMonth.addActionListener(e -> {
            fromIndex = comboBoxSingleMonth.getSelectedIndex();
            toIndex = comboBoxSingleMonth.getSelectedIndex();
        });

        comboBoxFromMonth.addActionListener(e -> {
            if (comboBoxSyncAllowed[0]){
                comboBoxSyncAllowed[0] = false;
                fromIndex = comboBoxFromMonth.getSelectedIndex();
                Month selectedToMonth = (Month) comboBoxToMonth.getSelectedItem();
                ((DefaultComboBoxModel)comboBoxToMonth.getModel()).removeAllElements();
                for (Month month : months.subList(fromIndex, months.size())){
                    ((DefaultComboBoxModel<Month>)comboBoxToMonth.getModel()).addElement(month);
                }
                comboBoxToMonth.setSelectedItem(selectedToMonth);
                comboBoxSyncAllowed[0] = true;
            }
        });

        comboBoxToMonth.addActionListener(e -> {
            if (comboBoxSyncAllowed[0]){
                comboBoxSyncAllowed[0] = false;
                toIndex = fromIndex + comboBoxToMonth.getSelectedIndex();
                Month selectedFromMonth = (Month) comboBoxFromMonth.getSelectedItem();
                ((DefaultComboBoxModel)comboBoxFromMonth.getModel()).removeAllElements();
                for (Month month : months.subList(0, toIndex + 1)){
                    ((DefaultComboBoxModel<Month>)comboBoxFromMonth.getModel()).addElement(month);
                }
                comboBoxFromMonth.setSelectedItem(selectedFromMonth);
                comboBoxSyncAllowed[0] = true;
            }
        });

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGap(5)
                .addGroup(layout.createParallelGroup()
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(allMonthsRadioButton)
                                .addComponent(intervalMonthsRadioButton)
                                .addComponent(singleMonthRadioButton))
                        .addComponent(panelFromMonth)
                        .addComponent(panelToMonth)
                        .addComponent(comboBoxSingleMonth))
                .addGap(5));
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGap(5)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(allMonthsRadioButton)
                        .addComponent(intervalMonthsRadioButton)
                        .addComponent(singleMonthRadioButton))
                .addGap(5)
                .addComponent(panelFromMonth,30,30,30)
                .addGap(2)
                .addComponent(panelToMonth,30,30,30)
                .addComponent(comboBoxSingleMonth,30,30,30)
                .addGap(5));

        allMonthsRadioButton.doClick();
    }

    public List<Month> getMonthsInterval(){
        return months.subList(fromIndex, toIndex + 1);
    }
}

