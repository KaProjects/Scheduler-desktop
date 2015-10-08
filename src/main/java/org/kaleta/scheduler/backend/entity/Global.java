package org.kaleta.scheduler.backend.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: Stanislav Kaleta
 * Date: 24.7.2015
 */
public class Global {
    private Map<Integer, Integer> months; // <monthId,order>
    private List<GlobalTask> tasks;

    public Global(){
        months = new HashMap<>();
        tasks = new ArrayList<>();
    }

    public Map<Integer, Integer> getMonths() {
        return months;
    }

    public List<GlobalTask> getTasks() {
        return tasks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Global global = (Global) o;

        if (months != null ? !months.equals(global.months) : global.months != null) return false;
        if (tasks != null ? !tasks.equals(global.tasks) : global.tasks != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = months != null ? months.hashCode() : 0;
        result = 31 * result + (tasks != null ? tasks.hashCode() : 0);
        return result;
    }
}
