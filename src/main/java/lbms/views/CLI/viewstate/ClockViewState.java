package lbms.views.CLI.viewstate;

import lbms.controllers.commandproxy.ProxyCommandController;
import lbms.views.CLI.CLIView;

/**
 * ClockViewState class processes the clock command.
 * @author Team B
 */
public class ClockViewState implements State {

    /**
     * Initializes the ClockViewState.
     */
    @Override
    public void init() {
        String response = new ProxyCommandController().processRequest("datetime;");

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
                CLIView.setState(new AdvanceViewState());
                break;
            case "reset":
                CLIView.setState(new ResetViewState());
                break;
            case "return":
                CLIView.setState(new SystemViewState());
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
