package org.kaleta.scheduler.frontend.action.menu;

import org.kaleta.scheduler.frontend.Configuration;

/**
 * Created by Stanislav Kaleta on 11.11.2015.
 */
public class PerformExit extends MenuAction{

    public PerformExit(Configuration config) {
        super(config, "Exit");
    }

    @Override
    protected void actionPerformed() {
        System.exit(0);
    }
}
