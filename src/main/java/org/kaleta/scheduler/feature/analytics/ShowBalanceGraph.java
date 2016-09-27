package org.kaleta.scheduler.feature.analytics;

import org.kaleta.scheduler.frontend.Configuration;
import org.kaleta.scheduler.frontend.action.menu.MenuAction;

import java.awt.Component;

/**
 * Created by Stanislav Kaleta on 27.09.2016.
 */
public class ShowBalanceGraph extends MenuAction {

    public ShowBalanceGraph(Configuration config) {
        super(config, "Balance");
    }

    @Override
    protected void actionPerformed() {
        BalanceGraph graph = new BalanceGraph();
        graph.setLocationRelativeTo((Component) getConfiguration());
        graph.setVisible(true);
    }
}
