package lbms.command;

import lbms.API;
import java.time.LocalDateTime;

/**
 * GetDateTime class that calls the API to get the system time.
 * @author Team B
 */
public class GetDateTime implements Command {

    /**
     * Constructor for GetDateTime.
     */
    public GetDateTime() { }

    /**
     * Gets the system date and time.
     */
    @Override
    public void execute() {
        LocalDateTime instance = API.getSystemDateTime();
        System.out.println(instance.getYear() + "/" + instance.getMonth() + "/" + instance.getDayOfMonth());
        System.out.println(instance.getHour() + ":" + instance.getMinute() + ":" + instance.getSecond());
    }

}
