package org.kaleta.scheduler.backend.manager;


import org.kaleta.scheduler.backend.entity.Settings;

/**
 *  Created by Stanislav Kaleta on 24.07.2015.
 *
 *  Provides basic CRUD operations for Settings entity.
 */
public interface SettingsManager {

    /**
     * Creates Settings in data source.
     * @throws ManagerException
     */
    void createSettings() throws ManagerException;

    /**
     * Retrieves Settings from data source.
     * @throws ManagerException
     */
    Settings retrieveSettings() throws ManagerException;

    /**
     * Updates Settings in data source.
     * @throws ManagerException
     */
    void updateSettings(Settings settings) throws ManagerException;

    /**
     * Deletes Settings from data source.
     * @throws ManagerException
     */
    void deleteSettings() throws ManagerException;
}
