package org.kaleta.scheduler.frontend.action;

import org.kaleta.scheduler.frontend.Configurable;
import org.kaleta.scheduler.frontend.ConfigurationAction;
import org.kaleta.scheduler.frontend.common.SwingWorkerHandler;
import org.kaleta.scheduler.service.MonthService;
import org.kaleta.scheduler.service.Service;

import javax.swing.*;
import java.util.Map;

/**
 * TODO documentation about actionPerformed
 * Created by Stanislav Kaleta on 30.10.2015.
 */
public class GlobalPanelMonthChangedAction extends ConfigurationAction {
    private JComponent componentBack;
    private JLabel labelMonthName;
    private JComponent componentNext;

    public GlobalPanelMonthChangedAction(Configurable configurable, JComponent componentBack,
                                         JLabel labelMonthName, JComponent componentNext){
        super(configurable);
        this.componentBack = componentBack;
        this.labelMonthName = labelMonthName;
        this.componentNext = componentNext;
    }

    @Override
    protected void actionPerformed() {
        Service service = new Service();
        Map<Integer, Integer> orders = service.getMonthService().getMonthsOrder();

        int monthId = getConfigurable().getConfiguration().getSelectedMonthId();

        Integer order = orders.get(monthId);

        componentBack.setVisible(false);
        componentNext.setVisible(false);
        for (Integer key : orders.keySet()) {
            if (orders.get(key) > order) {
                componentNext.setVisible(true);
            }
            if (orders.get(key) < order) {
                componentBack.setVisible(true);
            }
        }

        labelMonthName.setText(service.getMonthService().getMonthName(monthId));
    }
}
