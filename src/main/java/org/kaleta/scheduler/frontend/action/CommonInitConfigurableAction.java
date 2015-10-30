package org.kaleta.scheduler.frontend.action;

import org.kaleta.scheduler.frontend.Configurable;
import org.kaleta.scheduler.frontend.Configuration;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by Stanislav Kaleta on 30.10.2015.
 */
public class CommonInitConfigurableAction extends AbstractAction {
    private Configurable configurable;

    public CommonInitConfigurableAction(Configurable configurable){
        this.configurable = configurable;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        Configuration configuration = (Configuration) actionEvent.getSource();
        configurable.setConfiguration(configuration);
    }
}
