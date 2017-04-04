package lbms.views.viewstate;

import lbms.controllers.CommandController;
import lbms.views.CLIView;

/**
 * ResetViewState class.
 * @author Team B
 */
public class ResetViewState implements State {

    private boolean SYSTEM_STATUS;

    /**
     * Constructor for a ResetViewState object.
     * @param SYSTEM_STATUS: the status of the system
     */
    ResetViewState(boolean SYSTEM_STATUS) {
        this.SYSTEM_STATUS = SYSTEM_STATUS;
    }

    /**
     * Initializes the view.
     */
    @Override
    public void init() {
        String response = CommandController.processRequest(this.SYSTEM_STATUS,"reset;");

        try {
            System.out.println(parseResponse(response));
        }
        catch(Exception e) {
            System.out.println(response);
        }

        CLIView.setState(new ClockViewState(SYSTEM_STATUS));
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
