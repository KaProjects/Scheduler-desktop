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
import org.kaleta.scheduler.frontend.wizard.WizardDialog;

import java.io.File;
import java.io.IOException;

/**
 * Created by Stanislav Kaleta on 13.11.2015.
 */
public class ConfigService {

    ConfigService() {
        // package private class
    }

    /**
     * TODO documentation
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
     * TODO documentation (create set,glob + try to load all months)
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
     * TODO documentation
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
            Initializer.LOG.severe(e.getMessage());
            throw new ServiceFailureException(e);
        }
    }

    /**
     * TODO documentation
     * @param settings
     */
    public void updateSettings(Settings settings) {
        try {
            SettingsManager manager = new JaxbSettingsManager();
            manager.updateSettings(settings);
        } catch (ManagerException e) {
            Initializer.LOG.severe(e.getMessage());
            throw new ServiceFailureException(e);
        }
    }

    /**
     * TODO documentation
     * @return
     */
    public Settings getSettings() {
        try {
            SettingsManager manager = new JaxbSettingsManager();
            return manager.retrieveSettings();
        } catch (ManagerException e) {
            Initializer.LOG.severe(e.getMessage());
            throw new ServiceFailureException(e);
        }
    }

    /**
     * TODO documentation
     * @param currentlySelectedMonthId
     */
    public void monthChanged(Integer currentlySelectedMonthId){
        try {
            // need to check if month exists, no just set it
            // (otherwise causing many problems in gui)
            new JaxbMonthManager().retrieveMonth(currentlySelectedMonthId);
            SettingsManager manager = new JaxbSettingsManager();
            Settings settings = manager.retrieveSettings();
            settings.setLastMonthSelectedId(currentlySelectedMonthId);
            manager.updateSettings(settings);
        } catch (ManagerException e) {
            Initializer.LOG.severe(e.getMessage());
            throw new ServiceFailureException(e);
        }
    }

    /**
     * TODO documentation
     * @param currentlySelectedDayNumber
     */
    public void dayChanged(Integer currentlySelectedDayNumber){
        try {
            SettingsManager manager = new JaxbSettingsManager();
            Settings settings = manager.retrieveSettings();
            settings.setLastDaySelected(currentlySelectedDayNumber);
            manager.updateSettings(settings);
        } catch (ManagerException e) {
            Initializer.LOG.severe(e.getMessage());
            throw new ServiceFailureException(e);
        }
    }
}
