package org.kaleta.scheduler.frontend.action.mouse;

import org.kaleta.scheduler.frontend.Configurable;
import org.kaleta.scheduler.service.Service;

import java.awt.event.MouseEvent;
import java.util.List;

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
        List<Integer> orderedIds = Service.monthService().getMonthsOrder();
        int selectedMonthIndex = orderedIds.indexOf(getConfiguration().getSelectedMonthId());
        int modifier = (increase) ? 1 : -1;
        getConfiguration().selectMonth(orderedIds.get(selectedMonthIndex + modifier));
    }
}
