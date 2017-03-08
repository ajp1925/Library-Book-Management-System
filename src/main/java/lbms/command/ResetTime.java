package lbms.command;

import lbms.models.SystemDateTime;

/**
 * Created by Chris on 3/8/2017.
 */
public class ResetTime implements Command {

    @Override
    public String execute() {
        try {
            SystemDateTime.getInstance().reset();
            return "success;";
        } catch (Exception e) {
            return "failure;";
        }
    }

    @Override
    public String parseResponse(String response) {
        String[] fields = response.split(",");
        if (fields[1].equals("success;")) {
            return "\nSuccess, system clock has been reset";
        } else {
            return "\nFailure, system clock failed to reset";
        }
    }
}
