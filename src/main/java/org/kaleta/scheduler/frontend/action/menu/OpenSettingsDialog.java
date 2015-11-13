package org.kaleta.scheduler.frontend.action.menu;

import org.kaleta.scheduler.backend.entity.Settings;
import org.kaleta.scheduler.frontend.Configuration;
import org.kaleta.scheduler.frontend.dialog.SettingsDialog;
import org.kaleta.scheduler.service.Service;

import java.awt.Component;

/**
 * Created by Stanislav Kaleta on 11.11.2015.
 */
public class OpenSettingsDialog extends MenuAction{

    public OpenSettingsDialog(Configuration config) {
        super(config, "Settings...");
    }

    @Override
    protected void actionPerformed() {
        SettingsDialog dialog = new SettingsDialog();

        Settings settings = Service.configService().getSettings();
        String[] actuallyFilledSettings = new String[4];
        actuallyFilledSettings[0] = settings.getUserName();
        actuallyFilledSettings[1] = settings.getUiSchemeSelected();
        actuallyFilledSettings[2] = settings.getCurrency();
        actuallyFilledSettings[3] = settings.getLanguage();

        dialog.getContentPanel().setUserSettings(actuallyFilledSettings);

        dialog.getContentPanel().setTaskTypes(settings.getTaskTypes());
        dialog.getContentPanel().setItemTypes(settings.getItemTypes());
        dialog.getContentPanel().setGlobalTaskTypes(settings.getGlobalTaskTypes());

        dialog.setLocationRelativeTo((Component) getConfiguration());
        dialog.setVisible(true);

        if (dialog.getResult()){
            String[] userSettings = dialog.getContentPanel().getUserSettings();
            settings.setUserName(userSettings[0]);
            settings.setUiSchemeSelected(userSettings[1]);
            settings.setCurrency(userSettings[2]);
            settings.setLanguage(userSettings[3]);

            settings.getTaskTypes().clear();
            settings.getTaskTypes().addAll(dialog.getContentPanel().getTaskTypes());

            settings.getItemTypes().clear();
            settings.getItemTypes().addAll(dialog.getContentPanel().getItemTypes());

            settings.getGlobalTaskTypes().clear();
            settings.getGlobalTaskTypes().addAll(dialog.getContentPanel().getGlobalTaskTypes());

            Service.configService().updateSettings(settings);
            getConfiguration().applySettings();
        }
    }
}
