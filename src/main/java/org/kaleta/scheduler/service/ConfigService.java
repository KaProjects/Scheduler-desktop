package org.kaleta.scheduler.service;

import org.kaleta.scheduler.backend.entity.Global;
import org.kaleta.scheduler.backend.entity.Settings;
import org.kaleta.scheduler.backend.manager.GlobalManager;
import org.kaleta.scheduler.backend.manager.ManagerException;
import org.kaleta.scheduler.backend.manager.MonthManager;
import org.kaleta.scheduler.backend.manager.SettingsManager;
import org.kaleta.scheduler.backend.manager.jaxb.JaxbGlobalManager;
import org.kaleta.scheduler.backend.manager.jaxb.JaxbMonthManager;
import org.kaleta.scheduler.backend.manager.jaxb.JaxbSettingsManager;
import org.kaleta.scheduler.frontend.Initializer;
import org.kaleta.scheduler.frontend.common.ErrorDialog;
import org.kaleta.scheduler.frontend.wizard.WizardDialog;

import java.io.File;
import java.io.IOException;

/**
 * Created by Stanislav Kaleta on 13.11.2015.
 *
 * Provides access to data source which is related to configuration.
 */
public class ConfigService {

    ConfigService() {
        // package private class
    }

    /**
     * Checks that every resource is alright, throws ServiceFailureException if not.
     */
    public void checkResources() {
        File dataSourceFile = new File(Initializer.DATA_SOURCE);
        if (!dataSourceFile.exists()) {
            boolean result = dataSourceFile.mkdir();
            if (result) {
                System.out.println("# Data directory \"" + dataSourceFile.getName() + "\" created!");
            } else {
                System.err.println("ERROR: Data directory creation failed!");
                throw new ServiceFailureException("Data directory creation failed!");
            }
        }

        File logFile = new File(Initializer.DATA_SOURCE + "log.log");
        if (!logFile.exists()) {
            try {
                boolean result = logFile.createNewFile();
                if (result) {
                    System.out.println("# Log file \"%DATA%/" + logFile.getName() + "\" created!");
                } else {
                    System.err.println("ERROR: Log file creation failed!");
                    throw new ServiceFailureException("Log file creation failed!");
                }
            } catch (IOException e) {
                System.err.println(e.getMessage());
                throw new ServiceFailureException(e);
            }
        }

        File monthsFile = new File(Initializer.DATA_SOURCE + "months-database/");
        if (!monthsFile.exists()) {
            boolean result = monthsFile.mkdir();
            if (result) {
                System.out.println("# Months directory \"%DATA%/" + monthsFile.getName() + "/\" created!");
            } else {
                System.err.println("ERROR: Months directory creation failed!");
                throw new ServiceFailureException("Months directory creation failed!");
            }
        }

        System.out.println("# Resources checked. Everything OK.");
    }

    /**
     * Checks that data are valid, throws ServiceFailureException if not.
     */
    public void checkData(){
        File settingsFile = new File(Initializer.DATA_SOURCE + "settings.xml");
        if (!settingsFile.exists()) {
            try {
                SettingsManager manager = new JaxbSettingsManager();
                manager.createSettings();
                Initializer.LOG.info("Settings file \"%DATA%/" + settingsFile.getName() + "\" created!");
            } catch (ManagerException e) {
                Initializer.LOG.severe(e.getMessage());
                throw new ServiceFailureException(e);
            }
        }

        File globalFile = new File(Initializer.DATA_SOURCE + "global.xml");
        if (!globalFile.exists()) {
            try {
                GlobalManager manager = new JaxbGlobalManager();
                manager.createGlobal();
                Initializer.LOG.info("Global file \"%DATA%/" + globalFile.getName() + "\" created!");
            } catch (ManagerException e) {
                Initializer.LOG.severe(e.getMessage());
                throw new ServiceFailureException(e);
            }
        }

        try {
            Global global = new JaxbGlobalManager().retrieveGlobal();

            MonthManager manager = new JaxbMonthManager();
            for (Integer id : global.getMonths().keySet()) {
                manager.retrieveMonth(id);
            }
        } catch (ManagerException e) {
            Initializer.LOG.severe(e.getMessage());
            throw new ServiceFailureException(e);
        }

        Initializer.LOG.info("Data checked. Everything OK.");
    }

    /**
     * Checks whether app. is started for first time or not. If yes, creates necessary resources
     * and shows settings wizard dialog in order to obtains necessary settings.
     */
    public void checkFirstUse() {
        try {
            SettingsManager manager = new JaxbSettingsManager();
            Settings settings = manager.retrieveSettings();

            if (settings.getFirstUse()) {
                WizardDialog wizardDialog = new WizardDialog();
                wizardDialog.setVisible(true);
                if (wizardDialog.getResult()) {
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

                    manager.updateSettings(settings);
                    Initializer.LOG.info("Settings initialization was successfully completed!");
                } else {
                    Initializer.LOG.warning("Settings initialization was not completed!");
                    System.exit(0);
                }
            }
        } catch (ManagerException e) {
            Initializer.LOG.severe(ErrorDialog.getExceptionStackTrace(e));
            throw new ServiceFailureException(e);
        }
    }

    /**
     * Updates whole settings in data source.
     * This method is used only for applying changes after overall editing of settings via its dialog.
     */
    public void updateSettings(Settings settings) {
        try {
            SettingsManager manager = new JaxbSettingsManager();
            manager.updateSettings(settings);
        } catch (ManagerException e) {
            Initializer.LOG.severe(ErrorDialog.getExceptionStackTrace(e));
            throw new ServiceFailureException(e);
        }
    }

    /**
     * Gets whole settings from data source.
     */
    public Settings getSettings() {
        try {
            SettingsManager manager = new JaxbSettingsManager();
            return manager.retrieveSettings();
        } catch (ManagerException e) {
            Initializer.LOG.severe(ErrorDialog.getExceptionStackTrace(e));
            throw new ServiceFailureException(e);
        }
    }

    /**
     * Saves last selected month's id to data source, so it can be selected in following start of app.
     */
    public void monthChanged(Integer currentlySelectedMonthId){
        try {
            new JaxbMonthManager().retrieveMonth(currentlySelectedMonthId);
            SettingsManager manager = new JaxbSettingsManager();
            Settings settings = manager.retrieveSettings();
            settings.setLastMonthSelectedId(currentlySelectedMonthId);
            manager.updateSettings(settings);
        } catch (ManagerException e) {
            Initializer.LOG.severe(ErrorDialog.getExceptionStackTrace(e));
            throw new ServiceFailureException(e);
        }
    }

    /**
     * Saves last selected day's number to data source, so it can be selected in following start of app.
     */
    public void dayChanged(Integer currentlySelectedDayNumber){
        try {
            SettingsManager manager = new JaxbSettingsManager();
            Settings settings = manager.retrieveSettings();
            settings.setLastDaySelected(currentlySelectedDayNumber);
            manager.updateSettings(settings);
        } catch (ManagerException e) {
            Initializer.LOG.severe(ErrorDialog.getExceptionStackTrace(e));
            throw new ServiceFailureException(e);
        }
    }
}
