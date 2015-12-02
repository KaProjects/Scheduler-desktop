package org.kaleta.scheduler.backend.manager;


import org.kaleta.scheduler.backend.entity.Settings;

/**
 * Author: Stanislav Kaleta
 * Date: 24.7.2015
 */
public interface SettingsManager {

    /**
     *
     * @throws ManagerException
     */
    void createSettings() throws ManagerException;

    /**
     *
     * @return
     * @throws ManagerException
     */
    Settings retrieveSettings() throws ManagerException;

    /**
     *
     * @param settings
     * @throws ManagerException
     */
    void updateSettings(Settings settings) throws ManagerException;

    /**
     *
     * @throws ManagerException
     */
    void deleteSettings() throws ManagerException;
}
