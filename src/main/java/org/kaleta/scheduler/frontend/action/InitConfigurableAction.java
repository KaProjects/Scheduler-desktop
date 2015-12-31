package org.kaleta.scheduler.frontend.action;

import org.kaleta.scheduler.frontend.Configurable;
import org.kaleta.scheduler.frontend.Configuration;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by Stanislav Kaleta on 30.10.2015.
 *
 * Action which inits configuration in every component which needs it.
 */
public class InitConfigurableAction extends AbstractAction {
    private Configurable configurable;

    public InitConfigurableAction(Configurable configurable){
        this.configurable = configurable;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        Configuration configuration = (Configuration) actionEvent.getSource();
        configurable.setConfiguration(configuration);
    }
}
