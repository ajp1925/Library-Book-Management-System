package lbms.command;

import lbms.API;
import lbms.models.SystemDateTime;

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
        return API.getSystemDate().format(SystemDateTime.DATE_FORMAT) + "," +
                API.getSystemTime().format(SystemDateTime.TIME_FORMAT);
    }
}
