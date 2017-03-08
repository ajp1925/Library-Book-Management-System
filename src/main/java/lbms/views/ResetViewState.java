package lbms.views;

import lbms.controllers.CommandController;
import lbms.controllers.ViewController;

/**
 * Created by Chris on 3/8/2017.
 */
public class ResetViewState implements State {
    private boolean SYSTEM_STATUS;

    public ResetViewState(boolean SYSTEM_STATUS) {
        this.SYSTEM_STATUS = SYSTEM_STATUS;
    }

    @Override
    public void init() {
        String response = CommandController.processRequest("reset;");
        System.out.println(CommandController.getCommand().parseResponse(response));
        ViewController.setState(new ClockViewState(SYSTEM_STATUS));
    }

    /**
     * NO-OP
     */
    @Override
    public void onEnter() {
        // NO-OP
    }

    /**
     * NO-OP
     */
    @Override
    public void change(String state) {
        // NO-OP
    }
}
