package org.kaleta.scheduler.ex;

import org.kaleta.scheduler.backend.entity.Item;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Stanislav Kaleta
 * Date: 23.7.2014
 * To change this template use File | Settings | File Templates.
 */
public class FrameAccounting extends JFrame {
    private final java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("l18n/L18nStrings");
//    private JMenuBar menuBar;  /*deprecated -> del*/
//    private JMenu menuImport;
//    private JMenuItem menuImportItemFromMobile;
//    private JMenuItem menuImportItemFromFile;
    private JPanel panelTitle;
    private JPanel panelTable;
    private JPanel panelAddItem;
    private JButton buttonAddItem;
    private JLabel labelName;
    private JTable tableAccounting;
    private JScrollPane scrollPane;
    private JSpinner spinnerIncome;
    private JComboBox<String> comboBoxType;
    private JTextField textFieldAmount;
    private JButton buttonAdd;
    private int workingDay;
    private List<String> incomeTypes;
    private List<String> costTypes;


    public FrameAccounting(JButton bAdd){
//        menuBar = new JMenuBar();
//        menuImport = new JMenu();
//        menuImportItemFromMobile = new JMenuItem();
//        menuImportItemFromFile = new JMenuItem();
        panelTitle = new JPanel();
        panelTable = new JPanel();
        panelAddItem = new JPanel();
        buttonAddItem = new JButton(); /*TODO format input component*/
        labelName = new JLabel();
        tableAccounting = new JTable();
        scrollPane = new JScrollPane();
        spinnerIncome = new JSpinner();
        comboBoxType = new JComboBox<String>();
        textFieldAmount = new JTextField();
        buttonAdd = bAdd;
        workingDay = 0;
        incomeTypes = new ArrayList<String>();
        costTypes = new ArrayList<String>();

        getItemTypes();
//        initMenu();
        initPanelAddItem();
        initGroupLayout();
        initPanelTitle();
        initPanelTable();

        setVisible(false);
        setSize(new Dimension(300,535));
        setResizable(false);

    }

//    private void initMenu(){
//        setJMenuBar(menuBar);
//        menuImport.setText(bundle.getString("IMPORT"));
//        menuBar.add(menuImport);
//        menuImportItemFromMobile.setText(bundle.getString("FROMMOBILE"));
//        menuImport.add(menuImportItemFromMobile);
//        menuImportItemFromFile.setText(bundle.getString("FROMFILE"));
//        menuImport.add(menuImportItemFromFile);
//    }
    private void initGroupLayout(){
        GroupLayout frameLayout = new GroupLayout(getContentPane());
        getContentPane().setLayout(frameLayout);
        frameLayout.setHorizontalGroup(
            frameLayout.createParallelGroup()
                .addComponent(panelTitle)
                .addComponent(panelTable)
                .addComponent(panelAddItem, GroupLayout.PREFERRED_SIZE, 295, GroupLayout.PREFERRED_SIZE)
        );
        frameLayout.setVerticalGroup(
            frameLayout.createSequentialGroup()
                .addComponent(panelTitle, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                .addComponent(panelTable, GroupLayout.PREFERRED_SIZE, 380, GroupLayout.PREFERRED_SIZE)
                .addComponent(panelAddItem, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
        );
    }

    private void initPanelTitle(){
        GroupLayout panelButtonsLayout = new GroupLayout(panelTitle);
        panelTitle.setLayout(panelButtonsLayout);
        panelButtonsLayout.setHorizontalGroup(
            panelButtonsLayout.createSequentialGroup()
                .addGap(20)
                .addComponent(labelName, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE)
                .addComponent(buttonAddItem, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
                .addGap(10)
        );
        panelButtonsLayout.setVerticalGroup(
            panelButtonsLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(labelName)
                .addComponent(buttonAddItem)
        );
        buttonAddItem.setText(bundle.getString("ADDITEM"));
        buttonAddItem.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buttonAddItem.setEnabled(false);
                panelAddItem.setVisible(true);

            }
        });
        labelName.setText("- - -");
        labelName.setFont(new Font("times new roman",1,15));

    }
    private void initPanelTable(){
        panelTable.add(scrollPane);
        scrollPane.setViewportView(tableAccounting);
        tableAccounting.setPreferredScrollableViewportSize(new Dimension(280, 350));
        tableAccounting.setModel(new TableModelAccounting(new ArrayList<Item>()));
        /*TODO maybe mouse listener for something*/
        tableAccounting.getTableHeader().setReorderingAllowed(false);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                TableModelAccounting model = (TableModelAccounting) table.getModel();
                final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(model.getRowColour(row));
                return c;
            }
        };
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        tableAccounting.setDefaultRenderer(Object.class, centerRenderer);
        tableAccounting.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
    }
    private void initPanelAddItem(){
        GroupLayout panelAddItemLayout = new GroupLayout(panelAddItem);
        panelAddItem.setLayout(panelAddItemLayout);
        panelAddItemLayout.setHorizontalGroup(
                panelAddItemLayout.createSequentialGroup()
                        .addComponent(spinnerIncome, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
                        .addComponent(comboBoxType, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
                        .addComponent(textFieldAmount, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
                        .addComponent(buttonAdd, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
        );
        panelAddItemLayout.setVerticalGroup(
                panelAddItemLayout.createSequentialGroup()
                        .addGap(10)
                        .addGroup(panelAddItemLayout.createParallelGroup()
                                        .addComponent(spinnerIncome)
                                        .addComponent(comboBoxType)
                                        .addComponent(textFieldAmount)
                                        .addComponent(buttonAdd)
                        )
                        .addGap(10)
        );
        panelAddItem.setBorder(BorderFactory.createTitledBorder(bundle.getString("ADDITEM")));
        panelAddItem.setVisible(false);
        buttonAdd.setText(bundle.getString("ADD"));
        buttonAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panelAddItem.setVisible(false);
                buttonAddItem.setEnabled(true);
            }
        });
        spinnerIncome.setModel(new SpinnerListModel(new String[]{"-", "+"}));
        spinnerIncome.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (spinnerIncome.getValue().toString().equals("-")){
                    comboBoxType.removeAllItems();
                    for (String str : costTypes){
                        comboBoxType.addItem(str);
                    }
                }
                if (spinnerIncome.getValue().toString().equals("+")){
                    comboBoxType.removeAllItems();
                    for (String str : incomeTypes){
                        comboBoxType.addItem(str);
                    }
                }
            }
        });
        ((JSpinner.DefaultEditor)spinnerIncome.getEditor()).getTextField().setEditable(false);
        for (String str : costTypes){
            comboBoxType.addItem(str);
        }
    }
    private void getItemTypes() {
        try {
            File resourcesFile = new File(getClass().getProtectionDomain().getCodeSource().getLocation().getPath()+"resources/ItemTypes.txt");
            BufferedReader br = new BufferedReader(new FileReader(resourcesFile));
            String line;
            while ((line = br.readLine()) != null) {
                String[] splittedLine = line.split(";");
                if (splittedLine[0].equals("-")) {
                    costTypes.add(splittedLine[1]);
                }
                if (splittedLine[0].equals("+")) {
                    incomeTypes.add(splittedLine[1]);
                }

            }
        } catch (IOException ex){
            ex.printStackTrace(); /*TODO log*/
        }
    }


    public void setTableModel(TableModelAccounting model){
        tableAccounting.setModel(model);
        tableAccounting.getColumnModel().getColumn(0).setMaxWidth(40);
        tableAccounting.getColumnModel().getColumn(1).setMaxWidth(140);
        tableAccounting.getColumnModel().getColumn(2).setMaxWidth(100);
    }
    public void setDayName(int dayNumber, int dayInWeek,String monthName){
        final String[] dayNames = java.util.ResourceBundle.getBundle("l18n/L18nStrings").getString("DAYSOFWEEK").split(",");
        String dayName = "";
        switch (dayInWeek){
            case 1:
                dayName = dayNames[0]+" - "+dayNumber+". "+monthName;
                break;
            case 2:
                dayName = dayNames[1]+" - "+dayNumber+". "+monthName;
                break;
            case 3:
                dayName = dayNames[2]+" - "+dayNumber+". "+monthName;
                break;
            case 4:
                dayName = dayNames[3]+" - "+dayNumber+". "+monthName;
                break;
            case 5:
                dayName = dayNames[4]+" - "+dayNumber+". "+monthName;
                break;
            case 6:
                dayName = dayNames[5]+" - "+dayNumber+". "+monthName;
                break;
            case 7:
                dayName = dayNames[6]+" - "+dayNumber+". "+monthName;
                break;
        }
        labelName.setText(dayName);
    }
    public void setWorkingDay(int workingDay){
        this.workingDay = workingDay;
    }
    public Item getCreatedItem(){
        Item output = new Item();
        output.setDay(workingDay);
        if (spinnerIncome.getValue().toString().equals("+")){
            output.setIncome(true);
        } else {
            output.setIncome(false);
        }
        output.setType(comboBoxType.getSelectedItem().toString());
        output.setAmount(new BigDecimal(textFieldAmount.getText()));
        return output;
    }

}
