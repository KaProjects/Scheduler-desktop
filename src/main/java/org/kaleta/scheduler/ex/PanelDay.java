package org.kaleta.scheduler.ex;

import javax.swing.*;
import java.awt.*;

/**
 * Created with IntelliJ IDEA.
 * User: Stanislav Kaleta
 * Date: 19.7.2014
 * To change this template use File | Settings | File Templates.
 */
public class PanelDay extends JPanel {
    private JPanel panelTasks;
    private JPanel panelAccounting;
    private JButton buttonAccounting;
    private int workingDay;
    private String name;

    public PanelDay(JButton buttonAccounting){
        panelTasks = new JPanel(); /*TODO maybe custon panel*/  panelTasks.setBackground(Color.cyan);
        panelAccounting = new JPanel();
        workingDay = 0;
        name = "- - -";
        this.buttonAccounting = buttonAccounting;
        buttonAccounting.setEnabled(false);
        setBorder(BorderFactory.createTitledBorder(name)); /*TODO design*/

        initGroupLayout();
        initPanelTasks();
        initPanelAccounting();
    }

    private void initGroupLayout(){
        GroupLayout panelDayLayout = new GroupLayout(this);
        this.setLayout(panelDayLayout);
        panelDayLayout.setHorizontalGroup(
                panelDayLayout.createParallelGroup()
                        .addComponent(panelTasks)             /*TODO vyladit rozmery*/
                        .addComponent(panelAccounting)
        );
        panelDayLayout.setVerticalGroup(
            panelDayLayout.createSequentialGroup()
                .addComponent(panelTasks)
                .addComponent(panelAccounting,GroupLayout.PREFERRED_SIZE,50,GroupLayout.PREFERRED_SIZE)
        );
    }
    private  void initPanelTasks(){
        /*TODO*/
    }
    private void initPanelAccounting(){
        /*TODO mozno nejaky listener na to pre ktorz den to robi*/
        panelAccounting.add(buttonAccounting);
    }

    public void setData(int day,String newName,boolean exists){
        name = newName;
        workingDay = day;
        if(exists) {
            setBorder(BorderFactory.createTitledBorder(name+" - "+workingDay+"."));
            buttonAccounting.setEnabled(true);
        } else {
            setBorder(BorderFactory.createTitledBorder("- - -"));
            buttonAccounting.setEnabled(false);
        }

    }
}
