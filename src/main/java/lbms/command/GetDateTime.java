package lbms.command;

import lbms.models.SystemDateTime;

/**
 * GetDateTime class that calls the API to get the system time.
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
        return SystemDateTime.getInstance(null).getDate().format(SystemDateTime.DATE_FORMAT) + "," +
                SystemDateTime.getInstance(null).getTime().format(SystemDateTime.TIME_FORMAT) + ";";
    }

    /**
     * Parses the response for standard output.
     * @param response: the response string from execute
     * @return the output to be printed
     */
    @Override
    public String parseResponse(String response) {
        String[] fields = response.split(",");
        return "\nCurrent System Time: " + fields[1] + " " + fields[2];
    }
}
