package org.kaleta.scheduler.frontend.action.menu;

import org.kaleta.scheduler.backend.entity.Month;
import org.kaleta.scheduler.frontend.Configuration;
import org.kaleta.scheduler.frontend.Initializer;
import org.kaleta.scheduler.frontend.dialog.CreateMonthDialog;
import org.kaleta.scheduler.service.Service;

import java.awt.Component;

/**
 * Created by Stanislav Kaleta on 11.11.2015.
 */
public class OpenCreateMonthDialog extends MenuAction{

    public OpenCreateMonthDialog(Configuration config) {
        super(config, "Create...");
    }

    @Override
    protected void actionPerformed() {
        CreateMonthDialog dialog = new CreateMonthDialog();
        dialog.setLocationRelativeTo((Component) getConfiguration());
        dialog.setVisible(true);

        if (dialog.getResult()){
            Month createdMonth = dialog.getCreatedMonth();
            Service.monthService().createMonth(createdMonth);

            Initializer.LOG.info("New month \""+createdMonth.getName()
                    + "\" with id=" + createdMonth.getId()
                    + " created in file %DATA%/months-database/m" + createdMonth.getId()  + ".xml");

            if (dialog.wantToSelectCreatedMonth()){
                getConfiguration().selectMonth(createdMonth.getId());
            }
        }
    }
}
