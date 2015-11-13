package org.kaleta.scheduler.frontend.action.menu;

import org.kaleta.scheduler.frontend.Configuration;
import org.kaleta.scheduler.frontend.Initializer;

import javax.swing.*;
import java.awt.Component;

/**
 * Created by Stanislav Kaleta on 13.11.2015.
 */
public class OpenAboutDialog extends MenuAction{

    public OpenAboutDialog(Configuration config) {
        super(config, "About");
    }

    @Override
    protected void actionPerformed() {
        StringBuilder sb = new StringBuilder();
        sb.append("name: " + Initializer.NAME + "\n");
        sb.append("version: " + Initializer.VERSION + "\n\n");
        sb.append("developer: Stanislav Kaleta \n");
        sb.append("contact: kstanleykale@gmail.com \n\n");
        sb.append("testers: Stanislav Kaleta, Ludmila Florekova \n\n");
        sb.append("\u00a9 2014 - 2015 Stanislav Kaleta All rights reserved");

        JOptionPane.showMessageDialog((Component) getConfiguration(), sb.toString(), "About", JOptionPane.PLAIN_MESSAGE);
    }
}
