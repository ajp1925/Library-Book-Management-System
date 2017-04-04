package lbms.command;

import lbms.models.SystemDateTime;

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
        String[] arguments = request.split(",");
        days = Long.parseLong(arguments[0]);
        hours = arguments.length > 1 ? Long.parseLong(arguments[1]) : 0;
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
        if (hours == 0 && days == 0) {
            return "invalid-number-of-hours," + hours + ";";
        }
        SystemDateTime.getInstance(null).plusDays(days);
        SystemDateTime.getInstance(null).plusHours(hours);
        return "success;";
    }
}
