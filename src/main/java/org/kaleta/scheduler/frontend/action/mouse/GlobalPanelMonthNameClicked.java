package org.kaleta.scheduler.frontend.action.mouse;

import org.kaleta.scheduler.frontend.Configurable;
import org.kaleta.scheduler.frontend.dialog.SelectMonthDialog;
import org.kaleta.scheduler.service.Service;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * Created by Stanislav Kaleta on 30.10.2015.
 */
public class GlobalPanelMonthNameClicked extends MouseAction{
    private JComponent target;

    public GlobalPanelMonthNameClicked(Configurable configurable, JComponent target){
        super(configurable);
        this.target = target;
    }

    @Override
    protected void actionPerformed(MouseEvent e) {
        List<Integer> orderedIds = Service.monthService().getMonthsOrder();
        String[] monthNames = new String[orderedIds.size()];
        for (Integer id : orderedIds) {
            monthNames[orderedIds.indexOf(id)] = Service.monthService().getMonthName(id);
        }

        SelectMonthDialog dialog = new SelectMonthDialog();
        dialog.setMonthNames(monthNames, orderedIds.indexOf(getConfiguration().getSelectedMonthId()));
        dialog.setLocationRelativeTo(target);
        dialog.setLocation(dialog.getX(), dialog.getY() + dialog.getHeight() / 2);
        dialog.setVisible(true);
        if (dialog.getResult()) {
            Integer selectedMonthId = orderedIds.get(dialog.getSelectedMonthIndex());
            getConfiguration().selectMonth(selectedMonthId);
        }
    }
}
