package org.kaleta.scheduler.service;

import org.kaleta.scheduler.backend.entity.Item;
import org.kaleta.scheduler.backend.entity.Month;
import org.kaleta.scheduler.backend.entity.Settings;
import org.kaleta.scheduler.backend.entity.UserType;
import org.kaleta.scheduler.frontend.GuiModel;

import java.util.*;

/**
 * Created by Stanislav Kaleta on 30.10.2015.
 */
class ItemService {

    /**
     *
     * @return
     */
    public List<UserType> getItemTypes() {
        Settings settings = service.getSettings();
        return settings.getItemTypes();
    }

    /**
     *
     * @param item
     */
    public void addItem(Item item) {
        Month month = service.getMonth(selectedMonthId);

        int newId = 0;
        for (Item existingItem : month.getItems()){
            if (existingItem.getId() > newId){
                newId = existingItem.getId();
            }
        }
        newId = newId + 1;

        item.setDay(selectedDay);
        item.setId(newId);

        month.getItems().add(item);

        service.updateMonth(month);

        update(GuiModel.DAY_CHANGED);
    }

    /**
     *
     * @return
     */
    public List<Item> getRecentlyUsedItems() {
        int counter = 0;
        Map<Item,Integer> recentlyUsed = new HashMap<>();
        for (Item item : service.getMonth(selectedMonthId).getItems()){
            if (recentlyUsed.size() == 0){
                recentlyUsed.put(item, 1);
                counter = 1;
            } else {
                Set<Item> recentItems = new HashSet<>();
                recentItems.addAll(recentlyUsed.keySet());
                boolean set = false;
                for (Item setItem : recentItems){
                    if (setItem.getIncome().equals(item.getIncome())
                            && setItem.getType().equals(item.getType())
                            && setItem.getDescription().equals(item.getDescription())
                            && setItem.getAmount().equals(item.getAmount())){
                        int nUsed = recentlyUsed.get(setItem) + 1;
                        recentlyUsed.put(setItem,nUsed);
                        if (nUsed > counter){
                            counter = nUsed;
                        }
                        set = true;
                        break;
                    }
                }
                if (!set){
                    recentlyUsed.put(item, 1);
                }
            }
        }

        List<Item> subRecentlyUsed = new ArrayList<>();
        int subEdge = 10;
        while (subEdge > 0 && counter > 0){
            for (Item item : recentlyUsed.keySet()){
                if (recentlyUsed.get(item).equals(counter) && subEdge > 0){
                    subRecentlyUsed.add(item);
                    subEdge--;
                }
            }
            counter--;
        }
        return subRecentlyUsed;
    }

    /**
     *
     * @param item
     */
    public void updateItem(Item item) {
        service.updateItem(item, selectedMonthId);
    }

    /**
     *
     * @param item
     */
    public void deleteItem(Item item) {
        service.deleteItem(item, selectedMonthId);

        update(GuiModel.DAY_CHANGED);
    }
}
