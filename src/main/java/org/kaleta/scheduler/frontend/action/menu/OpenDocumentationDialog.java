package org.kaleta.scheduler.frontend.action.menu;

import org.kaleta.scheduler.frontend.Configuration;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by Stanislav Kaleta on 11.11.2015.
 */
public class OpenDocumentationDialog extends MenuAction{
    public OpenDocumentationDialog(Configuration config) {
        super(config, "Documentation");
        this.setEnabled(false);
    }

    @Override
    protected void actionPerformed() {
        throw new NotImplementedException();
        //TODO implement
        /*TODO somehow open html file with doc.(devel doc + user guide)*/
        /*TODO to version 2.y*/
    }
}
