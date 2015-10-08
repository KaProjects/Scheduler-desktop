package org.kaleta.scheduler.backend.entity;


import java.util.ArrayList;
import java.util.List;

/**
 * Author: Stanislav Kaleta
 * Date: 11.7.2014
 */
public class Month {
    private Integer id;
    private String name;
    private Integer daysNumber;
    private Integer dayStartsWith;
    private List<Integer> publicFreeDays;
    private List<Task> tasks;
    private List<Item> items;

    public Month(){
        name = null;
        daysNumber = null;
        dayStartsWith = null;
        publicFreeDays = new ArrayList<>();
        tasks = new ArrayList<>();
        items = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDaysNumber() {
        return daysNumber;
    }

    public void setDaysNumber(Integer daysNumber) {
        this.daysNumber = daysNumber;
    }

    public Integer getDayStartsWith() {
        return dayStartsWith;
    }

    public void setDayStartsWith(Integer dayStartsWith) {
        this.dayStartsWith = dayStartsWith;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getPublicFreeDays() {
        return publicFreeDays;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public List<Item> getItems() {
        return items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Month month = (Month) o;

        if (dayStartsWith != null ? !dayStartsWith.equals(month.dayStartsWith) : month.dayStartsWith != null)
            return false;
        if (daysNumber != null ? !daysNumber.equals(month.daysNumber) : month.daysNumber != null) return false;
        if (id != null ? !id.equals(month.id) : month.id != null) return false;
        if (items != null ? !items.equals(month.items) : month.items != null) return false;
        if (name != null ? !name.equals(month.name) : month.name != null) return false;
        if (publicFreeDays != null ? !publicFreeDays.equals(month.publicFreeDays) : month.publicFreeDays != null)
            return false;
        if (tasks != null ? !tasks.equals(month.tasks) : month.tasks != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (daysNumber != null ? daysNumber.hashCode() : 0);
        result = 31 * result + (dayStartsWith != null ? dayStartsWith.hashCode() : 0);
        result = 31 * result + (publicFreeDays != null ? publicFreeDays.hashCode() : 0);
        result = 31 * result + (tasks != null ? tasks.hashCode() : 0);
        result = 31 * result + (items != null ? items.hashCode() : 0);
        return result;
    }
}
