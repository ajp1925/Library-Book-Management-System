package lbms.command;

import lbms.API;

/**
 * AdvanceTime class that calls the API to advance system time.
 * @author Team B
 */
public class AdvanceTime implements Command {

    private long days;
    private long hours;

    /**
     * Constructor for AdvanceTime class.
     * @param request: the input string of the request
     */
    public AdvanceTime(String request) {
        request = request.replaceAll(";$", "");
        String[] arguments = request.split(",");
        days = Long.parseLong(arguments[0]);
        hours = Long.parseLong(arguments[1]);
    }

    /**
     * Executes the advance time command.
     * @return the response or error message
     */
    @Override
    public String execute() {
        if(days < 0 || days > 7) {
            return "invalid-number-of-days," + days + ";";
        }
        if(hours < 0 || hours > 23) {
            return "invalid-number-of-hours," + hours + ";";
        }
        API.addDaysToSystemTime(days);
        API.addHoursToSystemTime(hours);
        return "success;";
    }

}
