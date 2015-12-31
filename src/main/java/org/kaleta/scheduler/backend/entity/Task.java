package org.kaleta.scheduler.backend.entity;

/**
 * Created by Stanislav Kaleta on 24.07.2015.
 */
public class Task {
    private Integer id;
    private String type;
    private String description;
    private Integer day;
    private Time starts;
    private Time duration;
    private Boolean priority;
    private Boolean successful;

    public Task(){
        id = null;
        type = null;
        description = null;
        day = null;
        starts = null;
        duration = null;
        priority = null;
        successful = null;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Time getDuration() {
        return duration;
    }

    public void setDuration(Time duration) {
        this.duration = duration;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getPriority() {
        return priority;
    }

    public void setPriority(Boolean priority) {
        this.priority = priority;
    }

    public Time getStarts() {
        return starts;
    }

    public void setStarts(Time starts) {
        this.starts = starts;
    }

    public Boolean getSuccessful() {
        return successful;
    }

    public void setSuccessful(Boolean successful) {
        this.successful = successful;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        if (day != null ? !day.equals(task.day) : task.day != null) return false;
        if (description != null ? !description.equals(task.description) : task.description != null) return false;
        if (duration != null ? !duration.equals(task.duration) : task.duration != null) return false;
        if (id != null ? !id.equals(task.id) : task.id != null) return false;
        if (priority != null ? !priority.equals(task.priority) : task.priority != null) return false;
        if (starts != null ? !starts.equals(task.starts) : task.starts != null) return false;
        if (successful != null ? !successful.equals(task.successful) : task.successful != null) return false;
        return !(type != null ? !type.equals(task.type) : task.type != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (day != null ? day.hashCode() : 0);
        result = 31 * result + (starts != null ? starts.hashCode() : 0);
        result = 31 * result + (duration != null ? duration.hashCode() : 0);
        result = 31 * result + (priority != null ? priority.hashCode() : 0);
        result = 31 * result + (successful != null ? successful.hashCode() : 0);
        return result;
    }
}
