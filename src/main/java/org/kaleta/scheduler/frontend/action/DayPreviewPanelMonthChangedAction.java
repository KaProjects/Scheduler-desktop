package org.kaleta.scheduler.frontend.action;

import org.kaleta.scheduler.backend.entity.Day;
import org.kaleta.scheduler.frontend.Configurable;
import org.kaleta.scheduler.frontend.ConfigurationAction;
import org.kaleta.scheduler.service.Service;

import javax.swing.*;
import java.awt.Color;
import java.awt.Component;
import java.awt.Point;

/**
 * TODO documentation about actionPerformed
 * Created by Stanislav Kaleta on 30.10.2015.
 */
public class DayPreviewPanelMonthChangedAction extends ConfigurationAction {
    private JComponent target;
    private Point position;
    private JLabel labelDayNumber;// TODO will be changed
    private JLabel labelTasksItems;// TODo will be changed
    public DayPreviewPanelMonthChangedAction(JComponent target, Point position, JLabel labelDayNumber, JLabel labelTasksItems){
        super((Configurable) target);
        this.target = target;
        this.position = position;
        this.labelDayNumber = labelDayNumber;
        this.labelTasksItems = labelTasksItems;
    }

    @Override
    protected void actionPerformed() {
        Service service = new Service();
        Day day = service.getDayService().getDayAt(position,getConfigurable().getConfiguration().getSelectedMonthId());
        int dayNumber = day.getDayNumber();
        if (dayNumber == -1) {
            for (Component component : target.getComponents()){
                component.setVisible(false);
            }
            target.setEnabled(false);
            target.setBackground(Color.WHITE);//TODO this to UI (enabled=false)
            return;
        } else {
            for (Component component : target.getComponents()){
                component.setVisible(true);
            }
            target.setEnabled(true);
            target.setBackground(Color.LIGHT_GRAY);//TODO this to UI (enabled=true)
        }

        labelDayNumber.setText(String.valueOf(day.getDayNumber()));

        labelTasksItems.setText("T=" + day.getTasks().size() + " I=" + day.getItems().size());

        if (day.isPublicFreeDay()) {
            target.setBorder(BorderFactory.createLineBorder(Color.GREEN)); //TODO this to UI (?)
        }
    }
}
