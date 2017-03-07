package lbms.views;

import lbms.controllers.ViewController;

/**
 * Created by Chris on 3/7/17.
 */
public class SystemViewState implements State {
    /**
     * Prompts a user whether to views books or users, or exit
     */
    @Override
    public void init() {
        System.out.println("\nPlease select a command: ");
        System.out.println("clock)     View system time");
        System.out.println("stats)     View system statistics");
        System.out.println("return)    Return to main menu");
    }

    /**
     * NO-OP
     */
    @Override
    public void onEnter() {
        // NO-OP
    }

    /**
     * {@inheritDoc}
     */
    public void change(String state) {
        switch (state) {
            case "clock": ViewController.setState(new ClockViewState()); break;
            case "stats": break;
            case "return": ViewController.setState(new DefaultViewState()); break;
            default:
                System.out.println("Command not found\n");
                this.init();
        }
    }
}
