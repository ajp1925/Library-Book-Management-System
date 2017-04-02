package lbms.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Observable;

/**
 * Custom date time implementation for the Library Book Management System.
 * @author Team B
 */
public class SystemDateTime extends Thread {

    private static SystemDateTime instance = null;
    private LocalDateTime time;
    private volatile boolean stop = false;

    /** Formats for the date time. */
    private final static DateTimeFormatter DATETIME_FORMAT = DateTimeFormatter.ofPattern("yyyy/MM/dd, HH:mm:ss");
    public final static DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    public final static DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");

    /**
     * Runs the thread for the clock.
     */
    @Override
    public void run() {
        while(!stop) {
            this.time = time.plusSeconds(1);
            try {
                Thread.sleep(1000);
            }
            catch(InterruptedException e) {
                System.err.print("");
            }
        }
    }

    /**
     * Constructor for a SystemDateTime object.
     */
    private SystemDateTime() {
        this.time = LocalDateTime.now();
    }

    /**
     * Constructor for a SystemDateTime object after de-serialization.
     * @param time: the time from the previous startup
     */
    private SystemDateTime(LocalDateTime time) {
        this.time = time;
    }

    /**
     * Gets the instance of the system date time, or creates a new one.
     * @return the instance of the system date time
     */
    private static SystemDateTime getInstance() {
        if(instance == null) {
            instance = new SystemDateTime();
        }
        return instance;
    }

    /**
     * Gets the instance of the system date time, or creates a new one, may set the time.
     * @param time: the previous system time
     * @return an instance of the SystemDateTime
     */
    public static SystemDateTime getInstance(LocalDateTime time) {
        if(time == null) {
            return getInstance();
        }
        else if(instance == null) {
            instance = new SystemDateTime(time);
        }
        return instance;
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
        return time.format(DATETIME_FORMAT);
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

    /**
     * Resets the time.
     */
    public void reset() {
        this.time = LocalDateTime.now();
    }

    /**
     * Stops the clock.
     */
    public void stopClock() {
        this.stop = true;
    }
}
