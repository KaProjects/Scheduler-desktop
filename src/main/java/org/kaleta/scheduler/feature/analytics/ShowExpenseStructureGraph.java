package org.kaleta.scheduler.feature.analytics;

import org.kaleta.scheduler.feature.analytics.ExpenseStructureGraph;
import org.kaleta.scheduler.frontend.Configuration;
import org.kaleta.scheduler.frontend.action.menu.MenuAction;

import java.awt.Component;

/**
 * Created by Stanislav Kaleta on 27.09.2016.
 */
public class ShowExpenseStructureGraph extends MenuAction {

    public ShowExpenseStructureGraph(Configuration config) {
        super(config, "Expense Structure");
    }

    @Override
    protected void actionPerformed() {
        ExpenseStructureGraph graph = new ExpenseStructureGraph();
        graph.setLocationRelativeTo((Component) getConfiguration());
        graph.setVisible(true);
    }
}