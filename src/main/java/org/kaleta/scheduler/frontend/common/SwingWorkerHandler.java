package org.kaleta.scheduler.frontend.common;

import org.kaleta.scheduler.service.ServiceFailureException;

import javax.swing.*;

/**
 * Created by Stanislav Kaleta on 30.10.2015.
 */
public abstract class SwingWorkerHandler {

    public void execute(){
        new SwingWorker<Void,Void>() {
            @Override
            protected Void doInBackground() {
                try {
                    runInBackground();
                } catch (ServiceFailureException e){
                    // No need to log here. Cause is (should be) always logged before SFEx is threw.
                    JDialog errorDialog = new ErrorDialog(e);
                    errorDialog.setVisible(true);
                }
                return null;
            }
        }.execute();
    }

    protected abstract void runInBackground();
}
