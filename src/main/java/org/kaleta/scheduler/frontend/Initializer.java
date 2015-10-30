package org.kaleta.scheduler.frontend;

import org.kaleta.scheduler.backend.entity.*;
import org.kaleta.scheduler.service.Service;
import org.kaleta.scheduler.service.ServiceFailureException;
import org.kaleta.scheduler.frontend.common.ErrorDialog;
import org.kaleta.scheduler.frontend.common.LogFormatter;
import org.kaleta.scheduler.frontend.wizard.WizardDialog;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Author: Stanislav Kaleta
 * Date: 23.7.2015
 */
public class Initializer {
    public static final String NAME = "Scheduler";
    public static final String VERSION = "2.0-BETA";
    public static final String DATA_SOURCE = new File(Initializer.class.getProtectionDomain()
            .getCodeSource().getLocation().getPath())/**/.getParentFile().getPath()
            + "/" + NAME + "-" + VERSION + "-DATA/";
    public static final Logger LOG = Logger.getLogger("Logger");

    private static void checkResources() {
        File dataSourceFile = new File(DATA_SOURCE);
        if (!dataSourceFile.exists()) {
            boolean result = dataSourceFile.mkdir();
            if (result) {
                System.out.println("# Data directory \"" + dataSourceFile.getName() + "\" created!");
            } else {
                System.err.println("ERROR: Data directory creation failed!");
                throw new ServiceFailureException("Data directory creation failed!");
            }
        }

        try {
            File logFile = new File(DATA_SOURCE + "log.log");
            if (!logFile.exists()) {
                boolean result = logFile.createNewFile();
                if (result) {
                    System.out.println("# Log file \"%DATA%/" + logFile.getName() + "\" created!");
                } else {
                    System.err.println("ERROR: Log file creation failed!");
                    throw new ServiceFailureException("Log file creation failed!");
                }
            }
            FileHandler fh = new FileHandler(logFile.getCanonicalPath(), true);
            SimpleFormatter formatter = new LogFormatter();
            fh.setFormatter(formatter);
            LOG.addHandler(fh);
            LOG.setLevel(Level.INFO);
            LOG.setUseParentHandlers(false);
        } catch (IOException e) {
            System.err.println("ERROR: Setting the logger failed!");
            throw new ServiceFailureException("Setting the logger failed!");
        }

        File monthsFile = new File(DATA_SOURCE + "months-database/");
        if (!monthsFile.exists()) {
            boolean result = monthsFile.mkdir();
            if (result) {
                LOG.info("Months directory \"%DATA%/" + monthsFile.getName() + "/\" created!");
            } else {
                LOG.severe("Months directory creation failed!");
                throw new ServiceFailureException("Months directory creation failed!");
            }
        }

        File settingsFile = new File(DATA_SOURCE + "settings.xml");
        if (!settingsFile.exists()) {
            Service service = new Service();
            service.createSettings();
            LOG.info("Settings file \"%DATA%/" + settingsFile.getName() + "\" created!");

        }

        File globalFile = new File(DATA_SOURCE + "global.xml");
        if (!globalFile.exists()) {
            Service service = new Service();
            service.createGlobal();
            LOG.info("Global file \"%DATA%/" + globalFile.getName() + "\" created!");

        }
    }

    private static void checkFirstUse() {
        Service service = new Service();
        if (service.isFirstUse()) {
            WizardDialog wizardDialog = new WizardDialog();
            wizardDialog.setVisible(true);
            if (wizardDialog.getResult()) {
                Settings settings = new Settings();
                settings.setFirstUse(Boolean.FALSE);
                settings.setLastMonthSelectedId(-1);
                settings.setLastDaySelected(-1);

                String[] userSettings = wizardDialog.getContentPanel().getUserSettings();
                settings.setUserName(userSettings[0]);
                settings.setUiSchemeSelected(userSettings[1]);
                settings.setCurrency(userSettings[2]);
                settings.setLanguage(userSettings[3]);

                settings.getTaskTypes().addAll(wizardDialog.getContentPanel().getTaskTypes());

                settings.getItemTypes().addAll(wizardDialog.getContentPanel().getItemTypes());

                settings.getGlobalTaskTypes().addAll(wizardDialog.getContentPanel().getGlobalTaskTypes());

                service.updateSettings(settings);
            } else {
                LOG.info("Initial Settings was not completed!");
                System.exit(1);
            }
        }
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    checkResources();
                    checkFirstUse();

                    new AppFrame().setVisible(true);

                } catch (ServiceFailureException e) {
                    JDialog errorDialog = new ErrorDialog(e);
                    errorDialog.setVisible(true);
                    System.exit(1);
                }
            }
        });




    }
}
