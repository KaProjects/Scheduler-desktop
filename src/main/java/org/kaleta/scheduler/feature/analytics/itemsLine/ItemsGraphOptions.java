package org.kaleta.scheduler.feature.analytics.itemsLine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Stanislav Kaleta on 12.01.2016.
 */
public class ItemsGraphOptions extends JPanel {
    private ItemsGraphModel model;
    private Map<String,Map<String,Set<String>>> data;
    private boolean informModel = true;

    private JPanel panelIncomeTypes;
    private JPanel panelExpenseTypes;
    private JCheckBox checkBoxIncome;
    private JCheckBox checkBoxExpense;
    private Map<JCheckBox,List<JCheckBox>> actualTypeCheckBoxes;

    private Color borderColor = Color.CYAN;

    public ItemsGraphOptions(ItemsGraphModel model){
        this.model = model;
        data = model.getOptionsData();
        initComponents();
    }

    private void initComponents() {
        JPanel panelViewOptions = initViewOptions();
        JPanel panelSourceOptions = initSourceOptions();


        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup()
                        .addComponent(panelSourceOptions)
                        .addComponent(panelViewOptions)
        );
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(panelSourceOptions)
                .addComponent(panelViewOptions));

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // just grab mouse event
            }
        });
    }

    private JPanel initSourceOptions() {
        List<String> monthNames = new ArrayList<>(data.keySet());
        JRadioButton singleMonthRadioButton = new JRadioButton("Single Month");
        JRadioButton multipleMonthsRadioButton = new JRadioButton("Multiple Months");
        ButtonGroup group = new ButtonGroup();
        group.add(singleMonthRadioButton);
        group.add(multipleMonthsRadioButton);

        JComboBox<String> comboBoxMonths = new JComboBox<>(monthNames.toArray(new String[monthNames.size()]));
        JPanel panelMonths = new JPanel();
        JScrollPane scrollPaneMonths = new JScrollPane(panelMonths);
        JButton buttonSelectAll = new JButton("Select All");
        JButton buttonClearSelection = new JButton("Clear Selection");
        JCheckBox checkBoxGroupData = new JCheckBox("Group day values of one month to one value");

        comboBoxMonths.addActionListener(e -> {
            if (informModel) {
                List<String> month = new ArrayList<>();
                month.add((String) comboBoxMonths.getSelectedItem());
                model.setSelectedMonths(month, false);
                updateViewOptions(month);
            }
        });

        JCheckBox[] checkBoxMonths = new JCheckBox[monthNames.size()];
        for (String monthName : monthNames) {
            JCheckBox checkBox = new JCheckBox(monthName);
            checkBox.addActionListener(e -> {
                if (informModel) {
                    List<String> selectedMonths = new ArrayList<>();
                    for (JCheckBox checkBoxMonth : checkBoxMonths) {
                        if (checkBoxMonth.isSelected()) {
                            selectedMonths.add(checkBoxMonth.getText());
                        }
                    }
                    model.setSelectedMonths(selectedMonths, checkBoxGroupData.isSelected());
                    updateViewOptions(selectedMonths);
                }
            });
            checkBoxMonths[monthNames.indexOf(monthName)] = checkBox;
            panelMonths.add(checkBox);
        }

        buttonSelectAll.addActionListener(e -> {
            informModel = false;
            for (JCheckBox checkBox : checkBoxMonths) {
                checkBox.setSelected(true);
            }
            informModel = true;
            model.setSelectedMonths(monthNames, checkBoxGroupData.isSelected());
            updateViewOptions(monthNames);
        });
        buttonClearSelection.addActionListener(e -> {
            informModel = false;
            for (JCheckBox checkBox : checkBoxMonths) {
                checkBox.setSelected(false);
            }
            informModel = true;
            model.setSelectedMonths(new ArrayList<>(), checkBoxGroupData.isSelected());
            updateViewOptions(new ArrayList<>());
        });
        checkBoxGroupData.addActionListener(e -> {
            if (informModel) {
                List<String> selectedMonths = new ArrayList<>();
                for (JCheckBox checkBoxMonth : checkBoxMonths) {
                    if (checkBoxMonth.isSelected()) {
                        selectedMonths.add(checkBoxMonth.getText());
                    }
                }
                if (!selectedMonths.isEmpty()) {
                    model.setSelectedMonths(selectedMonths, checkBoxGroupData.isSelected());
                }
            }
        });
        singleMonthRadioButton.addActionListener(e -> {
            informModel = false;
            int selectedCount = 0;
            String selectedMonth = null;
            for (JCheckBox checkBox : checkBoxMonths) {
                if (checkBox.isSelected()) {
                    selectedCount++;
                    selectedMonth = checkBox.getText();
                }
            }
            if (selectedCount == 1) {
                comboBoxMonths.setSelectedItem(selectedMonth);
            } else {
                comboBoxMonths.setSelectedIndex(-1);
                model.setSelectedMonths(new ArrayList<>(), false);
                updateViewOptions(new ArrayList<>());
                informModelAboutTypeSelection();
            }
            informModel = true;
            comboBoxMonths.setVisible(true);
            scrollPaneMonths.setVisible(false);
            buttonSelectAll.setVisible(false);
            buttonClearSelection.setVisible(false);
            checkBoxGroupData.setVisible(false);
        });
        multipleMonthsRadioButton.addActionListener(e -> {
            informModel = false;
            for (JCheckBox checkBox : checkBoxMonths) {
                if (!(comboBoxMonths.getSelectedIndex() == -1)
                        && comboBoxMonths.getSelectedItem().equals(checkBox.getText())){
                    checkBox.setSelected(true);
                } else {
                    checkBox.setSelected(false);
                }
            }
            informModel = true;
            comboBoxMonths.setVisible(false);
            scrollPaneMonths.setVisible(true);
            buttonSelectAll.setVisible(true);
            buttonClearSelection.setVisible(true);
            checkBoxGroupData.setVisible(true);
        });
        singleMonthRadioButton.doClick();

        JPanel panelSourceOptions = new JPanel();
        panelSourceOptions.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(borderColor), "Source Options"));
        GroupLayout layoutSourceOptions = new GroupLayout(panelSourceOptions);
        panelSourceOptions.setLayout(layoutSourceOptions);
        layoutSourceOptions.setHorizontalGroup(layoutSourceOptions.createSequentialGroup()
                .addGap(5)
                .addGroup(layoutSourceOptions.createParallelGroup()
                        .addGroup(layoutSourceOptions.createSequentialGroup()
                                .addComponent(singleMonthRadioButton)
                                .addComponent(multipleMonthsRadioButton))
                        .addComponent(comboBoxMonths)
                        .addComponent(scrollPaneMonths)
                        .addGroup(layoutSourceOptions.createSequentialGroup()
                                .addComponent(buttonSelectAll)
                                .addGap(5)
                                .addComponent(buttonClearSelection))
                        .addComponent(checkBoxGroupData))
                .addGap(5));
        layoutSourceOptions.setVerticalGroup(layoutSourceOptions.createSequentialGroup()
                .addGap(5)
                .addGroup(layoutSourceOptions.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(singleMonthRadioButton)
                        .addComponent(multipleMonthsRadioButton))
                .addGap(5)
                .addComponent(comboBoxMonths,30,30,30)
                .addComponent(scrollPaneMonths,45,45,45)
                .addGap(5)
                .addGroup(layoutSourceOptions.createParallelGroup()
                        .addComponent(buttonSelectAll)
                        .addComponent(buttonClearSelection))
                .addGap(5)
                .addComponent(checkBoxGroupData)
                .addGap(5));

        return panelSourceOptions;
    }

    private JPanel initViewOptions() {
        checkBoxIncome = new JCheckBox("Income");
        checkBoxIncome.addActionListener(e -> informModelAboutTypeSelection());

        panelIncomeTypes = new JPanel();
        panelIncomeTypes.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(borderColor), "Income Types"));
        panelIncomeTypes.setLayout(new BoxLayout(panelIncomeTypes, BoxLayout.Y_AXIS));
        JScrollPane scrollPaneIncomeTypes = new JScrollPane(panelIncomeTypes);

        checkBoxExpense = new JCheckBox("Expense");
        checkBoxExpense.addActionListener(e -> informModelAboutTypeSelection());

        panelExpenseTypes = new JPanel();
        panelExpenseTypes.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(borderColor), "Expense Types"));
        panelExpenseTypes.setLayout(new BoxLayout(panelExpenseTypes, BoxLayout.Y_AXIS));
        JScrollPane scrollPaneExpenseTypes = new JScrollPane(panelExpenseTypes);

        actualTypeCheckBoxes = new HashMap<>();
        informModel = false;
        updateViewOptions(new ArrayList<>());
        informModel = true;

        JPanel panelViewOptions = new JPanel();
        panelViewOptions.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(borderColor), "View Options"));

        GroupLayout layout = new GroupLayout(panelViewOptions);
        panelViewOptions.setLayout(layout);
        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGap(5)
                .addGroup(layout.createParallelGroup()
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(checkBoxExpense)
                                .addGap(5)
                                .addComponent(checkBoxIncome))
                        .addComponent(scrollPaneExpenseTypes)
                        .addComponent(scrollPaneIncomeTypes))
                .addGap(5));
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGap(5)
                .addGroup(layout.createParallelGroup()
                        .addComponent(checkBoxExpense)
                        .addComponent(checkBoxIncome))
                .addComponent(scrollPaneExpenseTypes,100,700,700)
                .addComponent(scrollPaneIncomeTypes,100,700,700)
                .addGap(5));

        return panelViewOptions;
    }

    private void updateViewOptions(List<String> selectedMonths){
        if (selectedMonths.isEmpty()){
            checkBoxIncome.setSelected(false);
            checkBoxIncome.setEnabled(false);
            checkBoxExpense.setSelected(false);
            checkBoxExpense.setEnabled(false);
        } else {
            checkBoxIncome.setEnabled(true);
            checkBoxExpense.setEnabled(true);
        }
        panelIncomeTypes.removeAll();
        panelIncomeTypes.repaint();
        panelExpenseTypes.removeAll();
        panelExpenseTypes.repaint();
        actualTypeCheckBoxes.clear();

        Map<String,Set<String>> mergedItemTypes = new HashMap<>();
        for (String month : selectedMonths){
            for (String type : data.get(month).keySet()){
                if (!mergedItemTypes.containsKey(type)){
                    mergedItemTypes.put(type,new HashSet<>());
                }
                mergedItemTypes.get(type).addAll(data.get(month).get(type));
            }
        }
        for (String typeSign : mergedItemTypes.keySet()){
            String type = typeSign.substring(0,typeSign.length() - 1);
            JCheckBox checkBoxType = new JCheckBox(type);
            actualTypeCheckBoxes.put(checkBoxType, new ArrayList<>());

            JPanel panelDescriptions = new JPanel();
            panelDescriptions.setLayout(new FlowLayout(FlowLayout.LEADING));
            for (String description : mergedItemTypes.get(typeSign)){
                JCheckBox checkBoxDescription = new JCheckBox(description);
                checkBoxDescription.addActionListener(e -> {
                    if (informModel){
                        informModelAboutTypeSelection();
                    }
                });
                panelDescriptions.add(checkBoxDescription);
                actualTypeCheckBoxes.get(checkBoxType).add(checkBoxDescription);
            }
            panelDescriptions.setVisible(false);
            checkBoxType.addActionListener(e -> {
                if (checkBoxType.isSelected()){
                    panelDescriptions.setVisible(true);
                    informModelAboutTypeSelection();
                } else {
                    informModel = false;
                    for (JCheckBox checkBoxDescription : actualTypeCheckBoxes.get(checkBoxType)){
                        checkBoxDescription.setSelected(false);
                    }
                    informModel = true;
                    panelDescriptions.setVisible(false);
                    informModelAboutTypeSelection();
                }
            });

            JPanel panelType = new JPanel();
            panelType.setBorder(BorderFactory.createLineBorder(borderColor));
            SpringLayout layout = new SpringLayout();
            panelType.setLayout(layout);
            panelType.add(checkBoxType);
            panelType.add(panelDescriptions);
            layout.putConstraint(SpringLayout.NORTH,checkBoxType,0,SpringLayout.NORTH,panelType);
            layout.putConstraint(SpringLayout.WEST,checkBoxType,0,SpringLayout.WEST,panelType);
            layout.putConstraint(SpringLayout.EAST,panelType,0,SpringLayout.EAST,checkBoxType);
            layout.putConstraint(SpringLayout.SOUTH,panelType,0,SpringLayout.SOUTH,panelDescriptions);
            layout.putConstraint(SpringLayout.WEST,panelDescriptions,0,SpringLayout.WEST,panelType);
            layout.putConstraint(SpringLayout.EAST,panelType,0,SpringLayout.EAST,panelDescriptions);
            layout.putConstraint(SpringLayout.NORTH,panelDescriptions,0,SpringLayout.SOUTH,checkBoxType);

            if (typeSign.endsWith("+")){
                panelIncomeTypes.add(panelType);
            } else {
                panelExpenseTypes.add(panelType);
            }
        }
        panelIncomeTypes.revalidate();
        panelIncomeTypes.repaint();
        panelExpenseTypes.revalidate();
        panelExpenseTypes.repaint();

        if (informModel){
            informModelAboutTypeSelection();
        }
    }

    private void informModelAboutTypeSelection(){
        Map<String,List<String>> types = new HashMap<>();
        actualTypeCheckBoxes.keySet().stream().filter(AbstractButton::isSelected).forEach(checkBoxType -> {
            List<String> descriptions = new ArrayList<>();
            types.put(checkBoxType.getText(), descriptions);
            descriptions.addAll(actualTypeCheckBoxes.get(checkBoxType).stream()
                    .filter(AbstractButton::isSelected).map(JCheckBox::getText).collect(Collectors.toList()));
        });
        model.setSelectedTypes(checkBoxIncome.isSelected(), checkBoxExpense.isSelected(),types);
    }
}
