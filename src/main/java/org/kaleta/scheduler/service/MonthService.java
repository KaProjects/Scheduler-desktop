package org.kaleta.scheduler.service;

import org.kaleta.scheduler.backend.entity.Global;
import org.kaleta.scheduler.backend.entity.Month;
import org.kaleta.scheduler.backend.manager.GlobalManager;
import org.kaleta.scheduler.backend.manager.ManagerException;
import org.kaleta.scheduler.backend.manager.MonthManager;
import org.kaleta.scheduler.backend.manager.jaxb.JaxbGlobalManager;
import org.kaleta.scheduler.backend.manager.jaxb.JaxbMonthManager;
import org.kaleta.scheduler.frontend.Initializer;

import java.util.Map;

/**
 * Created by Stanislav Kaleta on 30.10.2015.
 */
class MonthService {

    /**
     * TODO documentation
     * @return
     */
    public Map<Integer,Integer> getMonthsOrder(){
        try {
            GlobalManager manager = new JaxbGlobalManager();
            Global global = manager.retrieveGlobal();
            //TODO sort via order in case to unordered data
            return global.getMonths();
        } catch (ManagerException e) {
            Initializer.LOG.severe(e.getMessage());
            throw new ServiceFailureException(e);
        }

    }

    /**
     * TODO documentation
     * @param monthId
     * @return
     */
    public String getMonthName(int monthId) {
        try {
            MonthManager manager = new JaxbMonthManager();
            Month month = manager.retrieveMonth(monthId);
            return month.getName();
        } catch (ManagerException e) {
            Initializer.LOG.severe(e.getMessage());
            throw new ServiceFailureException(e);
        }
    }
}
