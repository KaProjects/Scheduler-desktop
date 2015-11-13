package org.kaleta.scheduler.frontend.action.mouse;

import org.kaleta.scheduler.frontend.Configurable;
import org.kaleta.scheduler.frontend.Configuration;
import org.kaleta.scheduler.frontend.common.SwingWorkerHandler;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Stanislav Kaleta on 11.11.2015.
 */
abstract public class MouseAction extends MouseAdapter {
    private Configurable configurable;

    public MouseAction(Configurable configurable){
        this.configurable = configurable;
    }

    @Override
    public void mouseClicked(MouseEvent e)  {
        new SwingWorkerHandler() {
            @Override
            protected void runInBackground() {
                actionPerformed(e);
            }
        }.execute();
    }

    protected abstract void actionPerformed(MouseEvent e);

    protected Configuration getConfiguration() {
        return configurable.getConfiguration();
    }
}
