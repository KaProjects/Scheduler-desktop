package org.kaleta.scheduler.backend.manager;

import org.kaleta.scheduler.backend.entity.Month;

/**
 * Author: Stanislav Kaleta
 * Date: 23.7.2015
 */
public interface MonthManager {

    /**
     *
     * @param month
     * @throws ManagerException
     */
    public void createMonth(Month month) throws ManagerException;

    /**
     *
     * @param id
     * @return
     * @throws ManagerException
     */
    public Month retrieveMonth(Integer id) throws ManagerException;

    /**
     *
     * @param month
     * @throws ManagerException
     */
    public void updateMonth(Month month) throws ManagerException;

    /**
     *
     * @param month
     * @throws ManagerException
     */
    public void deleteMonth(Month month) throws ManagerException;
}
