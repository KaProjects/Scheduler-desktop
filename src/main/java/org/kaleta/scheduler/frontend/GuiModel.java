package org.kaleta.scheduler.frontend;

import org.kaleta.scheduler.backend.entity.Day;
import org.kaleta.scheduler.backend.entity.Item;
import org.kaleta.scheduler.backend.entity.UserType;

import java.awt.*;
import java.util.List;
import java.util.Map;

/**
 * Created by Stanislav Kaleta on 21.08.2015.
 */
public interface GuiModel {
    /**
     * list of commands
     */
    //public static final int UPDATE_ALL = 0;
    public static final int MONTH_CHANGED = 1;
    public static final int DAY_CHANGED = 2;
    //TODO DAY_UPDATED   LANG_CHANGED etc.

    /**
     * TODO documentation
     * smething like update component tree
     * @param command
     */
    public void update(int command);

    public int getSelectedMonthId();

    public Map<Integer,Integer> getMonthsOrder();

    public String getMonthName(int monthId);

    public void selectMonth(int monthId);

    public Day getDayAt(Point position);

    public Day getDay(int dayNumber);

    public int getSelectedDayNumber();

    public void selectDay(int dayNumber);

    public List<UserType> getItemTypes();

    public void addItem(Item item);

    public List<Item> getRecentlyUsedItems();

    public void updateItem(Item item);

    public void deleteItem(Item item);
}
