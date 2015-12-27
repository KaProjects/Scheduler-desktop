package org.kaleta.scheduler.frontend.action.mouse;

import org.kaleta.scheduler.backend.entity.Item;
import org.kaleta.scheduler.frontend.Configurable;
import org.kaleta.scheduler.frontend.Configuration;
import org.kaleta.scheduler.frontend.dialog.AddEditItemDialog;
import org.kaleta.scheduler.service.Service;

import javax.swing.*;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 * Created by Stanislav Kaleta on 11.11.2015.
 */
public class AccountingPanelTableClicked extends MouseAction{
    private JTable target;

    public AccountingPanelTableClicked(Configurable configurable, JTable target) {
        super(configurable);
        this.target = target;
    }

    @Override
    protected void actionPerformed(MouseEvent e) {
        if (e.getClickCount() == 2) {
            AddEditItemDialog dialog = new AddEditItemDialog(Service.itemService().getItemTypes(), new ArrayList<>());
            Item itemToEdit = (Item) target.getModel().getValueAt(target.getSelectedRow(), 4);
            dialog.setItem(itemToEdit);
            dialog.setLocationRelativeTo((Component) getConfiguration());
            dialog.setVisible(true);

            if (dialog.getResult()) {
                Item createdItem = dialog.getCreatedItem();
                itemToEdit.setIncome(createdItem.getIncome());
                itemToEdit.setAmount(createdItem.getAmount());
                itemToEdit.setType(createdItem.getType());
                itemToEdit.setDescription(createdItem.getDescription());
                Service.itemService().updateItem(itemToEdit, getConfiguration().getSelectedMonthId());
                getConfiguration().update(Configuration.ITEM_CHANGED);
            }
        }
    }
}
