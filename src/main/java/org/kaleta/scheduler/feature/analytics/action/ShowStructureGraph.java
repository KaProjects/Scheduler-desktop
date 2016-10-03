package org.kaleta.scheduler.feature.analytics.action;

import org.kaleta.scheduler.feature.analytics.structure.StructureGraph;
import org.kaleta.scheduler.frontend.Configuration;
import org.kaleta.scheduler.frontend.action.menu.MenuAction;

import java.awt.Component;

/**
 * Created by Stanislav Kaleta on 27.09.2016.
 */
public class ShowStructureGraph extends MenuAction {

    public ShowStructureGraph(Configuration config) {
        super(config, "Structure Graph");
    }

    @Override
    protected void actionPerformed() {
        StructureGraph graph = new StructureGraph();
        graph.setLocationRelativeTo((Component) getConfiguration());
        graph.setVisible(true);
    }
}