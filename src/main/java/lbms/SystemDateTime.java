package lbms;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by Chris on 2/27/17.
 */
public class SystemDateTime implements Runnable, Serializable {
    public static SystemDateTime instance = null;

    private static LocalDateTime time;
    private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E. MMM d, yyyy  hh:mm a");

    @Override
    public void run() {
        while(true)
        {
            this.time = LocalDateTime.now();
            try {
                Thread.sleep(60000);
            } catch (Exception ex) {}
        }
    }

    private SystemDateTime() {
        this.time = LocalDateTime.now();
    }

    public LocalTime getTime() {
        return time.toLocalTime();
    }

    public LocalDate getDate() {
        return time.toLocalDate();
    }

    public LocalDateTime getDateTime() {
        return time;
    }

    public String toString() {
        return time.format(formatter);
    }

    public void incrementDays(long days) {
        time.plusDays(days);
    }

    public void incrementHours(long hours) {
        time.plusHours(hours);
    }
}
