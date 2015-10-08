package org.kaleta.scheduler.backend.manager.jaxb.model;

import org.kaleta.scheduler.backend.entity.*;

import java.awt.*;
import java.math.BigDecimal;

/**
 * Author: Stanislav Kaleta
 * Date: 24.7.2015
 *
 * TODO documentation
 */
public class ModelUtil {

    /**
     * TODO documentation
     *
     * @param data
     * @return
     */
    public static Month transformMonthToModel(org.kaleta.scheduler.backend.entity.Month data){
        Month model = new Month();

        model.setId(String.valueOf(data.getId()));

        Month.Specification specification = new Month.Specification();
        specification.setName(data.getName());
        specification.setDays(String.valueOf(data.getDaysNumber()));
        specification.setFirstDay(String.valueOf(data.getDayStartsWith()));
        for (Integer day : data.getPublicFreeDays()){
            Month.Specification.FreeDay freeDay = new Month.Specification.FreeDay();
            freeDay.setDay(String.valueOf(day));
            specification.getFreeDayList().add(freeDay);
        }
        model.setSpecification(specification);

        Month.Schedule schedule = new Month.Schedule();
        for (Task task : data.getTasks()){
            Month.Schedule.Task taskModel = new Month.Schedule.Task();
            taskModel.setId(String.valueOf(task.getId()));
            taskModel.setType(task.getType());
            taskModel.setDescription(task.getDescription());
            taskModel.setDay(String.valueOf(task.getDay()));
            taskModel.setStarts(task.getStarts().toString());
            taskModel.setDuration(task.getDuration().toString());
            taskModel.setPriority(String.valueOf(task.getPriority()));
            taskModel.setSuccessful(String.valueOf(task.getSuccessful()));
            schedule.getTaskList().add(taskModel);
        }
        model.setSchedule(schedule);

        Month.Accounting accounting = new Month.Accounting();
        for (Item item : data.getItems()){
            Month.Accounting.Item itemModel = new Month.Accounting.Item();
            itemModel.setId(String.valueOf(item.getId()));
            itemModel.setType(item.getType());
            itemModel.setDescription(item.getDescription());
            itemModel.setDay(String.valueOf(item.getDay()));
            itemModel.setIncome(String.valueOf(item.getIncome()));
            itemModel.setAmount(String.valueOf(item.getAmount()));
            accounting.getItemList().add(itemModel);
        }
        model.setAccounting(accounting);

        return model;
    }

    /**
     * TODO documentation
     *
     * @param model
     * @return
     */
    public static org.kaleta.scheduler.backend.entity.Month transformMonthToData(Month model){
        org.kaleta.scheduler.backend.entity.Month data = new org.kaleta.scheduler.backend.entity.Month();

        data.setId(Integer.valueOf(model.getId()));
        data.setName(model.getSpecification().getName());
        data.setDaysNumber(Integer.valueOf(model.getSpecification().getDays()));
        data.setDayStartsWith(Integer.valueOf(model.getSpecification().getFirstDay()));
        for (Month.Specification.FreeDay freeDay : model.getSpecification().getFreeDayList()){
            data.getPublicFreeDays().add(Integer.valueOf(freeDay.getDay()));
        }

        for (Month.Schedule.Task modelTask : model.getSchedule().getTaskList()){
            Task task = new Task();
            task.setId(Integer.valueOf(modelTask.getId()));
            task.setType(modelTask.getType());
            task.setDescription(modelTask.getDescription());
            task.setDay(Integer.valueOf(modelTask.getDay()));
            Time starts = new Time();
            starts.setFromString(modelTask.getStarts());
            task.setStarts(starts);
            Time duration = new Time();
            duration.setFromString(modelTask.getDuration());
            task.setDuration(duration);
            task.setPriority(Boolean.valueOf(modelTask.getPriority()));
            task.setSuccessful(Boolean.valueOf(modelTask.getSuccessful()));
            data.getTasks().add(task);
        }

        for (Month.Accounting.Item modelItem : model.getAccounting().getItemList()){
            Item item = new Item();
            item.setId(Integer.valueOf(modelItem.getId()));
            item.setType(modelItem.getType());
            item.setDescription(modelItem.getDescription());
            item.setDay(Integer.valueOf(modelItem.getDay()));
            item.setIncome(Boolean.valueOf(modelItem.getIncome()));
            item.setAmount(new BigDecimal(modelItem.getAmount()));
            data.getItems().add(item);
        }

        return data;
    }

