package lbms;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by Chris on 2/27/17.
 */
public class SystemDateTime implements Serializable {
    private LocalDateTime time;

    public SystemDateTime() {
        this.time = LocalDateTime.now();
    }

    public String getTime() {
        return time.toString();
    }

    public void incrementDays(long days) {
        time.plusDays(days);
    }

    public void incrementHours(long hours) {
        time.plusHours(hours);
    }
}
