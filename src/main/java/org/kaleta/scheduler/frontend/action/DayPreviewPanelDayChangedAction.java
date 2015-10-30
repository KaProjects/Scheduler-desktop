package org.kaleta.scheduler.frontend.action;

import org.kaleta.scheduler.backend.entity.Day;
import org.kaleta.scheduler.frontend.Configurable;
import org.kaleta.scheduler.frontend.ConfigurationAction;
import org.kaleta.scheduler.service.Service;

import javax.swing.*;
import java.awt.Color;
import java.awt.Point;

/**
 * TODO documentation about actionPerformed
 * Created by Stanislav Kaleta on 30.10.2015.
 */
public class DayPreviewPanelDayChangedAction extends ConfigurationAction{
    private JComponent target;
    private Point position;

    public DayPreviewPanelDayChangedAction(JComponent target, Point position){
        super((Configurable) target);
        this.target = target;
        this.position = position;
    }
    @Override
    protected void actionPerformed() {
        Service service = new Service();
        Day day = service.getDayService().getDayAt(position,getConfigurable().getConfiguration().getSelectedMonthId());
        int dayNumber = day.getDayNumber();
        if (getConfigurable().getConfiguration().getSelectedDayNumber() == dayNumber) {
            target.setBackground(Color.ORANGE);//TODO this to UI
        } else {
            if (target.isEnabled()){
                target.setBackground(Color.LIGHT_GRAY);//TODO this to UI
            }

        }
    }
}
