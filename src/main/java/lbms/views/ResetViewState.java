package lbms.views;

import lbms.controllers.CommandController;
import lbms.controllers.ViewController;

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
    public ResetViewState(boolean SYSTEM_STATUS) {
        this.SYSTEM_STATUS = SYSTEM_STATUS;
    }

    /**
     * Initializes the view.
     */
    @Override
    public void init() {
        String response = CommandController.processRequest(this.SYSTEM_STATUS + "," + "reset;");
        System.out.println(CommandController.getCommand().parseResponse(response));
        ViewController.setState(new ClockViewState(SYSTEM_STATUS));
    }

    @Override
    public void onEnter() {
        // NO-OP TODO
    }

    @Override
    public void change(String state) {
        // NO-OP TODO
    }
}
