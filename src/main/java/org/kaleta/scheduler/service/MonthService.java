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

import java.util.*;
import java.util.stream.Stream;

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
     * Returns array of month ids sorted via order. (index 0 is first month id, index size - 1 is last month id)
     */
    public List<Integer> getMonthsOrder(){
        try {
            GlobalManager manager = new JaxbGlobalManager();
            Global global = manager.retrieveGlobal();
            Map<Integer,Integer> monthsMap = global.getMonths();
            List<Integer> sortedIds = new LinkedList<>();
            Stream<Map.Entry<Integer,Integer>> st = monthsMap.entrySet().stream();
            st.sorted(Comparator.comparing(Map.Entry::getValue)).forEachOrdered(e -> sortedIds.add(e.getValue() - 1, e.getKey()));
            return sortedIds;
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

    /**
     * Retrieves all month from data source. (sorted via order)
     */
    public List<Month> retrieveAllMonths(){
        try {
            List<Integer> sortedIds = new LinkedList<>();
            new JaxbGlobalManager().retrieveGlobal().getMonths().entrySet().stream()
                    .sorted(Comparator.comparing(Map.Entry::getValue))
                    .forEachOrdered(e -> sortedIds.add(e.getValue() - 1, e.getKey()));
            MonthManager manager = new JaxbMonthManager();
            List<Month> allMonths = new ArrayList<>();
            for (Integer id : sortedIds){
                allMonths.add(manager.retrieveMonth(id));
            }
            return allMonths;
        } catch (ManagerException e) {
            Initializer.LOG.severe(ErrorDialog.getExceptionStackTrace(e));
            throw new ServiceFailureException(e);
        }
    }
}
