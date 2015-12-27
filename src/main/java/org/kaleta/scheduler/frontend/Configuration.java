package org.kaleta.scheduler.frontend;

/**
 * Created by Stanislav Kaleta on 30.10.2015.
 */
public interface Configuration {
    /**
     * list of commands
     */
    int INIT_CONFIG = 0;
    int MONTH_CHANGED = 1;
    int DAY_CHANGED = 2;
    int ITEM_CHANGED = 3;

    /**
     * TODO documentation
     * smething like update component tree
     * @param command
     */
    void update(int command);

    /**
     * TODO documentation
     */
    void applySettings();

    /**
     * TODO documentation
     * @return
     */
    int getSelectedMonthId();

    /**
     * TODO documentation
     * @param monthId
     */
    void selectMonth(int monthId);

    /**
     * TODO documentation
     * @return
     */
    int getSelectedDayNumber();

    /**
     * TODO documentation
     * @param dayNumber
     */
    void selectDay(int dayNumber);

}
