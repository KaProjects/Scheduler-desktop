package org.kaleta.scheduler.service;

import org.kaleta.scheduler.backend.entity.Global;
import org.kaleta.scheduler.backend.entity.Month;
import org.kaleta.scheduler.backend.manager.GlobalManager;
import org.kaleta.scheduler.backend.manager.ManagerException;
import org.kaleta.scheduler.backend.manager.MonthManager;
import org.kaleta.scheduler.backend.manager.jaxb.JaxbGlobalManager;
import org.kaleta.scheduler.backend.manager.jaxb.JaxbMonthManager;
import org.kaleta.scheduler.frontend.Initializer;
import org.kaleta.scheduler.frontend.common.ErrorDialog;

import java.util.Map;

/**
 * Created by Stanislav Kaleta on 30.10.2015.
 *
 * Provides access to data source which is related to months.
 */
public class MonthService {

    MonthService(){
        // package private class
    }

    /**
     * Creates new month in data source.
     */
    public void createMonth(Month month) {
        try {
            GlobalManager globalManager = new JaxbGlobalManager();
            Global global = globalManager.retrieveGlobal();
            int lastId = 0;
            int lastOrder = 0;
            for (Integer usedId : global.getMonths().keySet()){
                if (usedId > lastId){
                    lastId = usedId;
                }
                if (global.getMonths().get(usedId) > lastOrder){
                    lastOrder = global.getMonths().get(usedId);
                }
            }
            int newId = lastId + 1;
            int newOrder = lastOrder + 1;
            month.setId(newId);

            MonthManager manager = new JaxbMonthManager();
            manager.createMonth(month);

            global.getMonths().put(newId, newOrder);
            globalManager.updateGlobal(global);
        } catch (ManagerException e) {
            Initializer.LOG.severe(ErrorDialog.getExceptionStackTrace(e));
            throw new ServiceFailureException(e);
        }
    }

    /**
     * Returns ordering of months as Map (key = month id, value = month order).
     */
    public Map<Integer,Integer> getMonthsOrder(){
        try {
            GlobalManager manager = new JaxbGlobalManager();
            Global global = manager.retrieveGlobal();
            return global.getMonths();
        } catch (ManagerException e) {
            Initializer.LOG.severe(ErrorDialog.getExceptionStackTrace(e));
            throw new ServiceFailureException(e);
        }

    }

    /**
     * Returns name of month with specified id.
     * @param monthId - id of month which name has to be returned
     */
    public String getMonthName(int monthId) {
        try {
            MonthManager manager = new JaxbMonthManager();
            Month month = manager.retrieveMonth(monthId);
            return month.getName();
        } catch (ManagerException e) {
            Initializer.LOG.severe(ErrorDialog.getExceptionStackTrace(e));
            throw new ServiceFailureException(e);
        }
    }
}
