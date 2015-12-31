package org.kaleta.scheduler.frontend;

/**
 * Created by Stanislav Kaleta on 30.10.2015.
 *
 * Provides major app. wide configuration operations.
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
     * Recursively updates component's tree of app.
     * Every component which contains action registered with key equals to command,
     * will be update according to that action.
     * @param command represents type of config. update
     */
    void update(int command);

    /**
     * Applies values of user settings to app.'s components while initialization
     * or after the user modification.
     */
    void applySettings();

    /**
     * Returns actually selected month's id.
     */
    int getSelectedMonthId();

    /**
     * Performs month selection and updates app.
     * @param monthId id of month which has to be selected.
     */
    void selectMonth(int monthId);

    /**
     * Returns actually selected day's number.
     */
    int getSelectedDayNumber();

    /**
     * Performs day selection and updates app.
     * @param dayNumber number of day which has to be selected.
     */
    void selectDay(int dayNumber);
}
