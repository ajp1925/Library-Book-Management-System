package lbms;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Created by Chris on 2/27/17.
 */
public class SystemDateTime extends Thread implements Serializable {
    private static SystemDateTime instance = null;

    private LocalDateTime time;
    private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E. MMM d, yyyy  hh:mm a");

    @Override
    public void run() {
        while(true)
        {
            this.time = time.plusMinutes(1);
            System.out.println(this.toString());
            try {
                Thread.sleep(60000);
            } catch (Exception ex) {}
        }
    }

    private SystemDateTime() { this.time = LocalDateTime.now(); }

    public static SystemDateTime getInstance() {
        if(instance == null) {
            instance = new SystemDateTime();
        }
        return instance;
    }

    public LocalTime getTime() { return time.toLocalTime(); }

    public LocalDate getDate() { return time.toLocalDate(); }

    public LocalDateTime getDateTime() { return time; }

    public String toString() { return time.format(formatter); }

    public void plusDays(long days) { time = time.plusDays(days); }

    public void plusHours(long hours) { time = time.plusHours(hours); }
}
