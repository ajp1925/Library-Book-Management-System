package lbms.views;

import lbms.command.GetDateTime;
import lbms.controllers.CommandController;
import lbms.controllers.ViewController;

/**
 * Created by Chris on 3/7/17.
 */
public class ClockViewState implements State {
    @Override
    public void init() {
        String[] response = CommandController.processRequest("datetime").split(",");
        System.out.println("\nCurrent System Time: " + response[1] + " " + response[2]);
        System.out.println("advance)    Fast-forward clock");
        System.out.println("return)     Return to main menu");
    }

    /**
     * NO-OP
     */
    @Override
    public void onEnter() {
        // NO-OP
    }

    @Override
    public void change(String state) {
        switch (state) {
            case "advance": ViewController.setState(new AdvanceViewState()); break;
            case "return": ViewController.setState(new DefaultViewState()); break;
            default:
                System.out.println("Command not found\n");
                this.init();
        }
    }
}
