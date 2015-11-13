package org.kaleta.scheduler.frontend.action.configuration;

import org.kaleta.scheduler.frontend.Configurable;
import org.kaleta.scheduler.frontend.Configuration;
import org.kaleta.scheduler.frontend.common.SwingWorkerHandler;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by Stanislav Kaleta on 21.08.2015.
 */
public abstract class ConfigurationAction extends AbstractAction {
    private Configurable configurable;

    public ConfigurationAction(Configurable configurable){
      this.configurable = configurable;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        new SwingWorkerHandler() {
            @Override
            protected void runInBackground() {
                actionPerformed();
            }
        }.execute();
    }

    protected abstract void actionPerformed();

    protected Configuration getConfiguration() {
        return configurable.getConfiguration();
    }
}
