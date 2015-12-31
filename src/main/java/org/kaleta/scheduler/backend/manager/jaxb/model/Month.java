package org.kaleta.scheduler.backend.manager.jaxb.model;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stanislav Kaleta on 20.07.2015.
 *
 * JAXB model of Month
 */
@XmlType(name = "monthType")
@XmlRootElement(name = "month")
@XmlAccessorType(XmlAccessType.FIELD)
public class Month {

    @XmlAttribute(required = true,name = "id")
    private String id;

    @XmlElement(required = true,name = "specification")
    private Month.Specification specification;

    @XmlElement(required = true,name = "schedule")
    private Month.Schedule schedule;

    @XmlElement(required = true,name = "accounting")
    private Month.Accounting accounting;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Month.Specification getSpecification() {
        return specification;
    }

    public void setSpecification(Month.Specification specification) {
        this.specification = specification;
    }

    public Month.Accounting getAccounting() {
        return accounting;
    }

    public void setAccounting(Month.Accounting accounting) {
        this.accounting = accounting;
    }

    public Month.Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Month.Schedule schedule) {
        this.schedule = schedule;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "specificationType")
    public static class Specification {

        @XmlElement(name = "public_free_day")
        private List<Month.Specification.FreeDay> freeDayList;

        @XmlAttribute(required = true,name = "name")
        private String name;

        @XmlAttribute(required = true,name = "days")
        private String days;

        @XmlAttribute(required = true,name = "first_day")
        private String firstDay;

        public List<Month.Specification.FreeDay> getFreeDayList() {
            if (freeDayList == null) {
                freeDayList = new ArrayList<>();
            }
            return this.freeDayList;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getFirstDay() {
            return firstDay;
        }

        public void setFirstDay(String firstDay) {
            this.firstDay = firstDay;
        }

        public String getDays() {
            return days;
        }

        public void setDays(String days) {
            this.days = days;
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "publicFreeDayType")
        public static class FreeDay {

            @XmlAttribute(required = true,name = "day")
            private String day;

            public String getDay() {
                return day;
            }

            public void setDay(String day) {
                this.day = day;
            }
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "scheduleType")
    public static class Schedule {

        @XmlElement(name = "task")
        private List<Month.Schedule.Task> taskList;

        public List<Month.Schedule.Task> getTaskList() {
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

            @XmlAttribute(required = true,name = "day")
            private String day;

            @XmlAttribute(required = true,name = "starts")
            private String starts;

            @XmlAttribute(required = true,name = "duration")
            private String duration;

            @XmlAttribute(required = true,name = "priority")
            private String priority;

            @XmlAttribute(required = true,name = "successful")
            private String successful;

            public String getDay() {
                return day;
            }

            public void setDay(String day) {
                this.day = day;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getDuration() {
                return duration;
            }

            public void setDuration(String duration) {
                this.duration = duration;
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

            public String getStarts() {
                return starts;
            }

            public void setStarts(String starts) {
                this.starts = starts;
            }

            public String getSuccessful() {
                return successful;
            }

            public void setSuccessful(String successful) {
                this.successful = successful;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "accountingType")
    public static class Accounting {

        @XmlElement(name = "item")
        private List<Month.Accounting.Item> itemList;

        public List<Month.Accounting.Item> getItemList() {
            if (itemList == null) {
                itemList = new ArrayList<>();
            }
            return this.itemList;
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "itemType")
        public static class Item {

            @XmlAttribute(required = true,name = "id")
            private String id;

            @XmlAttribute(required = true,name = "type")
            private String type;

            @XmlAttribute(required = true,name = "description")
            private String description;

            @XmlAttribute(required = true,name = "day")
            private String day;

            @XmlAttribute(required = true,name = "income")
            private String income;

            @XmlAttribute(required = true,name = "amount")
            private String amount;

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getDay() {
                return day;
            }

            public void setDay(String day) {
                this.day = day;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getIncome() {
                return income;
            }

            public void setIncome(String income) {
                this.income = income;
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
