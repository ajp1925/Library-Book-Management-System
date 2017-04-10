package lbms.command;

import lbms.models.SystemDateTime;

/**
 * AdvanceTime class that calls the API to advance system time.
 * @author Team B TODO -> test for R2
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
        this.days = Long.parseLong(arguments[0]);
        this.hours = arguments.length > 1 ? Long.parseLong(arguments[1]) : 0;
    }

    /**
     * Executes the advance time command.
     * @return the response or error message
     */
    @Override
    public String execute() {
        if (this.days < 0 || this.days > 7) {
            return "invalid-number-of-days," + this.days + ";";
        }
        if (this.hours < 0 || this.hours > 23) {
            return "invalid-number-of-hours," + this.hours + ";";
        }
        if (this.hours == 0 && this.days == 0) {
            return "invalid-number-of-hours," + this.hours + ";";
        }
        SystemDateTime.getInstance(null).plusDays(this.days);
        SystemDateTime.getInstance(null).plusHours(this.hours);
        return "success;";
    }
}
