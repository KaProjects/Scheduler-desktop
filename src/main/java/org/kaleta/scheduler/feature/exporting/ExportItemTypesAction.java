package org.kaleta.scheduler.feature.exporting;

import org.kaleta.scheduler.frontend.Configuration;
import org.kaleta.scheduler.frontend.action.menu.MenuAction;

import java.awt.Component;

/**
 * Created by Stanislav Kaleta on 29.12.2015.
 */
public class ExportItemTypesAction extends MenuAction {

    public ExportItemTypesAction(Configuration config) {
        super(config, "Item types");
    }

    @Override
    protected void actionPerformed() {
        ExportItemTypesDialog dialog = new ExportItemTypesDialog();
        dialog.setLocationRelativeTo((Component) getConfiguration());
        dialog.setVisible(true);
    }
}
