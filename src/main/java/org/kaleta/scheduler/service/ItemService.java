package org.kaleta.scheduler.service;

import org.kaleta.scheduler.backend.entity.Item;
import org.kaleta.scheduler.backend.entity.Month;
import org.kaleta.scheduler.backend.entity.Settings;
import org.kaleta.scheduler.backend.entity.UserType;
import org.kaleta.scheduler.backend.manager.ManagerException;
import org.kaleta.scheduler.backend.manager.MonthManager;
import org.kaleta.scheduler.backend.manager.SettingsManager;
import org.kaleta.scheduler.backend.manager.jaxb.JaxbMonthManager;
import org.kaleta.scheduler.backend.manager.jaxb.JaxbSettingsManager;
import org.kaleta.scheduler.frontend.Initializer;
import org.kaleta.scheduler.frontend.common.ErrorDialog;

import java.util.*;

/**
 * Created by Stanislav Kaleta on 30.10.2015.
 *
 * Provides access to data source which is related to items.
 */
public class ItemService {

    ItemService(){
        // package private class
    }

    /**
     * Returns all item types from data source.
     */
    public List<UserType> getItemTypes() {
        try {
            SettingsManager manager = new JaxbSettingsManager();
            Settings settings = manager.retrieveSettings();
            return settings.getItemTypes();
        } catch (ManagerException e) {
            Initializer.LOG.severe(ErrorDialog.getExceptionStackTrace(e));
            throw new ServiceFailureException(e);
        }
    }

    /**
     * Adds item for specified month and day to data source.
     * @param item - item which has to be added.
     * @param monthId - id of related month
     * @param dayNumber - number of related day
     */
    public void addItem(Item item, Integer monthId, Integer dayNumber) {
        try {
            MonthManager manager = new JaxbMonthManager();
            Month month = manager.retrieveMonth(monthId);

            int newId = 0;
            for (Item existingItem : month.getItems()){
                if (existingItem.getId() > newId){
                    newId = existingItem.getId();
                }
            }
            newId = newId + 1;

            item.setId(newId);
            item.setDay(dayNumber);

            month.getItems().add(item);
            manager.updateMonth(month);
        } catch (ManagerException e) {
            Initializer.LOG.severe(ErrorDialog.getExceptionStackTrace(e));
            throw new ServiceFailureException(e);
        }
    }

    /**
     * Returns 10 item types which have been used the most in specified month until now.
     */
    public List<Item> getRecentlyUsedItems(Integer monthId) {
        try {
            MonthManager manager = new JaxbMonthManager();
            Month month = manager.retrieveMonth(monthId);

            int counter = 0;
            Map<Item,Integer> recentlyUsed = new HashMap<>();
            for (Item item : month.getItems()){
                if (recentlyUsed.size() == 0){
                    recentlyUsed.put(item, 1);
                    counter = 1;
                } else {
                    Set<Item> recentItems = new HashSet<>();
                    recentItems.addAll(recentlyUsed.keySet());
                    boolean set = false;
                    for (Item setItem : recentItems){
                        if (setItem.getIncome().equals(item.getIncome())
                                && setItem.getType().equals(item.getType())
                                && setItem.getDescription().equals(item.getDescription())
                                && setItem.getAmount().equals(item.getAmount())){
                            int nUsed = recentlyUsed.get(setItem) + 1;
                            recentlyUsed.put(setItem,nUsed);
                            if (nUsed > counter){
                                counter = nUsed;
                            }
                            set = true;
                            break;
                        }
                    }
                    if (!set){
                        recentlyUsed.put(item, 1);
                    }
                }
            }

            List<Item> subRecentlyUsed = new ArrayList<>();
            int subEdge = 10;
            while (subEdge > 0 && counter > 0){
                for (Item item : recentlyUsed.keySet()){
                    if (recentlyUsed.get(item).equals(counter) && subEdge > 0){
                        subRecentlyUsed.add(item);
                        subEdge--;
                    }
                }
                counter--;
            }
            return subRecentlyUsed;
        } catch (ManagerException e) {
            Initializer.LOG.severe(ErrorDialog.getExceptionStackTrace(e));
            throw new ServiceFailureException(e);
        }
    }

