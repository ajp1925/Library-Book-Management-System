package lbms.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Custom date time implementation for the Library Book Management System.
 */
public class SystemDateTime extends Thread implements Serializable {

    private static SystemDateTime instance = null;
    private LocalDateTime time;
    private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E. MMM d, yyyy  hh:mm a");

    /**
     * Runs the thread for the clock.
     */
    @Override
    public void run() {
        while(true) {
            this.time = time.plusMinutes(1);
            System.out.println(this.toString());
            try {
                Thread.sleep(60000);
            }
            catch(Exception ex) {}
        }
    }

    /**
     * Constructor for a SystemDateTime object.
     */
    private SystemDateTime() {
        this.time = LocalDateTime.now();
    }

    /**
     * Gets the instance of the system date time, or creates a new one.
     * @return the instance of the system date time
     */
    public static SystemDateTime getInstance() {
        if(instance == null) {
            instance = new SystemDateTime();
        }
        return instance;
    }

    /**
     * Sets the instance of the system date time.
     * @param instance_: the instance to be set
     */
    public static void setInstance(SystemDateTime instance_) {
        instance = instance_;
    }

    /**
     * Gets the time of the system.
     * @return a local time object of the time
     */
    public LocalTime getTime() {
        return time.toLocalTime();
    }

    /**
     * Gets the date of the system.
     * @return a local date object of the system date
     */
    public LocalDate getDate() {
        return time.toLocalDate();
    }

    /**
     * Gets the system date time.
     * @return a local date time object of the system
     */
    public LocalDateTime getDateTime() {
        return time;
    }

    /**
     * Creates a string of the system date time.
     * @return string representation of the system date time
     */
    public String toString() {
        return time.format(formatter);
    }

    /**
     * Advances the time by days.
     * @param days: the number of days to advance the time
     */
    public void plusDays(long days) {
        time = time.plusDays(days);
    }

    /**
     * Advances the time by hours.
     * @param hours: the number of hours to advance the time
     */
    public void plusHours(long hours) {
        time = time.plusHours(hours);
    }
}
