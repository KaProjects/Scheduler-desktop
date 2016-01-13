package org.kaleta.scheduler.frontend.common;

import org.kaleta.scheduler.frontend.Initializer;
import org.kaleta.scheduler.service.ServiceFailureException;

import javax.swing.*;

/**
 * Created by Stanislav Kaleta on 30.10.2015.
 *
 * Handles executing in Swing Worker thread. Every GUI action should extend this class.
 */
public abstract class SwingWorkerHandler {

    public void execute(){
        new SwingWorker<Void,Void>() {
            @Override
            protected Void doInBackground() {
                try {
                    runInBackground();
                } catch (Exception e){
                    ErrorDialog errorDialog = new ErrorDialog(e);
                    if (!(e instanceof ServiceFailureException)){
                        Initializer.LOG.severe(errorDialog.getExceptionStackTrace());
                    }
                    // No need to log SFEx. cause its (should be) always logged before its thrown.
                    errorDialog.setVisible(true);
                }
                return null;
            }
        }.execute();
    }

    protected abstract void runInBackground();
}
