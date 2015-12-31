package org.kaleta.scheduler.backend.manager.jaxb.model;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stanislav Kaleta on 23.07.2015.
 *
 * JAXB model of Global
 */
@XmlType(name = "globalType")
@XmlRootElement(name = "global")
@XmlAccessorType(XmlAccessType.FIELD)
public class Global {

    @XmlElement(required = true,name = "months")
    private Global.Months months;

    @XmlElement(required = true,name = "tasks")
    private Tasks tasks;

    public Tasks getTasks() {
        return tasks;
    }

    public void setTasks(Tasks tasks) {
        this.tasks = tasks;
    }

    public Months getMonths() {
        return months;
    }

    public void setMonths(Months months) {
        this.months = months;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "monthsType")
    public static class Months {

        @XmlElement(name = "month")
        private List<Global.Months.Month> monthList;

        public List<Global.Months.Month> getMonthList() {
            if (monthList == null) {
                monthList = new ArrayList<>();
            }
            return this.monthList;
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "monthType")
        public static class Month {

            @XmlAttribute(required = true,name = "id")
            private String id;

            @XmlAttribute(required = true,name = "order")
            private String order;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getOrder() {
                return order;
            }

            public void setOrder(String order) {
                this.order = order;
            }
        }
    }


    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "tasksType")
    public static class Tasks {

        @XmlElement(name = "task")
        private List<Tasks.Task> taskList;

        public List<Tasks.Task> getTaskList() {
            if (taskList == null) {
                taskList = new ArrayList<>();
            }
            return this.taskList;
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "taskType")
        public static class Task {

            @XmlAttribute(required = true,name = "id")
            private String id;

            @XmlAttribute(required = true,name = "type")
            private String type;

            @XmlAttribute(required = true,name = "description")
            private String description;

            @XmlAttribute(required = true,name = "priority")
            private String priority;

            @XmlAttribute(required = true,name = "finished")
            private String finished;

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getFinished() {
                return finished;
            }

            public void setFinished(String finished) {
                this.finished = finished;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getPriority() {
                return priority;
            }

            public void setPriority(String priority) {
                this.priority = priority;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }
    }
}
