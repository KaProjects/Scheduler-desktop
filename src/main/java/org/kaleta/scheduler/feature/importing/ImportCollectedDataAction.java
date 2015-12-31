package org.kaleta.scheduler.feature.importing;

import org.kaleta.scheduler.frontend.Configuration;
import org.kaleta.scheduler.frontend.action.menu.MenuAction;

import java.awt.Component;

/**
 * Created by Stanislav Kaleta on 13.11.2015.
 */
public class ImportCollectedDataAction extends MenuAction{

    public ImportCollectedDataAction(Configuration config) {
        super(config, "Collected Items");
    }

    @Override
    protected void actionPerformed() {
        ImportCollectedDataDialog dialog = new ImportCollectedDataDialog();
        dialog.setLocationRelativeTo((Component) getConfiguration());
        dialog.setVisible(true);
        getConfiguration().update(Configuration.ITEM_CHANGED);
    }
}
