package org.kaleta.scheduler.feature.importing;

import org.kaleta.scheduler.backend.entity.Month;
import org.kaleta.scheduler.frontend.Configuration;
import org.kaleta.scheduler.frontend.action.menu.MenuAction;
import org.kaleta.scheduler.service.Service;

import java.awt.Component;

/**
 * Created by Stanislav Kaleta on 13.11.2015.
 */
public class ImportOldDataAction extends MenuAction{

    public ImportOldDataAction(Configuration config) {
        super(config, "Old Month");
    }

    @Override
    protected void actionPerformed() {
        ImportOldDataDialog dialog = new ImportOldDataDialog();
        dialog.setLocationRelativeTo((Component) getConfiguration());
        dialog.setVisible(true);
        if (dialog.getResult()){
            Month month = dialog.getImportedMonth();
            Service.monthService().createMonth(month);
            if (dialog.wantToSelectImportedMonth()){
                getConfiguration().selectMonth(month.getId());
            }
        }
    }
}
