package org.kaleta.scheduler.feature.analytics.itemsLine;

import org.kaleta.scheduler.feature.analytics.GraphFrame;
import org.kaleta.scheduler.frontend.Configuration;
import org.kaleta.scheduler.frontend.action.menu.MenuAction;

import java.awt.*;

/**
 * Created by Stanislav Kaleta on 12.01.2016.
 */
public class ShowItemsLineGraph extends MenuAction {


    public ShowItemsLineGraph(Configuration config) {
        super(config, "Items Line Graph");
    }

    @Override
    protected void actionPerformed() {
        ItemsGraphModel model = new ItemsGraphModel();

        ItemsGraphBackground background = new ItemsGraphBackground(model);
        ItemsGraphForeground foreground = new ItemsGraphForeground(model);
        ItemsGraphLegend legend = new ItemsGraphLegend(model);
        ItemsGraphOptions options = new ItemsGraphOptions(model);

        GraphFrame graph = new GraphFrame(options,legend,background,foreground);
        model.setTarget(graph.getContentPane());
        graph.setTitle("Items Graph");
        graph.setLocationRelativeTo((Component) getConfiguration());

        /*TODO decide max or just fixed init size*/
        graph.setExtendedState(Frame.MAXIMIZED_BOTH);
        //graph.setSize(500,500);

        graph.setVisible(true);
    }
}
