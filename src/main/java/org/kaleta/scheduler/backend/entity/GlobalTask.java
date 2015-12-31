package org.kaleta.scheduler.backend.entity;

/**
 * Author: Stanislav Kaleta
 * Date: 25.7.2015
 */
public class GlobalTask {
    private Integer id;
    private String type;
    private String description;
    private Boolean priority;
    private Boolean finished;

    public GlobalTask(){
        id = null;
        type = null;
        description = null;
        priority = null;
        finished = null;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
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

        GlobalTask that = (GlobalTask) o;

        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (finished != null ? !finished.equals(that.finished) : that.finished != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (priority != null ? !priority.equals(that.priority) : that.priority != null) return false;
        return !(type != null ? !type.equals(that.type) : that.type != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (priority != null ? priority.hashCode() : 0);
        result = 31 * result + (finished != null ? finished.hashCode() : 0);
        return result;
    }
}
