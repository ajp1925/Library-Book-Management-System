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
     * Execute method calls the API for the given information.
     */
    @Override
    public String execute() {
        API.addDaysToSystemTime(days);
        API.addHoursToSystemTime(hours);
    }

}
