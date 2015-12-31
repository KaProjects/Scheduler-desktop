package org.kaleta.scheduler.backend.manager;

import org.kaleta.scheduler.backend.entity.Month;

/**
 *  Created by Stanislav Kaleta on 23.07.2015.
 *
 *  Provides basic CRUD operations for Month entity.
 */
public interface MonthManager {

    /**
     * Creates new Month in data source.
     * @throws ManagerException
     */
    void createMonth(Month month) throws ManagerException;

    /**
     * Retrieves specific Month from data source.
     * @param id of wanted month
     * @throws ManagerException
     */
    Month retrieveMonth(Integer id) throws ManagerException;

    /**
     * Updates specific Month in data source.
     * @throws ManagerException
     */
    void updateMonth(Month month) throws ManagerException;

    /**
     * Deletes specific Month from data source.
     * @throws ManagerException
     */
    void deleteMonth(Month month) throws ManagerException;
}
