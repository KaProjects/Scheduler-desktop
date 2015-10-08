package org.kaleta.scheduler.ex;

import org.kaleta.scheduler.backend.entity.Month;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Stanislav Kaleta
 * Date: 11.7.2014
 * To change this template use File | Settings | File Templates.
 */
public class FrameNewMonth extends JFrame {
    private JPanel panelTitle;
    private JPanel panelLabels;
    private JPanel panelFields;
    private JPanel panelButtons;
    private JLabel labelTitle;
    private JLabel labelName;
    private JTextField textFieldName;
    private JLabel labelNumberOfDays;
    private JSpinner spinnerNumberOfDays;
    private JLabel labelDayStartsWith;
    private JSpinner spinnerDayStartWith;
    private JButton buttonCreate;
    private JButton buttonCancel;
    private Map<String,Integer> daysOfWeek;

    private final java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("l18n/L18nStrings");

    public FrameNewMonth(JButton buttonForCreate) {
        panelTitle = new JPanel();
        panelLabels = new JPanel();
        panelFields = new JPanel();
        panelButtons = new JPanel();
        labelTitle = new JLabel();
        labelName = new JLabel();
        textFieldName = new JTextField();
        labelNumberOfDays = new JLabel();
        spinnerNumberOfDays = new JSpinner();
        labelDayStartsWith = new JLabel();
        spinnerDayStartWith = new JSpinner();
        buttonCreate = buttonForCreate;
        buttonCancel = new JButton();
        daysOfWeek = new HashMap<String,Integer>();

        initGroupLayout();
        initPanelTitle();
        initPanelLabels();
        initPanelFields();
        initPanelButtons();

        setVisible(false);
        setName(bundle.getString("NEWMONTH"));
        setSize(new Dimension(360, 280));
        setResizable(false);
    }

    private void initGroupLayout(){
        GroupLayout frameLayout = new GroupLayout(getContentPane());
        getContentPane().setLayout(frameLayout);
        frameLayout.setHorizontalGroup(
                frameLayout.createParallelGroup()
                        .addGroup(frameLayout.createSequentialGroup()
                                .addComponent(panelTitle))
                        .addGroup(frameLayout.createSequentialGroup()
                                .addGap(65)
                                .addComponent(panelLabels, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
                                .addComponent(panelFields, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE))
                        .addGroup(frameLayout.createSequentialGroup()
                                .addComponent(panelButtons))
        );
        frameLayout.setVerticalGroup(
                frameLayout.createSequentialGroup()
                        .addGap(20)
                        .addComponent(panelTitle, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
                        .addGroup(frameLayout.createParallelGroup()
                                .addComponent(panelLabels)
                                .addComponent(panelFields))
                        .addComponent(panelButtons, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
        );
    }
    private void initPanelTitle(){
        panelTitle.add(labelTitle);
        labelTitle.setText(bundle.getString("INSERTNEWMONTHPARAM"));
        labelTitle.setFont(new Font("times new roman",1,20));
    }
    private void initPanelLabels() {
        GroupLayout panelLabelsLayout = new GroupLayout(panelLabels);
        panelLabels.setLayout(panelLabelsLayout);
        panelLabelsLayout.setHorizontalGroup(
            panelLabelsLayout.createParallelGroup()
                .addComponent(labelName)
                .addComponent(labelNumberOfDays)
                .addComponent(labelDayStartsWith)
        );
        panelLabelsLayout.setVerticalGroup(
                panelLabelsLayout.createSequentialGroup()
                        .addComponent(labelName)
                        .addGap(30)
                        .addComponent(labelNumberOfDays)
                        .addGap(30)
                        .addComponent(labelDayStartsWith)
        );
        labelName.setText(bundle.getString("NAME"));
        labelNumberOfDays.setText(bundle.getString("NUMBEROFDAYS"));
        labelDayStartsWith.setText(bundle.getString("STARTINGDAY"));
    }
    private void initPanelFields(){
        GroupLayout panelFieldsLayout = new GroupLayout(panelFields);
        panelFields.setLayout(panelFieldsLayout);
        panelFieldsLayout.setHorizontalGroup(
                panelFieldsLayout.createParallelGroup()
                        .addGroup(panelFieldsLayout.createSequentialGroup()
                                .addComponent(textFieldName)
                                .addGap(10))
                        .addGroup(panelFieldsLayout.createSequentialGroup()
                                .addComponent(spinnerNumberOfDays)
                                .addGap(90))
                        .addGroup(panelFieldsLayout.createSequentialGroup()
                                .addComponent(spinnerDayStartWith)
                                .addGap(50))
        );
        panelFieldsLayout.setVerticalGroup(
                panelFieldsLayout.createSequentialGroup()
                        .addComponent(textFieldName)
                        .addGap(20)
                        .addComponent(spinnerNumberOfDays)
                        .addGap(20)
                        .addComponent(spinnerDayStartWith)
                        .addGap(20)
        );
        spinnerNumberOfDays.setModel(new SpinnerListModel(new String[]{"28", "29", "30", "31"}));
        ((JSpinner.DefaultEditor)spinnerNumberOfDays.getEditor()).getTextField().setEditable(false);
        String[] tempArray = bundle.getString("DAYSOFWEEK").split(",");
        Integer tempInt = 0;
        for (String day : tempArray){
            tempInt++;
            daysOfWeek.put(day,tempInt);
        }
        spinnerDayStartWith.setModel(new SpinnerListModel(tempArray));
        ((JSpinner.DefaultEditor)spinnerDayStartWith.getEditor()).getTextField().setEditable(false);
    }
    private void initPanelButtons(){
        GroupLayout panelButtonsLayout = new GroupLayout(panelButtons);
        panelButtons.setLayout(panelButtonsLayout);
        panelButtonsLayout.setHorizontalGroup(
                panelButtonsLayout.createSequentialGroup()
                        .addGap(100)
                        .addComponent(buttonCreate)
                        .addGap(20)
                        .addComponent(buttonCancel)
        );
        panelButtonsLayout.setVerticalGroup(
                panelButtonsLayout.createParallelGroup()
                        .addComponent(buttonCreate)
                        .addComponent(buttonCancel)
        );
        buttonCancel.setText(bundle.getString("CANCEL"));
        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textFieldName.setText("");
                setVisible(false);
            }
        });
    }
    public void cleanTextFields(){
        textFieldName.setText("");
    }
    public Month getNewMonth(){
        Month output = new Month();
        output.setName(textFieldName.getText());
        output.setDaysNumber(Integer.parseInt(spinnerNumberOfDays.getValue().toString()));
        output.setDayStartsWith(daysOfWeek.get(spinnerDayStartWith.getValue()));
        return output;
    }

}
