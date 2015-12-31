package org.kaleta.scheduler.frontend.action.menu;

import org.kaleta.scheduler.frontend.Configuration;
import org.kaleta.scheduler.frontend.dialog.SelectMonthDialog;
import org.kaleta.scheduler.service.Service;

import java.awt.Component;
import java.util.Map;

/**
 * Created by Stanislav Kaleta on 11.11.2015.
 */
public class OpenSelectMonthDialog extends MenuAction{

    public OpenSelectMonthDialog(Configuration config) {
        super(config, "Select...");
    }

    @Override
    protected void actionPerformed() {
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
        dialog.setLocationRelativeTo((Component) getConfiguration());
        dialog.setVisible(true);

        if (dialog.getResult()) {
            Integer selectedMonthId = monthIds[dialog.getSelectedMonthIndex()];
            getConfiguration().selectMonth(selectedMonthId);
        }
    }
}
