package org.kaleta.scheduler.frontend.common;

import org.kaleta.scheduler.frontend.Initializer;

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
                } catch (Exception/*ServiceFailureException*/ e){
                    // TODO handle runtime gui exceptions
                    Initializer.LOG.severe("TODO: SWING WORKER EXCEPTION");
                    //throw new ServiceFailureException(e);
                }
                return null;
            }
        }.execute();
    }

    protected abstract void runInBackground();
}
