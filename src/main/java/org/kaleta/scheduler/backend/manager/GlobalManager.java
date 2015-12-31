package org.kaleta.scheduler.backend.manager;


import org.kaleta.scheduler.backend.entity.Global;

/**
 *  Created by Stanislav Kaleta on 23.07.2015.
 *
 *  Provides basic CRUD operations for Global entity.
 */
public interface GlobalManager {

    /**
     * Creates Global in data source.
     * @throws ManagerException
     */
    void createGlobal() throws ManagerException;

    /**
     * Retrieves Global from data source.
     * @throws ManagerException
     */
    Global retrieveGlobal() throws ManagerException;

    /**
     * Updates Global in data source.
     * @throws ManagerException
     */
    void updateGlobal(Global global) throws ManagerException;

    /**
     * Deletes Global from data source.
     * @throws ManagerException
     */
    void deleteGlobal() throws ManagerException;
}
