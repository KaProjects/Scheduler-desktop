package org.kaleta.scheduler.backend.entity;

/**
 * Author: Stanislav Kaleta
 * Date: 24.7.2015
 */
public class Time {
    private Integer hour;
    private Integer minutes;

    public Time(){
        hour = null;
        minutes = null;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public Integer getMinutes() {
        return minutes;
    }

    public void setMinutes(Integer minutes) {
        this.minutes = minutes;
    }

    /**
     * @param time as "10:00", "01:05", etc.
     */
    public void setFromString(String time ){
        hour = Integer.valueOf(time.substring(0,2));
        minutes = Integer.valueOf(time.substring(3,5));
    }

    @Override
    public String toString() {
        return String.format("%02d", hour) + ":" + String.format("%02d", minutes);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Time time = (Time) o;

        if (hour != null ? !hour.equals(time.hour) : time.hour != null) return false;
        if (minutes != null ? !minutes.equals(time.minutes) : time.minutes != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = hour != null ? hour.hashCode() : 0;
        result = 31 * result + (minutes != null ? minutes.hashCode() : 0);
        return result;
    }
}
