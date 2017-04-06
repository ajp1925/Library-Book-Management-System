package lbms.views.CLI.viewstate;

import lbms.controllers.CommandController;
import lbms.views.CLI.CLIView;

/**
 * ResetViewState class.
 * @author Team B
 */
public class ResetViewState implements State {
    /**
     * Constructor for a ResetViewState object.
     */
    ResetViewState() {
    }

    /**
     * Initializes the view.
     */
    @Override
    public void init() {
        String response = CommandController.processRequest("reset;");

        try {
            System.out.println(parseResponse(response));
        }
        catch(Exception e) {
            System.out.println(response);
        }

        CLIView.setState(new ClockViewState());
    }

    /**
     * No operation from this method.
     */
    @Override
    public void onEnter() {}

    /**
     * No operation from this method.
     * @param state: the command to handle
     */
    @Override
    public void change(String state) {}

    /**
     * Parses the response for standard output.
     * @param response: the response string from execute
     * @return the output to be printed
     */
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
