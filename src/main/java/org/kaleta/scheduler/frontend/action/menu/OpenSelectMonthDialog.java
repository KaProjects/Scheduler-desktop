package org.kaleta.scheduler.frontend.action.menu;

import org.kaleta.scheduler.frontend.Configuration;
import org.kaleta.scheduler.frontend.dialog.SelectMonthDialog;
import org.kaleta.scheduler.service.Service;

import java.awt.*;
import java.util.List;

/**
 * Created by Stanislav Kaleta on 11.11.2015.
 */
public class OpenSelectMonthDialog extends MenuAction{

    public OpenSelectMonthDialog(Configuration config) {
        super(config, "Select...");
    }

    @Override
    protected void actionPerformed() {
        List<Integer> orderedIds = Service.monthService().getMonthsOrder();
        String[] monthNames = new String[orderedIds.size()];
        for (Integer id : orderedIds) {
            monthNames[orderedIds.indexOf(id)] = Service.monthService().getMonthName(id);
        }

        SelectMonthDialog dialog = new SelectMonthDialog();
        dialog.setMonthNames(monthNames, orderedIds.indexOf(getConfiguration().getSelectedMonthId()));
        dialog.setLocationRelativeTo((Component) getConfiguration());
        dialog.setVisible(true);
        if (dialog.getResult()) {
            Integer selectedMonthId = orderedIds.get(dialog.getSelectedMonthIndex());
            getConfiguration().selectMonth(selectedMonthId);
        }
    }
}
