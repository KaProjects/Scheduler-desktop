package org.kaleta.scheduler.backend.manager;


import org.kaleta.scheduler.backend.entity.Global;

/**
 * Author: Stanislav Kaleta
 * Date: 23.7.2015
 */
public interface GlobalManager {

    /**
     *
     * @throws ManagerException
     */
    void createGlobal() throws ManagerException;

    /**
     *
     * @return
     * @throws ManagerException
     */
    Global retrieveGlobal() throws ManagerException;

    /**
     *
     * @param global
     * @throws ManagerException
     */
    void updateGlobal(Global global) throws ManagerException;

    /**
     *
     * @throws ManagerException
     */
    void deleteGlobal() throws ManagerException;
}
