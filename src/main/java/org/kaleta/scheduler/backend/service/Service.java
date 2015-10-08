package org.kaleta.scheduler.backend.service;

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
    /*TODO service methods + later refactoring(month,global,settings,???,...)
    * create new m
    * get m
    * get all m
    * get tasks for day
    * get items for day
    * get days for m
    * get settings
    * change settings,...
    * get item/task types and description for them
    * update some part e.g add/update/change task/item/freeday to day in month
    * add/get,change,delete global tasks
    * or completly any acction
    *
    * or more thinking about this (including frotnend)
    * */

    /**
     * TODO documentation
     */
    public void createSettings() {
        try {
            SettingsManager manager = new JaxbSettingsManager();
            manager.createSettings();
        } catch (ManagerException e) {
            Initializer.LOG.severe("Settings file creation failed!\n" + e.getMessage());
            throw new ServiceFailureException(e);
        }
    }

    /**
     * TODO documentation
     */
    public void createGlobal() {
        try {
            GlobalManager manager = new JaxbGlobalManager();
            manager.createGlobal();
        } catch (ManagerException e) {
            Initializer.LOG.severe("Global file creation failed!\n" + e.getMessage());
            throw new ServiceFailureException(e);
        }
    }

    /**
     * TODO documentation
     */
    public void createMonth(Month month) {
        try {
            MonthManager manager = new JaxbMonthManager();
            manager.createMonth(month);
        } catch (ManagerException e) {
            Initializer.LOG.severe("Error while creating new month!\n" + e.getMessage());
            throw new ServiceFailureException(e);
        }
    }

    /**
     * TODO documentation
     *
     * @return
     */
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

    /**
     * TODO documentation
     *
     * @param settings
     */
    public void updateSettings(Settings settings) {
        try {
            SettingsManager manager = new JaxbSettingsManager();
            manager.updateSettings(settings);
        } catch (ManagerException e) {
            Initializer.LOG.severe("Error while updating settings!\n" + e.getMessage());
            throw new ServiceFailureException(e);
        }
    }

    /**
     * TODO documentation
     *
     * @param global
     */
    public void updateGlobal(Global global) {
        try {
            GlobalManager manager = new JaxbGlobalManager();
            manager.updateGlobal(global);
        } catch (ManagerException e) {
            Initializer.LOG.severe("Error while updating global data!\n" + e.getMessage());
            throw new ServiceFailureException(e);
        }
    }

    /**
     * TODO documentation
     *
     * @param month
     */
    public void updateMonth(Month month) {
        try {
            MonthManager manager = new JaxbMonthManager();
            manager.updateMonth(month);
        } catch (ManagerException e) {
            Initializer.LOG.severe("Error while updating month with id=\"" + month.getId() + "\"!\n" + e.getMessage());
            throw new ServiceFailureException(e);
        }
    }

    /**
     * TODO documentation
     *
     * @return
     */
    public Settings getSettings() {
        try {
            SettingsManager manager = new JaxbSettingsManager();
            return manager.retrieveSettings();
        } catch (ManagerException e) {
            Initializer.LOG.severe("Error while retrieving settings!\n" + e.getMessage());
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
            Initializer.LOG.severe("Error while retrieving month with id=" + monthId + "!\n" + e.getMessage());
            throw new ServiceFailureException(e);
        }
    }

    /**
     * TODO documentation
     *
     * @return
     */
    public Global getGlobal() {
        try {
            GlobalManager manager = new JaxbGlobalManager();
            return manager.retrieveGlobal();
        } catch (ManagerException e) {
            Initializer.LOG.severe("Error while retrieving global data!\n" + e.getMessage());
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
}
