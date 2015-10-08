package org.kaleta.scheduler.backend.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stanislav Kaleta on 24.07.2014
 */
public class Day {
    private Integer dayNumber;
    private Boolean publicFreeDay;
    private List<Item> items;
    private List<Task> tasks;

    public Day() {
        dayNumber = null;
        publicFreeDay = null;
        items = new ArrayList<>();
        tasks = new ArrayList<>();
    }

    public Integer getDayNumber() {
        return dayNumber;
    }

    public void setDayNumber(Integer dayNumber) {
        this.dayNumber = dayNumber;
    }

    public Boolean isPublicFreeDay() {
        return publicFreeDay;
    }

    public void setPublicFreeDay(Boolean publicFreeDay) {
        this.publicFreeDay = publicFreeDay;
    }

    public List<Item> getItems() {
        return items;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Day day = (Day) o;

        if (dayNumber != null ? !dayNumber.equals(day.dayNumber) : day.dayNumber != null) return false;
        if (publicFreeDay != null ? !publicFreeDay.equals(day.publicFreeDay) : day.publicFreeDay != null)
            return false;
        if (items != null ? !items.equals(day.items) : day.items != null) return false;
        return !(tasks != null ? !tasks.equals(day.tasks) : day.tasks != null);

    }

    @Override
    public int hashCode() {
        int result = dayNumber != null ? dayNumber.hashCode() : 0;
        result = 31 * result + (publicFreeDay != null ? publicFreeDay.hashCode() : 0);
        result = 31 * result + (items != null ? items.hashCode() : 0);
        result = 31 * result + (tasks != null ? tasks.hashCode() : 0);
        return result;
    }
}