    /**
     * Updates item which is already in data source.
     * @param updatedItem - item which has to be updated
     * @param monthId - id of related month
     */
    public void updateItem(Item updatedItem, Integer monthId) {
        try {
            MonthManager manager = new JaxbMonthManager();
            Month month = manager.retrieveMonth(monthId);
            boolean foundId = false;
            for (Item item : month.getItems()){
                if (item.getId().equals(updatedItem.getId())){
                    item.setIncome(updatedItem.getIncome());
                    item.setAmount(updatedItem.getAmount());
                    item.setType(updatedItem.getType());
                    item.setDescription(updatedItem.getDescription());
                    foundId = true;
                    break;
                }
            }
            if (!foundId){
                String msg = "Item with id=" + updatedItem.getId() + "was not found in month(id=" + monthId + ")!";
                Initializer.LOG.severe(msg);
                throw new IllegalArgumentException(msg);
            } else {
                manager.updateMonth(month);
            }
        } catch (ManagerException e) {
            Initializer.LOG.severe(ErrorDialog.getExceptionStackTrace(e));
            throw new ServiceFailureException(e);
        }
    }

    /**
     * Deletes item from data source.
     * @param item - item which has to be deleted
     * @param monthId - id of related month
     */
    public void deleteItem(Item item, Integer monthId) {
        try {
            MonthManager manager = new JaxbMonthManager();
            Month month = manager.retrieveMonth(monthId);
            month.getItems().remove(item);
            manager.updateMonth(month);
        } catch (ManagerException e) {
            Initializer.LOG.severe(ErrorDialog.getExceptionStackTrace(e));
            throw new ServiceFailureException(e);
        }
    }

    /**
     * Returns value of daily expense which is maximum for specified month.
     */
    public Integer getMaxDailyExpense(Integer monthId){
        try {
            MonthManager manager = new JaxbMonthManager();
            Month month = manager.retrieveMonth(monthId);

            Map<Integer, Integer> dailyIncomes = new HashMap<>();
            for (Item item : month.getItems()){
                if (!item.getIncome()){
                    if (dailyIncomes.keySet().contains(item.getDay())){
                        dailyIncomes.put(item.getDay(), dailyIncomes.get(item.getDay()) + item.getAmount().intValue());
                    } else {
                        dailyIncomes.put(item.getDay(), item.getAmount().intValue());
                    }
                }
            }
            Integer max = 0;
            for (Integer key : dailyIncomes.keySet()){
                if (dailyIncomes.get(key) > max){
                    max = dailyIncomes.get(key);
                }
            }
            return max;
        } catch (ManagerException e) {
            Initializer.LOG.severe(ErrorDialog.getExceptionStackTrace(e));
            throw new ServiceFailureException(e);
        }
    }

    /**
     * Returns value of daily income which is maximum for specified month.
     */
    public Integer getMaxDailyIncome(Integer monthId){
        try {
            MonthManager manager = new JaxbMonthManager();
            Month month = manager.retrieveMonth(monthId);

            Map<Integer, Integer> dailyIncomes = new HashMap<>();
            for (Item item : month.getItems()){
                if (item.getIncome()){
                    if (dailyIncomes.keySet().contains(item.getDay())){
                        dailyIncomes.put(item.getDay(), dailyIncomes.get(item.getDay()) + item.getAmount().intValue());
                    } else {
                        dailyIncomes.put(item.getDay(), item.getAmount().intValue());
                    }
                }
            }
            Integer max = 0;
            for (Integer key : dailyIncomes.keySet()){
                if (dailyIncomes.get(key) > max){
                    max = dailyIncomes.get(key);
                }
            }
            return max;
        } catch (ManagerException e) {
            Initializer.LOG.severe(ErrorDialog.getExceptionStackTrace(e));
            throw new ServiceFailureException(e);
        }
    }
}
