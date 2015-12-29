package org.kaleta.scheduler.frontend.panel;

import org.kaleta.scheduler.backend.entity.Time;

import javax.swing.*;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stanislav Kaleta on 06.08.2015.
 */
public class SchedulePanel extends JPanel {
    private List<JPanel> hours;

    public SchedulePanel(){
        hours = new ArrayList<>();

        initComponents();
    }

    private void initComponents() {


        JPanel layerHours = new JPanel();
        layerHours.setLayout(new BoxLayout(layerHours, BoxLayout.Y_AXIS));
        for (int hour=0;hour<24;hour++){
            JPanel panelHour = new JPanel(new GridLayout(1,1));
            panelHour.setBackground(Color.WHITE);
            panelHour.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
            JLabel labelHourTitle = new JLabel(" " + hour + ":00");
            labelHourTitle.setVerticalAlignment(SwingConstants.TOP);
            labelHourTitle.setForeground(Color.LIGHT_GRAY);
            panelHour.add(labelHourTitle);
            hours.add(hour,panelHour);
            layerHours.add(panelHour);
        }


        JPanel layerTasks = new JPanel();
        layerTasks.setLayout(null);
        layerTasks.setOpaque(false);

        JPanel task = new JPanel();
        Color red = Color.red;
        task.setBackground(new Color(red.getRed(), red.getGreen(), red.getBlue(), 55));
        task.setBorder(BorderFactory.createLineBorder(red));
        task.add(new JLabel("task"));
        task.setToolTipText("<html>10:00 - 11:30<br>type<br>desc.</html>");
        layerTasks.add(task);

        JPanel task2 = new JPanel();
        Color blue = Color.blue;
        task2.setBackground(new Color(blue.getRed(), blue.getGreen(), blue.getBlue(), 55));
        task2.setBorder(BorderFactory.createLineBorder(blue, 2));
        task2.add(new JLabel("task2"));
        task2.setToolTipText("<html>11:30 - 12:00<br>type<br>desc.</html>");
        layerTasks.add(task2);

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.add(layerHours, new Integer(0));
        layeredPane.add(layerTasks, new Integer(1));
        layeredPane.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                layerHours.setBounds(0, 0, layeredPane.getSize().width, layeredPane.getSize().height);
                layerTasks.setBounds(0, 0, layeredPane.getSize().width, layeredPane.getSize().height);

                Time startT = new Time();
                startT.setFromString("10:00");

                Time duration = new Time();
                duration.setFromString("01:30");

                float start = startT.getHour() + (float)startT.getMinutes()/60;
                float end = duration.getHour() + (float)duration.getMinutes()/60;

                task.setBounds(0, (int) (layerHours.getHeight()/24*start),layeredPane.getSize().width,(int) (layerHours.getHeight()/24*end));

                Time startT2 = new Time();
                startT2.setFromString("11:30");

                Time duration2 = new Time();
                duration2.setFromString("00:30");

                float start2 = startT2.getHour() + (float)startT2.getMinutes()/60;
                float end2 = duration2.getHour() + (float)duration2.getMinutes()/60;

                task2.setBounds(0, (int) (layerHours.getHeight()/24*start2),layeredPane.getSize().width,(int) (layerHours.getHeight()/24*end2));

                layeredPane.revalidate();

            }
        });



        JButton buttonAdd = new JButton("Add Task");
        buttonAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                //TODO ... open dialog + set + perform + revalidate scroll/panel
                System.out.println(layeredPane.getSize());
            }
        });

        //TODO edit task

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup()
                .addComponent(layeredPane)
                .addComponent(buttonAdd, GroupLayout.Alignment.CENTER));
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(layeredPane)
                .addGap(5)
                .addComponent(buttonAdd));

        // TODO actions month,day,task added
    }
}
