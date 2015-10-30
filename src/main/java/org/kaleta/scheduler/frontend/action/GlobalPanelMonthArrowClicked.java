package org.kaleta.scheduler.frontend.action;

import org.kaleta.scheduler.frontend.Configurable;
import org.kaleta.scheduler.frontend.common.SwingWorkerHandler;
import org.kaleta.scheduler.service.MonthService;
import org.kaleta.scheduler.service.Service;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

/**
 * TODO documentation about actionPerformed
 * Created by Stanislav Kaleta on 30.10.2015.
 */
public class GlobalPanelMonthArrowClicked extends MouseAdapter {
    private Configurable configurable;
    private boolean increase;

    public GlobalPanelMonthArrowClicked(Configurable configurable, boolean increase){
        this.configurable = configurable;
        this.increase = increase;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        new SwingWorkerHandler() {
            @Override
            protected void runInBackground() {
                Service service = new Service();
                Map<Integer, Integer> orders = service.getMonthService().getMonthsOrder();

                int monthId = configurable.getConfiguration().getSelectedMonthId();

                Integer order = orders.get(monthId);

                int modifier = (increase) ? 1 : -1;

                for (Integer key : orders.keySet()) {
                    if (orders.get(key).equals(order + modifier)) {
                        configurable.getConfiguration().selectMonth(key);
                    }
                }
            }
        }.execute();
    }
}
