package org.kaleta.scheduler.feature.importing.mobile;

import org.kaleta.scheduler.frontend.Configuration;
import org.kaleta.scheduler.frontend.action.menu.MenuAction;

import java.awt.Component;

/**
 * Created by Stanislav Kaleta on 13.11.2015.
 */
public class ImportCollectedDataAction extends MenuAction{

    public ImportCollectedDataAction(Configuration config) {
        super(config, "From android app...");
        this.setEnabled(false);
    }

    @Override
    protected void actionPerformed() {
        ImportCollectedDataDialog dialog = new ImportCollectedDataDialog();
        dialog.setLocationRelativeTo((Component) getConfiguration());
        dialog.setVisible(true);
        //if (dialog.getResult()){
            // TODO impl.
            // TODO this dialog maybe only will have cancel button, i.e. everything happens inside
       // }
    }
}
