package org.kaleta.scheduler.feature.analytics;

import org.kaleta.scheduler.backend.entity.Month;
import org.kaleta.scheduler.service.Service;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * Created by Stanislav Kaleta on 30.09.2016.
 */
public class SourceOptionsEditor extends JPanel {
    private Map<Month, Boolean> months;

    public SourceOptionsEditor(){
        months = new TreeMap<>();
        for (Month month : Service.monthService().retrieveAllMonths()){
            months.put(month, Boolean.FALSE);
        }
        initComponents();
    }

    private void initComponents() {
        JRadioButton singleMonthRadioButton = new JRadioButton("Single Month");
        JRadioButton multipleMonthsRadioButton = new JRadioButton("Multiple Months");
        ButtonGroup group = new ButtonGroup();
        group.add(singleMonthRadioButton);
        group.add(multipleMonthsRadioButton);

        JComboBox<Month> comboBoxMonths = new JComboBox<>(months.keySet().toArray(new Month[months.keySet().size()]));
        JPanel panelMonths = new JPanel();
        JScrollPane scrollPaneMonths = new JScrollPane(panelMonths);
        JButton buttonSelectAll = new JButton("Select All");
        JButton buttonClearSelection = new JButton("Clear Selection");

        List<JCheckBox> checkBoxList = new ArrayList<>();
        for (Month month : months.keySet()) {
            JCheckBox checkBox = new JCheckBox(month.getName());
            checkBox.addActionListener(e -> {
                months.put(month, checkBox.isSelected());
            });
            checkBoxList.add(checkBox);
            panelMonths.add(checkBox);
        }

        comboBoxMonths.addActionListener(e -> {
            Month selectedMonth = (Month) comboBoxMonths.getSelectedItem();
            for (Month month : months.keySet()){
                months.put(month, month.equals(selectedMonth));
            }

        });

        buttonSelectAll.addActionListener(e -> {
            for (JCheckBox checkBox : checkBoxList){
                checkBox.setSelected(true);
            }
            for (Month month : months.keySet()){
                months.put(month, Boolean.TRUE);
            }
        });

        buttonClearSelection.addActionListener(e -> {
            for (JCheckBox checkBox : checkBoxList){
                checkBox.setSelected(false);
            }
            for (Month month : months.keySet()){
                months.put(month, Boolean.FALSE);
            }
        });

        singleMonthRadioButton.addActionListener(e -> {
            int selectedMonthCount = 0;
            Month selectedMonth = null;
            for (Month month : months.keySet()){
                if (months.get(month)){
                    selectedMonthCount++;
                    selectedMonth = month;
                }
            }
            if (selectedMonthCount == 1) {
                comboBoxMonths.setSelectedItem(selectedMonth);
            } else {
                comboBoxMonths.setSelectedIndex(-1);
            }
            comboBoxMonths.setVisible(true);
            scrollPaneMonths.setVisible(false);
            buttonSelectAll.setVisible(false);
            buttonClearSelection.setVisible(false);
        });

        multipleMonthsRadioButton.addActionListener(e -> {
            for (JCheckBox checkBox : checkBoxList) {
                if (!(comboBoxMonths.getSelectedIndex() == -1)
                        && ((Month)comboBoxMonths.getSelectedItem()).getName().equals(checkBox.getText())){
                    checkBox.setSelected(true);
                } else {
                    checkBox.setSelected(false);
                }
            }
            comboBoxMonths.setVisible(false);
            scrollPaneMonths.setVisible(true);
            buttonSelectAll.setVisible(true);
            buttonClearSelection.setVisible(true);
        });

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGap(5)
                .addGroup(layout.createParallelGroup()
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(singleMonthRadioButton)
                                .addComponent(multipleMonthsRadioButton))
                        .addComponent(comboBoxMonths)
                        .addComponent(scrollPaneMonths)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(buttonSelectAll)
                                .addGap(5)
                                .addComponent(buttonClearSelection)))
                .addGap(5));
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGap(5)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(singleMonthRadioButton)
                        .addComponent(multipleMonthsRadioButton))
                .addGap(5)
                .addComponent(comboBoxMonths,30,30,30)
                .addComponent(scrollPaneMonths,45,45,45)
                .addGap(5)
                .addGroup(layout.createParallelGroup()
                        .addComponent(buttonSelectAll)
                        .addComponent(buttonClearSelection))
                .addGap(5));

        singleMonthRadioButton.doClick();
    }

    public List<Month> getSelectedMonths(){
        return months.keySet().stream().filter(month -> months.get(month)).collect(Collectors.toList());
    }
}
