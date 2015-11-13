package org.kaleta.scheduler.frontend.action.menu;

import org.kaleta.scheduler.frontend.Configuration;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by Stanislav Kaleta on 11.11.2015.
 */
public class OpenEditMonthDialog extends MenuAction {

    public OpenEditMonthDialog(Configuration config) {
        super(config, "Edit...");
        this.setEnabled(false);

    }

    @Override
    protected void actionPerformed() {
        throw new NotImplementedException();
        //TODO implement
        //TODO open edit dialog (+throw month changed action - cuz month control update)
        //TODO edit month params + edit order
    }
}
