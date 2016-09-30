package org.kaleta.scheduler.feature.analytics.structure;

import org.kaleta.scheduler.backend.entity.UserType;
import org.kaleta.scheduler.service.Service;

import javax.swing.*;
import java.util.List;

/**
 * Created by Stanislav Kaleta on 30.09.2016.
 */
public class StructureViewOptionsEditor extends JPanel{
    private boolean income;
    private boolean allTypes;
    private UserType selectedType;

    public StructureViewOptionsEditor(){
        initComponents();
    }

    private void initComponents() {
        JRadioButton expenseRadioButton = new JRadioButton("Expense");
        JRadioButton incomeRadioButton = new JRadioButton("Income");
        ButtonGroup group = new ButtonGroup();
        group.add(expenseRadioButton);
        group.add(incomeRadioButton);

        JRadioButton allTypesRadioButton = new JRadioButton("All Item Types");
        JRadioButton oneTypeRadioButton = new JRadioButton("One Item Type");
        ButtonGroup groupTypes = new ButtonGroup();
        groupTypes.add(allTypesRadioButton);
        groupTypes.add(oneTypeRadioButton);

        JComboBox<UserType> comboBoxTypes = new JComboBox<>();

        List<UserType> expenseTypes = Service.itemService().getItemTypes(false);
        expenseRadioButton.addActionListener(e -> {
            DefaultComboBoxModel model = (DefaultComboBoxModel) comboBoxTypes.getModel();
            model.removeAllElements();
            for (UserType type : expenseTypes){
                model.addElement(type);
            }
            comboBoxTypes.setSelectedIndex(-1);
            income = false;
        });

        List<UserType> incomeTypes = Service.itemService().getItemTypes(true);
        incomeRadioButton.addActionListener(e -> {
            DefaultComboBoxModel model = (DefaultComboBoxModel) comboBoxTypes.getModel();
            model.removeAllElements();
            for (UserType type : incomeTypes){
                model.addElement(type);
            }
            comboBoxTypes.setSelectedIndex(-1);
            income = true;
        });

        allTypesRadioButton.addActionListener(e -> {
            comboBoxTypes.setVisible(false);
            allTypes = true;
        });

        oneTypeRadioButton.addActionListener(e -> {
            comboBoxTypes.setVisible(true);
            allTypes = false;
        });

        comboBoxTypes.addActionListener(e -> selectedType = (UserType) comboBoxTypes.getSelectedItem());

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGap(5)
                .addGroup(layout.createParallelGroup()
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(expenseRadioButton)
                                .addComponent(incomeRadioButton))
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(allTypesRadioButton)
                                .addComponent(oneTypeRadioButton))
                        .addComponent(comboBoxTypes))
                .addGap(5));
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGap(5)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(expenseRadioButton)
                        .addComponent(incomeRadioButton))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(allTypesRadioButton)
                        .addComponent(oneTypeRadioButton))
                .addComponent(comboBoxTypes,30,30,30)
                .addGap(5));

        expenseRadioButton.doClick();
        allTypesRadioButton.doClick();
    }

    public boolean isIncome(){
        return income;
    }

    public boolean isAllTypes(){
        return allTypes;
    }

    public UserType getSelectedType() {
        return selectedType;
    }
}
