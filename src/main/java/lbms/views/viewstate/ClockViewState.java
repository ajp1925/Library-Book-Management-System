package lbms.views.viewstate;

import lbms.controllers.CommandController;
import lbms.views.CLIView;

/**
 * ClockViewState class processes the clock command.
 * @author Team B
 */
public class ClockViewState implements State {

    private boolean SYSTEM_STATUS;

    /**
     * Constructor for a ClockViewState.
     * @param SYSTEM_STATUS: the status of the system
     */
    ClockViewState(boolean SYSTEM_STATUS) {
        this.SYSTEM_STATUS = SYSTEM_STATUS;
    }

    /**
     * Initializes the ClockViewState.
     */
    @Override
    public void init() {
        String response = CommandController.processRequest(this.SYSTEM_STATUS,"datetime;");

        try {
            System.out.println(parseResponse(response));
        }
        catch(Exception e) {
            System.out.println(response);
        }

        System.out.println("clock)      View system time");
        System.out.println("advance)    Fast-forward clock");
        System.out.println("reset)      Reset the clock to current time");
        System.out.println("return)     Return to main menu");
    }

    /**
     * No operation from this method.
     */
    @Override
    public void onEnter() {}

    /**
     * Changes the state.
     * @param state: the command to handle
     */
    @Override
    public void change(String state) {
        switch(state) {
            case "clock":
                this.init();
                break;
            case "advance":
                CLIView.setState(new AdvanceViewState(SYSTEM_STATUS));
                break;
            case "reset":
                CLIView.setState(new ResetViewState(SYSTEM_STATUS));
                break;
            case "return":
                CLIView.setState(new SystemViewState(SYSTEM_STATUS));
                break;
            default:
                System.out.println("Command not found\n");
                this.init();
        }
    }

    /**
     * Parses the response for standard output.
     * @param response: the response string from execute
     * @return the output to be printed
     */
    public String parseResponse(String response) {
        String[] fields = response.split(",");
        return "\nCurrent System Time: " + fields[1] + " " + fields[2];
    }
}
