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
     * @param days: the number of days to advance
     * @param hours: the number of hours to advance
     */
    public AdvanceTime(long days, long hours) {
        this.days = days;
        this.hours = hours;
    }

    /**
     * Executes the advance time command.
     * @return the output of errors or null if successful
     */
    @Override
    public String execute() {
        if(days < 0 || days > 7) {
            return "Invalid number of days, " + days + ".";
        }
        if(hours < 0 || hours > 23) {
            return "Invalid number of hours, " + hours + ".";
        }
        API.addDaysToSystemTime(days);
        API.addHoursToSystemTime(hours);
        return null;
    }

}
