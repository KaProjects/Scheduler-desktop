package org.kaleta.scheduler.frontend.action;

import org.kaleta.scheduler.backend.entity.Day;
import org.kaleta.scheduler.frontend.common.SwingWorkerHandler;
import org.kaleta.scheduler.frontend.panel.DayPreviewPanel;
import org.kaleta.scheduler.service.Service;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Stanislav Kaleta on 30.10.2015.
 */
public class DayPreviewPanelClicked extends MouseAdapter {
    private DayPreviewPanel panel;
    private Point position;

    public DayPreviewPanelClicked(DayPreviewPanel panel, Point position){
        this.panel = panel;
        this.position = position;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        new SwingWorkerHandler() {
            @Override
            protected void runInBackground() {
                Service service = new Service();
                Day day = service.getDayService().getDayAt(position, panel.getConfiguration().getSelectedMonthId());
                int dayNumber = day.getDayNumber();
                if (panel.isEnabled()){
                    panel.getConfiguration().selectDay(dayNumber);
                }
            }
        }.execute();
    }
}
