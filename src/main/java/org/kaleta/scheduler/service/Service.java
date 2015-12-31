package org.kaleta.scheduler.service;

/**
 * Created by Stanislav Kaleta on 23.07.2015.
 *
 * Puts together logically divided service classes which provide access to data source.
 * Every front-end action should access to back-end via this class.
 */
public class Service {

    /**
     * Returns instance of configuration's service class.
     */
    public static ConfigService configService(){
        return new ConfigService();
    }

    /**
     * Returns instance of month's service class.
     */
    public static MonthService monthService(){
        return new MonthService();
    }

    /**
     * Returns instance of day's service class.
     */
    public static DayService dayService(){
        return new DayService();
    }

    /**
     * Returns instance of item's service class.
     */
    public static ItemService itemService(){
        return new ItemService();
    }
}
