package org.kaleta.scheduler.backend.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Stanislav Kaleta
 * Date: 24.7.2015
 */
public class Settings {
    // app settings
    private Boolean firstUse;

    // user settings
    private String userName;
    //private String userPassword;
    private Integer lastMonthSelectedId;
    private Integer lastDaySelected;
    private String uiSchemeSelected;
    private String language;
    private String currency;

    // types settings
    private List<UserType> itemTypes;
    private List<UserType> taskTypes;
    private List<UserType> globalTaskTypes;

    public Settings(){
        firstUse = null;
        userName = null;
        lastMonthSelectedId = null;
        lastDaySelected = null;
        uiSchemeSelected = null;
        language = null;
        currency = null;
        itemTypes = new ArrayList<>();
        taskTypes = new ArrayList<>();
        globalTaskTypes = new ArrayList<>();
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Boolean getFirstUse() {
        return firstUse;
    }

    public void setFirstUse(Boolean firstUse) {
        this.firstUse = firstUse;
    }

    public List<UserType> getGlobalTaskTypes() {
        return globalTaskTypes;
    }

    public List<UserType> getItemTypes() {
        return itemTypes;
    }

    public Integer getLastDaySelected() {
        return lastDaySelected;
    }

    public void setLastDaySelected(Integer lastDaySelected) {
        this.lastDaySelected = lastDaySelected;
    }

    public Integer getLastMonthSelectedId() {
        return lastMonthSelectedId;
    }

    public void setLastMonthSelectedId(Integer lastMonthSelectedId) {
        this.lastMonthSelectedId = lastMonthSelectedId;
    }

    public List<UserType> getTaskTypes() {
        return taskTypes;
    }

    public String getUiSchemeSelected() {
        return uiSchemeSelected;
    }

    public void setUiSchemeSelected(String uiSchemeSelected) {
        this.uiSchemeSelected = uiSchemeSelected;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Settings settings = (Settings) o;

        if (firstUse != null ? !firstUse.equals(settings.firstUse) : settings.firstUse != null) return false;
        if (globalTaskTypes != null ? !globalTaskTypes.equals(settings.globalTaskTypes) : settings.globalTaskTypes != null)
            return false;
        if (itemTypes != null ? !itemTypes.equals(settings.itemTypes) : settings.itemTypes != null) return false;
        if (lastDaySelected != null ? !lastDaySelected.equals(settings.lastDaySelected) : settings.lastDaySelected != null)
            return false;
        if (lastMonthSelectedId != null ? !lastMonthSelectedId.equals(settings.lastMonthSelectedId) : settings.lastMonthSelectedId != null)
            return false;
        if (taskTypes != null ? !taskTypes.equals(settings.taskTypes) : settings.taskTypes != null) return false;
        if (uiSchemeSelected != null ? !uiSchemeSelected.equals(settings.uiSchemeSelected) : settings.uiSchemeSelected != null)
            return false;
        if (userName != null ? !userName.equals(settings.userName) : settings.userName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = firstUse != null ? firstUse.hashCode() : 0;
        result = 31 * result + (userName != null ? userName.hashCode() : 0);
        result = 31 * result + (lastMonthSelectedId != null ? lastMonthSelectedId.hashCode() : 0);
        result = 31 * result + (lastDaySelected != null ? lastDaySelected.hashCode() : 0);
        result = 31 * result + (uiSchemeSelected != null ? uiSchemeSelected.hashCode() : 0);
        result = 31 * result + (itemTypes != null ? itemTypes.hashCode() : 0);
        result = 31 * result + (taskTypes != null ? taskTypes.hashCode() : 0);
        result = 31 * result + (globalTaskTypes != null ? globalTaskTypes.hashCode() : 0);
        return result;
    }
}