    /**
     * TODO documentation
     *
     * @param data
     * @return
     */
    public static Global transformGlobalToModel(org.kaleta.scheduler.backend.entity.Global data){
        Global model = new Global();

        Global.Months months = new Global.Months();
        for(Integer monthId : data.getMonths().keySet()){
            Global.Months.Month month = new Global.Months.Month();
            month.setId(String.valueOf(monthId));
            month.setOrder(String.valueOf(data.getMonths().get(monthId)));
            months.getMonthList().add(month);
        }
        model.setMonths(months);

        Global.Tasks tasks = new Global.Tasks();
        for(GlobalTask globalTask : data.getTasks()){
            Global.Tasks.Task task = new Global.Tasks.Task();
            task.setId(String.valueOf(globalTask.getId()));
            task.setType(globalTask.getType());
            task.setDescription(globalTask.getDescription());
            task.setPriority(String.valueOf(globalTask.getPriority()));
            task.setFinished(String.valueOf(globalTask.getFinished()));
            tasks.getTaskList().add(task);
        }
        model.setTasks(tasks);

        return model;
    }

    /**
     * TODO documentation
     *
     * @param model
     * @return
     */
    public static org.kaleta.scheduler.backend.entity.Global transformGlobalToData(Global model){
        org.kaleta.scheduler.backend.entity.Global data = new org.kaleta.scheduler.backend.entity.Global();

        for(Global.Months.Month month : model.getMonths().getMonthList()){
            data.getMonths().put(Integer.valueOf(month.getId()),Integer.valueOf(month.getOrder()));
        }

        for(Global.Tasks.Task task : model.getTasks().getTaskList()){
            GlobalTask globalTask = new GlobalTask();
            globalTask.setId(Integer.valueOf(task.getId()));
            globalTask.setType(task.getType());
            globalTask.setDescription(task.getDescription());
            globalTask.setPriority(Boolean.valueOf(task.getPriority()));
            globalTask.setFinished(Boolean.valueOf(task.getFinished()));
            data.getTasks().add(globalTask);
        }

        return data;
    }

    /**
     * TODO documentation
     *
     * @param data
     * @return
     */
    public static Settings transformSettingsToModel(org.kaleta.scheduler.backend.entity.Settings data){
        Settings model = new Settings();

        Settings.AppSettings appSettings = new Settings.AppSettings();
        Settings.AppSettings.FirstUse firstUse = new Settings.AppSettings.FirstUse();
        firstUse.setValue(String.valueOf(data.getFirstUse()));
        appSettings.setFirstUse(firstUse);
        model.setAppSettings(appSettings);

        Settings.UserSettings userSettings = new Settings.UserSettings();
        Settings.UserSettings.Value userName = new Settings.UserSettings.Value();
        userName.setValue(data.getUserName());
        userSettings.setUserName(userName);
        Settings.UserSettings.LastSelected lastSelected = new Settings.UserSettings.LastSelected();
        lastSelected.setMonth_id(String.valueOf(data.getLastMonthSelectedId()));
        lastSelected.setDay(String.valueOf(data.getLastDaySelected()));
        userSettings.setLastSelected(lastSelected);
        Settings.UserSettings.Value uiScheme = new Settings.UserSettings.Value();
        uiScheme.setValue(data.getUiSchemeSelected());
        userSettings.setUiScheme(uiScheme);
        Settings.UserSettings.Value language = new Settings.UserSettings.Value();
        language.setValue(data.getLanguage());
        userSettings.setLanguage(language);
        Settings.UserSettings.Value currency = new Settings.UserSettings.Value();
        currency.setValue(data.getCurrency());
        userSettings.setCurrency(currency);
        model.setUserSettings(userSettings);

        Settings.TypesSettings typesSettings = new Settings.TypesSettings();
        Settings.TypesSettings.ItemTypes itemTypes = new Settings.TypesSettings.ItemTypes();
        for(UserType type : data.getItemTypes()){
            Settings.TypesSettings.Type itemType = new Settings.TypesSettings.Type();
            itemType.setName(type.getName());
            itemType.setColor(String.valueOf(type.getColor().getRGB()));
            itemType.setSign((type.getSign()) ? "+" : "-");//diff -/+
            for(String desc : type.getPreparedDescriptions()){
                Settings.TypesSettings.Description description = new Settings.TypesSettings.Description();
                description.setValue(desc);
                itemType.getDescriptions().add(description);
            }
            itemTypes.getItemTypes().add(itemType);
        }
        typesSettings.setItemTypes(itemTypes);
        Settings.TypesSettings.TaskTypes taskTypes = new Settings.TypesSettings.TaskTypes();
        for(UserType type : data.getTaskTypes()){
            Settings.TypesSettings.Type taskType = new Settings.TypesSettings.Type();
            taskType.setName(type.getName());
            taskType.setColor(String.valueOf(type.getColor().getRGB()));
            taskType.setSign("?");//not supported
            for(String desc : type.getPreparedDescriptions()){
                Settings.TypesSettings.Description description = new Settings.TypesSettings.Description();
                description.setValue(desc);
                taskType.getDescriptions().add(description);
            }
            taskTypes.getTaskTypes().add(taskType);
        }
        typesSettings.setTaskTypes(taskTypes);
        Settings.TypesSettings.GlobalTaskTypes globalTaskTypes = new Settings.TypesSettings.GlobalTaskTypes();
        for(UserType type : data.getGlobalTaskTypes()){
            Settings.TypesSettings.Type globalTaskType = new Settings.TypesSettings.Type();
            globalTaskType.setName(type.getName());
            globalTaskType.setColor(String.valueOf(type.getColor().getRGB()));
            globalTaskType.setSign("?");//not supported
            for(String desc : type.getPreparedDescriptions()){
                Settings.TypesSettings.Description description = new Settings.TypesSettings.Description();
                description.setValue(desc);
                globalTaskType.getDescriptions().add(description);
            }
            globalTaskTypes.getGlobalTaskTypes().add(globalTaskType);
        }
        typesSettings.setGlobalTaskTypes(globalTaskTypes);
        model.setTypesSettings(typesSettings);

        return model;
    }

