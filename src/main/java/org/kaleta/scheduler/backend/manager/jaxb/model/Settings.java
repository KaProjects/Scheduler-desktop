package org.kaleta.scheduler.backend.manager.jaxb.model;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Stanislav Kaleta
 * Date: 23.7.2015
 */
@XmlType(name = "settingsType")
@XmlRootElement(name = "settings")
@XmlAccessorType(XmlAccessType.FIELD)
public class Settings {

    @XmlElement(required = true,name = "app_settings")
    private Settings.AppSettings appSettings;

    @XmlElement(required = true,name = "user_settings")
    private Settings.UserSettings userSettings;

    @XmlElement(required = true,name = "types_settings")
    private Settings.TypesSettings typesSettings;

    public Settings.AppSettings getAppSettings() {
        return appSettings;
    }

    public void setAppSettings(Settings.AppSettings appSettings) {
        this.appSettings = appSettings;
    }

    public Settings.TypesSettings getTypesSettings() {
        return typesSettings;
    }

    public void setTypesSettings(Settings.TypesSettings typesSettings) {
        this.typesSettings = typesSettings;
    }

    public Settings.UserSettings getUserSettings() {
        return userSettings;
    }

    public void setUserSettings(Settings.UserSettings userSettings) {
        this.userSettings = userSettings;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "appSettingsType")
    public static class AppSettings{

        @XmlElement(required = true,name = "first_use")
        private Settings.AppSettings.FirstUse firstUse;

        public AppSettings.FirstUse getFirstUse() {
            return firstUse;
        }

        public void setFirstUse(AppSettings.FirstUse firstUse) {
            this.firstUse = firstUse;
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "firstUseType")
        public static class FirstUse{

            @XmlAttribute(required = true,name = "value")
            private String value;

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "userSettingsType")
    public static class UserSettings{

        @XmlElement(required = true,name = "user_name")
        private Settings.UserSettings.Value userName;

        @XmlElement(required = true,name = "last_selected")
        private Settings.UserSettings.LastSelected lastSelected;

        @XmlElement(required = true,name = "ui_scheme")
        private Settings.UserSettings.Value uiScheme;

        @XmlElement(required = true,name = "language")
        private Settings.UserSettings.Value language;

        @XmlElement(required = true,name = "currency")
        private Settings.UserSettings.Value currency;

        public Value getCurrency() {
            return currency;
        }

        public void setCurrency(Value currency) {
            this.currency = currency;
        }

        public Value getLanguage() {
            return language;
        }

        public void setLanguage(Value language) {
            this.language = language;
        }

        public LastSelected getLastSelected() {
            return lastSelected;
        }

        public void setLastSelected(LastSelected lastSelected) {
            this.lastSelected = lastSelected;
        }

        public Value getUiScheme() {
            return uiScheme;
        }

        public void setUiScheme(Value uiScheme) {
            this.uiScheme = uiScheme;
        }

        public Value getUserName() {
            return userName;
        }

        public void setUserName(Value userName) {
            this.userName = userName;
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "valueType")
        public static class Value{

            @XmlAttribute(required = true,name = "value")
            private String value;

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "lastSelectedType")
        public static class LastSelected{

            @XmlAttribute(required = true,name = "month_id")
            private String month_id;

            @XmlAttribute(required = true,name = "day")
            private String day;

            public String getDay() {
                return day;
            }

            public void setDay(String day) {
                this.day = day;
            }

            public String getMonth_id() {
                return month_id;
            }

            public void setMonth_id(String month_id) {
                this.month_id = month_id;
            }
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "typesSettingsType")
    public static class TypesSettings{

        @XmlElement(required = true,name = "item_types")
        private Settings.TypesSettings.ItemTypes itemTypes;

        @XmlElement(required = true,name = "task_types")
        private Settings.TypesSettings.TaskTypes taskTypes;

        @XmlElement(required = true,name = "global_task_types")
        private Settings.TypesSettings.GlobalTaskTypes globalTaskTypes;

        public Settings.TypesSettings.GlobalTaskTypes getGlobalTaskTypes() {
            return globalTaskTypes;
        }

        public void setGlobalTaskTypes(Settings.TypesSettings.GlobalTaskTypes globalTaskTypes) {
            this.globalTaskTypes = globalTaskTypes;
        }

        public Settings.TypesSettings.ItemTypes getItemTypes() {
            return itemTypes;
        }

        public void setItemTypes(Settings.TypesSettings.ItemTypes itemTypes) {
            this.itemTypes = itemTypes;
        }

        public Settings.TypesSettings.TaskTypes getTaskTypes() {
            return taskTypes;
        }

        public void setTaskTypes(Settings.TypesSettings.TaskTypes taskTypes) {
            this.taskTypes = taskTypes;
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "itemTypesType")
        public static class ItemTypes{

            @XmlElement(name = "item_type")
            private List<Settings.TypesSettings.Type> itemTypes;

            public List<Settings.TypesSettings.Type> getItemTypes() {
                if (itemTypes == null) {
                    itemTypes = new ArrayList<>();
                }
                return this.itemTypes;
            }
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "taskTypesType")
        public static class TaskTypes{

            @XmlElement(name = "task_type")
            private List<Settings.TypesSettings.Type> taskTypes;

            public List<Settings.TypesSettings.Type> getTaskTypes() {
                if (taskTypes == null) {
                    taskTypes = new ArrayList<>();
                }
                return this.taskTypes;
            }
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "gTaskTypesType")
        public static class GlobalTaskTypes{

            @XmlElement(name = "g_task_type")
            private List<Settings.TypesSettings.Type> globalTaskTypes;

            public List<Settings.TypesSettings.Type> getGlobalTaskTypes() {
                if (globalTaskTypes == null) {
                    globalTaskTypes = new ArrayList<>();
                }
                return this.globalTaskTypes;
            }
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "typeType")
        public static class Type{

            @XmlElement(name = "description_type")
            private List<Settings.TypesSettings.Description> descriptions;

            @XmlAttribute(required = true,name = "name")
            private String name;

            @XmlAttribute(required = true,name = "color")
            private String color;

            @XmlAttribute(required = true,name = "sign")
            private String sign;

            public String getColor() {
                return color;
            }

            public void setColor(String color) {
                this.color = color;
            }

            public List<Settings.TypesSettings.Description> getDescriptions() {
                if (descriptions == null) {
                    descriptions = new ArrayList<>();
                }
                return this.descriptions;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getSign() {
                return sign;
            }

            public void setSign(String sign) {
                this.sign = sign;
            }
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "descriptionType")
        public static class Description{

            @XmlAttribute(required = true,name = "value")
            private String value;

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }
    }
}
