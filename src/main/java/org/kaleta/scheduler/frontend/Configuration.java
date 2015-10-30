package org.kaleta.scheduler.frontend;

/**
 * Created by Stanislav Kaleta on 30.10.2015.
 */
public interface Configuration {
    /**
     * list of commands
     */
    public static final int INIT_CONFIG = 0;
    public static final int MONTH_CHANGED = 1;
    public static final int DAY_CHANGED = 2;

    /**
     * TODO documentation
     * smething like update component tree
     * @param command
     */
    public void update(int command);

    /**
     * TODO documentation
     * @return
     */
    public int getSelectedMonthId();

    /**
     * TODO documentation
     * @param monthId
     */
    public void selectMonth(int monthId);

    /**
     * TODO documentation
     * @return
     */
    public int getSelectedDayNumber();//C

    /**
     * TODO documentation
     * @param dayNumber
     */
    public void selectDay(int dayNumber);

}
