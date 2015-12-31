package org.kaleta.scheduler.frontend.action.configuration;

import org.kaleta.scheduler.backend.entity.Day;
import org.kaleta.scheduler.frontend.Configurable;
import org.kaleta.scheduler.service.Service;

import javax.swing.*;
import java.awt.Color;
import java.awt.Point;

/**
 * Created by Stanislav Kaleta on 30.10.2015.
 */
public class DayPreviewPanelDayChanged extends ConfigurationAction {
    private JComponent target;
    private Point position;

    public DayPreviewPanelDayChanged(JComponent target, Point position){
        super((Configurable) target);
        this.target = target;
        this.position = position;
    }

    @Override
    protected void actionPerformed() {
        Day day = Service.dayService().getDayAt(position,getConfiguration().getSelectedMonthId());
        int dayNumber = day.getDayNumber();
        if (getConfiguration().getSelectedDayNumber() == dayNumber) {
            target.setBackground(Color.ORANGE);
        } else {
            if (target.isEnabled()){
                target.setBackground(Color.LIGHT_GRAY);
            }
        }
    }
}
