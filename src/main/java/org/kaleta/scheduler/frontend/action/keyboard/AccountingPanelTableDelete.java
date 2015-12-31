package org.kaleta.scheduler.frontend.action.keyboard;

import org.kaleta.scheduler.backend.entity.Item;
import org.kaleta.scheduler.frontend.Configurable;
import org.kaleta.scheduler.frontend.Configuration;
import org.kaleta.scheduler.service.Service;

import javax.swing.*;
import java.awt.event.KeyEvent;

/**
 * Created by Stanislav Kaleta on 11.11.2015.
 */
public class AccountingPanelTableDelete extends KeyboardAction {
    private JTable target;

    public AccountingPanelTableDelete(Configurable configurable, JTable target) {
        super(configurable, KeyStroke.getKeyStroke(KeyEvent.VK_DELETE,0));
        this.target = target;
    }

    @Override
    protected void actionPerformed() {
        int selectedRow = target.getSelectedRow();
        if (selectedRow == -1) {
            return;
        }
        int result = JOptionPane.showConfirmDialog(target,"Are you sure?", "Deleting Item", JOptionPane.YES_NO_CANCEL_OPTION);
        if (result == 0) {
            Item itemToDel = (Item) target.getModel().getValueAt(selectedRow, 4);
            Service.itemService().deleteItem(itemToDel, getConfiguration().getSelectedMonthId());
            getConfiguration().update(Configuration.ITEM_CHANGED);
        }
    }
}
