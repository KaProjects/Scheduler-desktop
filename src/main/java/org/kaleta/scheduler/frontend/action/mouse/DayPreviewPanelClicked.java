package org.kaleta.scheduler.frontend.action.mouse;

import org.kaleta.scheduler.backend.entity.Day;
import org.kaleta.scheduler.frontend.Configurable;
import org.kaleta.scheduler.service.Service;

import javax.swing.*;
import java.awt.Point;
import java.awt.event.MouseEvent;

/**
 * Created by Stanislav Kaleta on 30.10.2015.
 */
public class DayPreviewPanelClicked extends MouseAction {
    private JComponent target;
    private Point position;

    public DayPreviewPanelClicked(JComponent target, Point position){
        super((Configurable) target);
        this.target = target;
        this.position = position;
    }

    @Override
    protected void actionPerformed(MouseEvent e) {
        if (!target.isEnabled()){
            return;
        }
        Day day = Service.dayService().getDayAt(position, getConfiguration().getSelectedMonthId());
        getConfiguration().selectDay(day.getDayNumber());
        target.grabFocus();
    }
}
