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
    public String execute() {
        String output = "datetime,";
        LocalDateTime instance = API.getSystemDateTime();
        output += instance.getYear() + "/" + instance.getMonth() + "/" + instance.getDayOfMonth() + ",";
        output += instance.getHour() + ":" + instance.getMinute() + ":" + instance.getSecond() + ";";
        return output;
    }
}
