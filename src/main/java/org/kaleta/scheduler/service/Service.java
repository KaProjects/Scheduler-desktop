package org.kaleta.scheduler.service;

import org.kaleta.scheduler.backend.entity.Global;
import org.kaleta.scheduler.backend.entity.Item;
import org.kaleta.scheduler.backend.entity.Month;
import org.kaleta.scheduler.backend.entity.Settings;
import org.kaleta.scheduler.backend.manager.GlobalManager;
import org.kaleta.scheduler.backend.manager.ManagerException;
import org.kaleta.scheduler.backend.manager.MonthManager;
import org.kaleta.scheduler.backend.manager.SettingsManager;
import org.kaleta.scheduler.backend.manager.jaxb.JaxbGlobalManager;
import org.kaleta.scheduler.backend.manager.jaxb.JaxbMonthManager;
import org.kaleta.scheduler.backend.manager.jaxb.JaxbSettingsManager;
import org.kaleta.scheduler.frontend.Initializer;

import java.util.Map;

/**
 * User: Stanislav Kaleta
 * Date: 23.7.2015
 */
public class Service {

    public void createSettings() {
        try {
            SettingsManager manager = new JaxbSettingsManager();
            manager.createSettings();
        } catch (ManagerException e) {
            Initializer.LOG.severe(e.getMessage());
            throw new ServiceFailureException(e);
        }
    }

    public void createGlobal() {
        try {
            GlobalManager manager = new JaxbGlobalManager();
            manager.createGlobal();
        } catch (ManagerException e) {
            Initializer.LOG.severe(e.getMessage());
            throw new ServiceFailureException(e);
        }
    }

    public void createMonth(Month month) {
        try {
            MonthManager manager = new JaxbMonthManager();
            manager.createMonth(month);
        } catch (ManagerException e) {
            Initializer.LOG.severe(e.getMessage());
            throw new ServiceFailureException(e);
        }
    }

    public boolean isFirstUse() {
        try {
            SettingsManager manager = new JaxbSettingsManager();
            Settings settings = manager.retrieveSettings();
            return settings.getFirstUse();
        } catch (ManagerException e) {
            Initializer.LOG.severe("Settings error!\n" + e.getMessage());
            throw new ServiceFailureException(e);
        }
    }

    public void updateSettings(Settings settings) {
        try {
            SettingsManager manager = new JaxbSettingsManager();
            manager.updateSettings(settings);
        } catch (ManagerException e) {
            Initializer.LOG.severe(e.getMessage());
            throw new ServiceFailureException(e);
        }
    }

    public void updateGlobal(Global global) {
        try {
            GlobalManager manager = new JaxbGlobalManager();
            manager.updateGlobal(global);
        } catch (ManagerException e) {
            Initializer.LOG.severe(e.getMessage());
            throw new ServiceFailureException(e);
        }
    }

    public void updateMonth(Month month) {
        try {
            MonthManager manager = new JaxbMonthManager();
            manager.updateMonth(month);
        } catch (ManagerException e) {
            Initializer.LOG.severe(e.getMessage());
            throw new ServiceFailureException(e);
        }
    }

    public Settings getSettings() {
        try {
            SettingsManager manager = new JaxbSettingsManager();
            return manager.retrieveSettings();
        } catch (ManagerException e) {
            Initializer.LOG.severe(e.getMessage());
            throw new ServiceFailureException(e);
        }
    }

    /**
     * @param monthId
     * @return
     */
    public Month getMonth(int monthId) {
        try {
            MonthManager manager = new JaxbMonthManager();
            return manager.retrieveMonth(monthId);
        } catch (ManagerException e) {
            Initializer.LOG.severe(e.getMessage());
            throw new ServiceFailureException(e);
        }
    }

    public Global getGlobal() {
        try {
            GlobalManager manager = new JaxbGlobalManager();
            return manager.retrieveGlobal();
        } catch (ManagerException e) {
            Initializer.LOG.severe(e.getMessage());
            throw new ServiceFailureException(e);
        }
    }

    public void updateItem(Item updatedItem, int monthId) {
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
                throw new ServiceFailureException(msg);
            } else {
                manager.updateMonth(month);
            }
        } catch (ManagerException e) {
            Initializer.LOG.severe("Error while updating item(id=" + updatedItem.getId()
                    + ") in month(id=" + monthId + ")\n" + e.getMessage());
            throw new ServiceFailureException(e);
        }
    }

    public void deleteItem(Item itemToDel, int monthId){
        try {
            MonthManager manager = new JaxbMonthManager();
            Month month = manager.retrieveMonth(monthId);
            month.getItems().remove(itemToDel);
            manager.updateMonth(month);
        } catch (ManagerException e) {
            Initializer.LOG.severe("Error while deleting item(id=" + itemToDel.getId()
                    + ") in month(id=" + monthId + ")\n" + e.getMessage());
            throw new ServiceFailureException(e);
        }
    }

    // TODO ///////////////////// new methods  ^remove later^ (maybe excpet isFirst use, and init creations)

    /**
     * TODO documentation
     * @return
     */
    public MonthService getMonthService(){
        return new MonthService();
    }

    /**
     * TODO documentation
     * @return
     */
    public DayService getDayService(){
        return new DayService();
    }

    /**
     * TODO documentation
     * @return
     */
    public ItemService getItemService(){
        return new ItemService();
    }


}
