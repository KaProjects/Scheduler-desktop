package org.kaleta.scheduler.frontend.action.mouse;

import org.kaleta.scheduler.frontend.Configurable;
import org.kaleta.scheduler.service.Service;

import java.awt.event.MouseEvent;
import java.util.Map;

/**
 * Created by Stanislav Kaleta on 30.10.2015.
 */
public class GlobalPanelMonthArrowClicked extends MouseAction {
    private boolean increase;

    public GlobalPanelMonthArrowClicked(Configurable configurable, boolean increase){
        super(configurable);
        this.increase = increase;
    }

    @Override
    protected void actionPerformed(MouseEvent e) {
        Map<Integer, Integer> orders = Service.monthService().getMonthsOrder();

        int monthId = getConfiguration().getSelectedMonthId();

        Integer order = orders.get(monthId);

        int modifier = (increase) ? 1 : -1;

        for (Integer key : orders.keySet()) {
            if (orders.get(key).equals(order + modifier)) {
                getConfiguration().selectMonth(key);
            }
        }
    }
}
