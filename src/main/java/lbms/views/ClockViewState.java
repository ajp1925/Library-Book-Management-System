package lbms.views;

import lbms.controllers.CommandController;
import lbms.controllers.ViewController;

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
    public ClockViewState(boolean SYSTEM_STATUS) {
        this.SYSTEM_STATUS = SYSTEM_STATUS;
    }

    /**
     * Initializes the ClockViewState.
     */
    @Override
    public void init() {
        String response = CommandController.processRequest("datetime;");
        System.out.println(CommandController.getCommand().parseResponse(response));
        System.out.println("clock)      View system time");
        System.out.println("advance)    Fast-forward clock");
        System.out.println("reset)      Reset the clock to current time");
        System.out.println("return)     Return to main menu");
    }

    @Override
    public void onEnter() {
        // NO-OP TODO
    }

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
                ViewController.setState(new AdvanceViewState(SYSTEM_STATUS));
                break;
            case "reset":
                ViewController.setState(new ResetViewState(SYSTEM_STATUS));
                break;
            case "return":
                ViewController.setState(new DefaultViewState(SYSTEM_STATUS));
                break;
            default:
                System.out.println("Command not found\n");
                this.init();
        }
    }
}
