package lbms.command;

import lbms.models.SystemDateTime;

/**
 * ResetTime class used to reset the time during testing.
 * @author Team B TODO -> update for R2
 */
public class ResetTime implements Command {

    /**
     * Constructor for ResetTime command.
     */
    public ResetTime() {}


    /**
     * Executes the reset time command on the system.
     * @return a string of the response
     */
    @Override
    public String execute() {
        try {
            SystemDateTime.getInstance(null).reset();
            return "success;";
        } catch (Exception e) {
            return "failure;";
        }
    }
}
