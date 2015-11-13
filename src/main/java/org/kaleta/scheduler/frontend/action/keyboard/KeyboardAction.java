package org.kaleta.scheduler.frontend.action.keyboard;

import org.kaleta.scheduler.frontend.Configurable;
import org.kaleta.scheduler.frontend.Configuration;
import org.kaleta.scheduler.frontend.common.SwingWorkerHandler;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by Stanislav Kaleta on 11.11.2015.
 */
abstract public class KeyboardAction extends KeyAdapter {
    private Configurable configurable;
    private KeyStroke keyStroke;

    public KeyboardAction(Configurable configurable, KeyStroke keyStroke){
        this.configurable = configurable;
        this.keyStroke = keyStroke;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getComponent().hasFocus()
                && KeyStroke.getKeyStroke(e.getKeyChar(),e.getModifiers()).equals(keyStroke)){
            new SwingWorkerHandler() {
                @Override
                protected void runInBackground() {
                    actionPerformed();
                }
            }.execute();
        }
    }



    protected abstract void actionPerformed();

    protected Configuration getConfiguration() {
        return configurable.getConfiguration();
    }
}
