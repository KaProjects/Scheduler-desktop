package org.kaleta.scheduler.frontend.common;

import org.kaleta.scheduler.frontend.GuiModel;
import org.kaleta.scheduler.frontend.common.ErrorDialog;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by Stanislav Kaleta on 21.08.2015.
 */
public abstract class ComponentAction extends AbstractAction {
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        GuiModel model = (GuiModel) actionEvent.getSource();
        new SwingWorker<Void,Void>() {
            @Override
            protected Void doInBackground() {
                try {
                    actionPerformed(model);
                } catch (Exception e){
                    JDialog errorDialog = new ErrorDialog(e);
                    errorDialog.setVisible(true);
                }
                return null;
            }
        }.execute();
    }

    /**
     * TODO documentation
     *  dont forget (Usage): first beckend action
     *      - if fail - nothing happend
     *      - if success - continue with updating comps
     * @param guiModel
     */
    protected abstract void actionPerformed(GuiModel guiModel);
}
