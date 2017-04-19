package lbms.command;

import lbms.models.SystemDateTime;

/**
 * GetDateTime class that calls the api to get the system time.
 * @author Team B
 */
public class GetDateTime implements Command {

    /**
     * Constructor for GetDateTime.
     */
    public GetDateTime() {}

    /**
     * Gets the system date and time.
     */
    @Override
    public String execute() {
        return "," + SystemDateTime.getInstance(null).getDate().format(SystemDateTime.DATE_FORMAT) + "," +
                SystemDateTime.getInstance(null).getTime().format(SystemDateTime.TIME_FORMAT) + ";";
    }
}
