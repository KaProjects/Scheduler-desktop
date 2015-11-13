package org.kaleta.scheduler.service;

/**
 * User: Stanislav Kaleta
 * Date: 23.7.2015
 */
public class Service {

    /**
     * TODO documentation
     * @return
     */
    public static ConfigService configService(){
        return new ConfigService();
    }

    /**
     * TODO documentation
     * @return
     */
    public static MonthService monthService(){
        return new MonthService();
    }

    /**
     * TODO documentation
     * @return
     */
    public static DayService dayService(){
        return new DayService();
    }

    /**
     * TODO documentation
     * @return
     */
    public static ItemService itemService(){
        return new ItemService();
    }


}
