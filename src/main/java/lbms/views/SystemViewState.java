package lbms.views;

import lbms.controllers.ViewController;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

/**
 * Created by Chris on 3/7/17.
 */
public class SystemViewState implements State {

    private boolean SYSTEM_STATUS;

    public SystemViewState(boolean SYSTEM_STATUS) {
        this.SYSTEM_STATUS = SYSTEM_STATUS;
    }

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
            case "clock": ViewController.setState(new ClockViewState(SYSTEM_STATUS)); break;
            case "stats": break;
            case "return": ViewController.setState(new DefaultViewState(SYSTEM_STATUS)); break;
            default:
                System.out.println("Command not found\n");
                this.init();
        }
    }
}
