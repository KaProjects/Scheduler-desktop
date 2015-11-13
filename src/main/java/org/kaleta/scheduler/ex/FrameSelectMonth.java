package org.kaleta.scheduler.ex;

import javax.swing.*;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created with IntelliJ IDEA.
 * User: Stanislav Kaleta
 * Date: 11.7.2014
 * To change this template use File | Settings | File Templates.
 */
public class FrameSelectMonth extends JFrame {
    private final java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("l18n/L18nStrings");
    private JPanel panelTitle;
    private JPanel panelList;
    private JPanel panelButtons;
    private JList<String> listMonth;
    private JScrollPane scrollPane;
    private JLabel labelTitle;
    private JButton buttonSelect;
    private JButton buttonCancel;


    public FrameSelectMonth(JButton buttonForSelect){
        panelTitle = new JPanel();
        panelList = new JPanel();
        panelButtons = new JPanel();
        listMonth = new JList<String>();
        scrollPane = new JScrollPane(listMonth);
        labelTitle = new JLabel();
        buttonSelect = buttonForSelect;
        buttonCancel = new JButton();

        initGroupLayout();
        initPanelTitle();
        initPanelList();
        initPanelButtons();

        setVisible(false);
        setName(bundle.getString("SELECTMONTH"));
        setSize(new Dimension(200, 280));
        setResizable(false);
    }

    private void initGroupLayout(){
        GroupLayout frameLayout = new GroupLayout(getContentPane());
        getContentPane().setLayout(frameLayout);
        frameLayout.setHorizontalGroup(
            frameLayout.createParallelGroup()
                .addComponent(panelTitle)
                .addComponent(panelList)
                .addComponent(panelButtons)

        );
        frameLayout.setVerticalGroup(
            frameLayout.createSequentialGroup()
                .addGap(5)
                .addComponent(panelTitle,GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                .addGap(5)
                .addComponent(panelList,GroupLayout.PREFERRED_SIZE, 170, GroupLayout.PREFERRED_SIZE)
                .addComponent(panelButtons,GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
        );
    }
    private void initPanelTitle(){
        panelTitle.add(labelTitle);
        labelTitle.setText(bundle.getString("CHOOSEMONTH"));
        labelTitle.setFont(new Font("times new roman",1,20));
    }
    private void initPanelList(){
        panelList.add(scrollPane);
        scrollPane.setPreferredSize(new Dimension(120, 150));
        listMonth.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listMonth.setVisibleRowCount(-1);
    }
    private void initPanelButtons(){
        GroupLayout panelButtonsLayout = new GroupLayout(panelButtons);
        panelButtons.setLayout(panelButtonsLayout);
        panelButtonsLayout.setHorizontalGroup(
            panelButtonsLayout.createSequentialGroup()
                .addGap(10)
                .addComponent(buttonSelect)
                .addGap(10)
                .addComponent(buttonCancel)
        );
        panelButtonsLayout.setVerticalGroup(
            panelButtonsLayout.createParallelGroup(GroupLayout.Alignment.CENTER)
                .addComponent(buttonSelect)
                .addComponent(buttonCancel)
        );
        panelButtons.add(buttonSelect);
        panelButtons.add(buttonCancel);
        buttonCancel.setText(bundle.getString("CANCEL"));
        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
    }

    public void setData(String[] data){
       listMonth.setListData(data);
    }
    // for perform settings
    protected void setSelectedMonth(String monthName){
        setData(new String[]{monthName});
        listMonth.setSelectedValue(monthName,false);
    }

    public String getSelectedMonth(){
        return listMonth.getSelectedValue();
    }
}
