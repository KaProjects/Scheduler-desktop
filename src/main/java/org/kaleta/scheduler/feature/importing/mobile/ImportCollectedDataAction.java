package org.kaleta.scheduler.feature.importing.mobile;

import org.kaleta.scheduler.frontend.Configuration;
import org.kaleta.scheduler.frontend.action.menu.MenuAction;

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
        //TODO implement
    }
}
