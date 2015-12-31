package org.kaleta.scheduler.service;

import org.kaleta.scheduler.backend.entity.Day;
import org.kaleta.scheduler.backend.entity.Item;
import org.kaleta.scheduler.backend.entity.Month;
import org.kaleta.scheduler.backend.entity.Task;
import org.kaleta.scheduler.backend.manager.ManagerException;
import org.kaleta.scheduler.backend.manager.MonthManager;
import org.kaleta.scheduler.backend.manager.jaxb.JaxbMonthManager;
import org.kaleta.scheduler.frontend.Initializer;
import org.kaleta.scheduler.frontend.common.ErrorDialog;

import java.awt.Point;

/**
 * Created by Stanislav Kaleta on 30.10.2015.
 *
 *  Provides access to data source which is related to days.
 */
public class DayService {

    DayService(){
        // package private class
    }

    /**
     * Returns day which is at specified position in day/week map.
     * @param position - position of day, where x = day in week and y = number of week
     * @param monthId - id of related month
     */
    public Day getDayAt(Point position, Integer monthId) {
        try {
            MonthManager manager = new JaxbMonthManager();
            Month month = manager.retrieveMonth(monthId);

            int dayNumber = 7 * (position.y - 1) + position.x - (month.getDayStartsWith() - 1);
            if (dayNumber < 1){
                dayNumber = -1;
            }
            if (dayNumber > month.getDaysNumber()){
                dayNumber = -1;
            }

            return getDay(dayNumber, monthId);
        } catch (ManagerException e) {
            Initializer.LOG.severe(ErrorDialog.getExceptionStackTrace(e));
            throw new ServiceFailureException(e);
        }
    }

    /**
     * Returns object of day according to his number and month id.
     * @param dayNumber number of day
     * @param monthId id of month
     */
    public Day getDay(int dayNumber, Integer monthId) {
        try {
            Day day = new Day();

            day.setDayNumber(dayNumber);
            if (dayNumber == -1){
                return day;
            }

            MonthManager manager = new JaxbMonthManager();
            Month month = manager.retrieveMonth(monthId);

            for (Item item : month.getItems()){
                if (item.getDay() == dayNumber){
                    day.getItems().add(item);
                }
            }

            for (Task task : month.getTasks()){
                if (task.getDay() == dayNumber){
                    day.getTasks().add(task);
                }
            }

            day.setPublicFreeDay(Boolean.FALSE);
            for (Integer integer : month.getPublicFreeDays()){
                if (integer == dayNumber){
                    day.setPublicFreeDay(Boolean.TRUE);
                    break;
                }
            }

            return day;
        } catch (ManagerException e) {
            Initializer.LOG.severe(ErrorDialog.getExceptionStackTrace(e));
            throw new ServiceFailureException(e);
        }
    }
}