    /**
     * TODO documentation
     *
     * @param model
     * @return
     */
    public static org.kaleta.scheduler.backend.entity.Settings transformSettingsToData(Settings model){
        org.kaleta.scheduler.backend.entity.Settings data = new org.kaleta.scheduler.backend.entity.Settings();

        data.setFirstUse(Boolean.valueOf(model.getAppSettings().getFirstUse().getValue()));
        data.setUserName(model.getUserSettings().getUserName().getValue());
        data.setLastMonthSelectedId(Integer.valueOf(model.getUserSettings().getLastSelected().getMonth_id()));
        data.setLastDaySelected(Integer.valueOf(model.getUserSettings().getLastSelected().getDay()));
        data.setUiSchemeSelected(model.getUserSettings().getUiScheme().getValue());
        data.setLanguage(model.getUserSettings().getLanguage().getValue());
        data.setCurrency(model.getUserSettings().getCurrency().getValue());
        for(Settings.TypesSettings.Type type : model.getTypesSettings().getItemTypes().getItemTypes()){
            UserType itemType = new UserType();
            itemType.setName(type.getName());
            itemType.setColor(new Color(Integer.valueOf(type.getColor())));
            itemType.setSign(type.getSign().equals("+"));//diff -/+
            for(Settings.TypesSettings.Description description : type.getDescriptions()){
                itemType.getPreparedDescriptions().add(description.getValue());
            }
            data.getItemTypes().add(itemType);
        }
        for(Settings.TypesSettings.Type type : model.getTypesSettings().getTaskTypes().getTaskTypes()){
            UserType taskType = new UserType();
            taskType.setName(type.getName());
            taskType.setColor(new Color(Integer.valueOf(type.getColor())));
            taskType.setSign(false);//not supported
            for(Settings.TypesSettings.Description description : type.getDescriptions()){
                taskType.getPreparedDescriptions().add(description.getValue());
            }
            data.getTaskTypes().add(taskType);
        }
        for(Settings.TypesSettings.Type type : model.getTypesSettings().getGlobalTaskTypes().getGlobalTaskTypes()){
            UserType globalTaskType = new UserType();
            globalTaskType.setName(type.getName());
            globalTaskType.setColor(new Color(Integer.valueOf(type.getColor())));
            globalTaskType.setSign(false);//not supported
            for(Settings.TypesSettings.Description description : type.getDescriptions()){
                globalTaskType.getPreparedDescriptions().add(description.getValue());
            }
            data.getGlobalTaskTypes().add(globalTaskType);
        }

        return data;
    }
}
