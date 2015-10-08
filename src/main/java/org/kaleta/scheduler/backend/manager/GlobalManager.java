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
    public void createGlobal() throws ManagerException;

    /**
     *
     * @return
     * @throws ManagerException
     */
    public Global retrieveGlobal() throws ManagerException;

    /**
     *
     * @param global
     * @throws ManagerException
     */
    public void updateGlobal(Global global) throws ManagerException;

    /**
     *
     * @throws ManagerException
     */
    public void deleteGlobal() throws ManagerException;
}
