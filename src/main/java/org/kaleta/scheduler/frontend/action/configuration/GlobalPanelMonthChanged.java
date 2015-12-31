package org.kaleta.scheduler.frontend.action.configuration;

import org.kaleta.scheduler.frontend.Configurable;
import org.kaleta.scheduler.service.Service;

import javax.swing.*;
import java.util.Map;

/**
 * Created by Stanislav Kaleta on 30.10.2015.
 */
public class GlobalPanelMonthChanged extends ConfigurationAction {
    private JComponent componentBack;
    private JLabel labelMonthName;
    private JComponent componentNext;

    public GlobalPanelMonthChanged(Configurable configurable, JComponent componentBack,
                                   JLabel labelMonthName, JComponent componentNext){
        super(configurable);
        this.componentBack = componentBack;
        this.labelMonthName = labelMonthName;
        this.componentNext = componentNext;
    }

    @Override
    protected void actionPerformed() {
        Map<Integer, Integer> orders = Service.monthService().getMonthsOrder();

        int monthId = getConfiguration().getSelectedMonthId();

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

        labelMonthName.setText(Service.monthService().getMonthName(monthId));
    }
}
