package org.kaleta.scheduler.frontend.action.mouse;

import org.kaleta.scheduler.backend.entity.Item;
import org.kaleta.scheduler.frontend.Configurable;
import org.kaleta.scheduler.frontend.Configuration;
import org.kaleta.scheduler.frontend.dialog.AddEditItemDialog;
import org.kaleta.scheduler.service.Service;

import java.awt.Component;
import java.awt.event.MouseEvent;

/**
 * Created by Stanislav Kaleta on 11.11.2015.
 */
public class AccountingPanelAddItemClicked extends MouseAction{

    public AccountingPanelAddItemClicked(Configurable configurable) {
        super(configurable);
    }

    @Override
    protected void actionPerformed(MouseEvent e) {
        AddEditItemDialog dialog = new AddEditItemDialog(Service.itemService().getItemTypes(),
                Service.itemService().getRecentlyUsedItems(getConfiguration().getSelectedMonthId()));
        dialog.setLocationRelativeTo((Component) getConfiguration());
        dialog.setVisible(true);

        if (dialog.getResult()) {
            Item item = dialog.getCreatedItem();
            Configuration config = getConfiguration();
            Service.itemService().addItem(item, config.getSelectedMonthId(), config.getSelectedDayNumber());
            getConfiguration().update(Configuration.ITEM_CHANGED);
        }
    }
}
