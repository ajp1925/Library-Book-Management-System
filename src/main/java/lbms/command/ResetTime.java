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
        }
        catch(Exception e) {
            return "failure;";
        }
    }

    /**
     * Parses the response for standard output.
     * @param response: the response string from execute
     * @return the output to be printed
     */
    @Override
    public String parseResponse(String response) {
        String[] fields = response.split(",");
        if(fields[1].equals("success;")) {
            return "\nSuccess, system clock has been reset";
        }
        else {
            return "\nFailure, system clock failed to reset";
        }
    }
}
