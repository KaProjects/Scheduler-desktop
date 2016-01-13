package org.kaleta.scheduler.frontend.action.configuration;

import org.kaleta.scheduler.frontend.Configurable;
import org.kaleta.scheduler.service.Service;

import javax.swing.*;
import java.util.List;

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
        List<Integer> orderedIds = Service.monthService().getMonthsOrder();
        Integer selectedMonthId = getConfiguration().getSelectedMonthId();

        if (orderedIds.get(orderedIds.size() - 1).equals(selectedMonthId)) {
            componentNext.setVisible(false);
        } else {
            componentNext.setVisible(true);
        }
        if (orderedIds.get(0).equals(selectedMonthId)) {
            componentBack.setVisible(false);
        } else {
            componentBack.setVisible(true);
        }

        labelMonthName.setText(Service.monthService().getMonthName(selectedMonthId));
    }
}
