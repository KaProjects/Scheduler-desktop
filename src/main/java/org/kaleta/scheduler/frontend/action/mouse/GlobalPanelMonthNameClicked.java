package org.kaleta.scheduler.frontend.action.mouse;

import org.kaleta.scheduler.frontend.Configurable;
import org.kaleta.scheduler.frontend.dialog.SelectMonthDialog;
import org.kaleta.scheduler.service.Service;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.util.Map;

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
        SelectMonthDialog dialog = new SelectMonthDialog();

        Map<Integer, Integer> orders = Service.monthService().getMonthsOrder();
        String[] monthNames = new String[orders.size()];
        Integer[] monthIds = new Integer[orders.size()];
        int actuallySelectedMonthIndex = -1;
        int index = 0;
        for (Integer key : orders.keySet()) {
            monthNames[index] = Service.monthService().getMonthName(key);
            monthIds[index] = key;

            if (key == getConfiguration().getSelectedMonthId()) {
                actuallySelectedMonthIndex = index;
            }
            index++;
        }

        dialog.setMonthNames(monthNames, actuallySelectedMonthIndex);
        dialog.setLocationRelativeTo(target);
        dialog.setLocation(dialog.getX(), dialog.getY() + dialog.getHeight() / 2);
        dialog.setVisible(true);

        if (dialog.getResult()) {
            Integer selectedMonthId = monthIds[dialog.getSelectedMonthIndex()];
            getConfiguration().selectMonth(selectedMonthId);
        }
    }
}
