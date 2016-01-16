package org.kaleta.scheduler.feature.analytics.itemsLine;

import org.kaleta.scheduler.backend.entity.Item;
import org.kaleta.scheduler.backend.entity.Month;
import org.kaleta.scheduler.service.Service;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by Stanislav Kaleta on 12.01.2016.
 * TODO documentation (+ public methods)
 */
public class ItemsGraphModel {
    private Container target;
    private List<Month> monthsData;

    public ItemsGraphModel(){
        monthsData = Service.monthService().retrieveAllMonths();


    }

    public void setTarget(Container target){
        this.target = target;
    }

    public Map<String,Map<String,Set<String>>> getOptionsData(){
        Map<String,Map<String,Set<String>>> optionsData = new LinkedHashMap<>();
        for (Month month : monthsData) {
            Map<String,Set<String>> itemTypes = new HashMap<>();
            optionsData.put(month.getName(),itemTypes);
            for (Item item : month.getItems()){
                String type = (item.getType().equals("")) ? "Undefined" : item.getType();
                type += (item.getIncome()) ? "+" : "-";
                if (!(itemTypes.keySet()).contains(type)){
                    itemTypes.put(type,new HashSet<>());
                }
                String description = (item.getDescription().equals("")) ? "Undefined" : item.getDescription();
                itemTypes.get(type).add(description);
            }
        }
        return optionsData;
    }

    public void setSelectedMonths(List<String> selectedMonths, boolean groupData){
        System.out.println("######################");
        System.out.println("data are grouped: " + groupData);
        System.out.println("selected months: " + selectedMonths);
        System.out.println("######################");
        // TODO react (in swing worker/another thread)
    }

    public void setSelectedTypes(boolean income, boolean expense, Map<String,List<String>> types){
        System.out.println("######################");
        System.out.println("income: " + income + " expense: "+expense);
        for (String type : types.keySet()){
            System.out.println("type: " + type + " " +types.get(type));
        }
        System.out.println("######################");
        // TODO react (in swing worker/another thread)
    }
}
