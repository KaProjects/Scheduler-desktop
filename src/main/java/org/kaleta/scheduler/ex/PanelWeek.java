package org.kaleta.scheduler.ex;

import org.kaleta.scheduler.backend.entity.Day;
import org.kaleta.scheduler.backend.entity.Item;
import org.kaleta.scheduler.backend.entity.Month;
import org.kaleta.scheduler.backend.entity.Task;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Stanislav Kaleta
 * Date: 20.7.2014
 * To change this template use File | Settings | File Templates.
 */
public class PanelWeek extends JPanel {
    private PanelDay panelDay1;
    private PanelDay panelDay2;
    private PanelDay panelDay3;
    private PanelDay panelDay4;
    private PanelDay panelDay5;
    private PanelDay panelDay6;
    private PanelDay panelDay7;
    private Month month;
    private int numberOfWeeks;
    private Map<Integer,Map<Integer, Day>> days;

    public PanelWeek(JButton[] bAccs){
        month = null;
        numberOfWeeks = 0;
        days = null;
        panelDay1 = new PanelDay(bAccs[0]);
        panelDay2 = new PanelDay(bAccs[1]);
        panelDay3 = new PanelDay(bAccs[2]);
        panelDay4 = new PanelDay(bAccs[3]);
        panelDay5 = new PanelDay(bAccs[4]);
        panelDay6 = new PanelDay(bAccs[5]);
        panelDay7 = new PanelDay(bAccs[6]);

        initGroupLayout();
    }
    public void setNewLayout(Month month/*TODO este nieco?ako labelInfo, buttonAccounting*/){
        this.month = month;
        numberOfWeeks = countWeeks();
        days = new HashMap<Integer, Map<Integer, Day>>();
        makeWeeks();
    }
    private void initGroupLayout(){
        GroupLayout panelWeekLayout = new GroupLayout(this);
        this.setLayout(panelWeekLayout);
        panelWeekLayout.setHorizontalGroup(
                panelWeekLayout.createSequentialGroup()
                        .addComponent(panelDay1)
                        .addComponent(panelDay2)
                        .addComponent(panelDay3)
                        .addComponent(panelDay4)
                        .addComponent(panelDay5)
                        .addComponent(panelDay6)
                        .addComponent(panelDay7)
        );
        panelWeekLayout.setVerticalGroup(
                panelWeekLayout.createParallelGroup()
                        .addComponent(panelDay1)
                        .addComponent(panelDay2)
                        .addComponent(panelDay3)
                        .addComponent(panelDay4)
                        .addComponent(panelDay5)
                        .addComponent(panelDay6)
                        .addComponent(panelDay7)
        );
    }
    private int countWeeks(){
        int temp = month.getDaysNumber();
        int output = 0;
        if (month.getDayStartsWith() != 1){
            temp = temp - 7 + (month.getDayStartsWith()-1);
            output++;
        }
        while (temp>0){
            temp = temp - 7;
            output++;
        }
        return output;
    }
    private void makeWeeks(){
        int dayNumber = 1;

        Map<Integer,Day> firstWeekDays = new HashMap<Integer, Day>();
        for (int d=month.getDayStartsWith();d<=7;d++){
            Day day = new Day();
            day.setDayNumber(dayNumber);
            day.getItems().addAll(getRecordsForDay(dayNumber));
            day.getTasks().addAll(getTasksForDay(dayNumber));
            firstWeekDays.put(d,day);
            dayNumber++;
        }
        days.put(1,firstWeekDays);

        int week = 2;
        while (dayNumber <= month.getDaysNumber()-7){
            Map<Integer,Day> midWeeksDays = new HashMap<Integer, Day>();
            for (int d=1;d<=7;d++){
                Day day = new Day();
                day.setDayNumber(dayNumber);
                day.getItems().addAll(getRecordsForDay(dayNumber));
                day.getTasks().addAll(getTasksForDay(dayNumber));
                midWeeksDays.put(d,day);
                dayNumber++;
            }
            days.put(week,midWeeksDays);
            week++;
        }

        Map<Integer,Day> lastWeekDays = new HashMap<Integer, Day>();
        int daysLeft = month.getDaysNumber()-dayNumber+1;
        for (int d=1;d<=daysLeft;d++){
            Day day = new Day();
            day.setDayNumber(dayNumber);
            day.getItems().addAll(getRecordsForDay(dayNumber));
            day.getTasks().addAll(getTasksForDay(dayNumber));
            lastWeekDays.put(d,day);
            dayNumber++;
        }
        days.put(week,lastWeekDays);
    }
    private List<Task> getTasksForDay(int day){
        List<Task> output = new ArrayList<Task>();
        for (Task t : month.getTasks()) {
            if (t.getDay() == day){
                output.add(t);
            }
        }
        return output;
    }
    private List<Item> getRecordsForDay(int day){
        List<Item> output = new ArrayList<Item>();
        for (Item r : month.getItems()){
            if (r.getDay() == day){
                output.add(r);
            }
        }
        return output;
    }

    public String[] getSpinnerValues(){
        String[] output = new String[numberOfWeeks];
        for (int i=1;i<=numberOfWeeks;i++){
            output[i-1] = String.valueOf(i);
        }
        return output;
    }
    public void setWorkingWeek(int weekNumber){
        String[] dayNames = java.util.ResourceBundle.getBundle("l18n/L18nStrings").getString("DAYSOFWEEK").split(",");
        Map<Integer,Day> week= days.get(weekNumber);
        for (int i=1;i<=7;i++){
            if(week.containsKey(i)) {
                Day day = week.get(i);
                switch (i){
                    case 1:
                        panelDay1.setData(day.getDayNumber(),dayNames[0],true);
                        break;
                    case 2:
                        panelDay2.setData(day.getDayNumber(),dayNames[1],true);
                        break;
                    case 3:
                        panelDay3.setData(day.getDayNumber(),dayNames[2],true);
                        break;
                    case 4:
                        panelDay4.setData(day.getDayNumber(),dayNames[3],true);
                        break;
                    case 5:
                        panelDay5.setData(day.getDayNumber(),dayNames[4],true);
                        break;
                    case 6:
                        panelDay6.setData(day.getDayNumber(),dayNames[5],true);
                        break;
                    case 7:
                        panelDay7.setData(day.getDayNumber(),dayNames[6],true);
                        break;
                }
            } else {
                switch (i){
                    case 1:
                        panelDay1.setData(0,"",false);
                        break;
                    case 2:
                        panelDay2.setData(0,"",false);
                        break;
                    case 3:
                        panelDay3.setData(0,"",false);
                        break;
                    case 4:
                        panelDay4.setData(0,"",false);
                        break;
                    case 5:
                        panelDay5.setData(0,"",false);
                        break;
                    case 6:
                        panelDay6.setData(0,"",false);
                        break;
                    case 7:
                        panelDay7.setData(0,"",false);
                        break;
                }

            }



        }
    }
    public int getDayNumber(int week, int dayInWeek){
        return days.get(week).get(dayInWeek).getDayNumber();
    }
}
