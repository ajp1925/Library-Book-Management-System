package lbms.views;

import lbms.command.GetDateTime;
import lbms.controllers.CommandController;
import lbms.controllers.ViewController;

/**
 * Created by Chris on 3/7/17.
 */
public class ClockViewState implements State {
    /**
     * Prompts a user whether to views books or users, or exit
     */
    @Override
    public void init() {
        String[] clock = CommandController.processRequest("datetime").split(",");
        System.out.println("\n" + clock[1] + " " + clock[2]);
        ViewController.setState(new SystemViewState());
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
    public void change(String state) {
        // NO-OP
    }
}
