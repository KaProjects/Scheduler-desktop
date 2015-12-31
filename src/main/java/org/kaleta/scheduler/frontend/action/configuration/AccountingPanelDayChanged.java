package org.kaleta.scheduler.frontend.action.configuration;

import org.kaleta.scheduler.backend.entity.Day;
import org.kaleta.scheduler.frontend.Configurable;
import org.kaleta.scheduler.frontend.panel.AccountingPanelTableModel;
import org.kaleta.scheduler.service.Service;

import javax.swing.*;

/**
 * Created by Stanislav Kaleta on 11.11.2015.
 */
public class AccountingPanelDayChanged extends ConfigurationAction {
    private JComponent target;
    private JTable table;

    public AccountingPanelDayChanged(JComponent target, JTable table) {
        super((Configurable) target);
        this.target = target;
        this.table = table;
    }

    @Override
    protected void actionPerformed() {
        Day day = Service.dayService().getDay(getConfiguration().getSelectedDayNumber(), getConfiguration().getSelectedMonthId());
        ((AccountingPanelTableModel) table.getModel()).setData(day.getItems());
        table.clearSelection();
        table.revalidate();
        table.repaint();
        target.revalidate();
        target.repaint();
    }
}
