package lbms.views;

import lbms.controllers.CommandController;
import lbms.controllers.ViewController;
import java.util.Scanner;

/**
 * BeginVisitViewState class that processes the begin visit command.
 * @author Team B
 */
public class BeginVisitViewState implements State {

    private boolean SYSTEM_STATUS;
    private long visitorID;

    /**
     * Constructor for the BeginVisitViewState.
     * @param SYSTEM_STATUS: the initial status of the system
     */
    BeginVisitViewState(boolean SYSTEM_STATUS) {
        this.SYSTEM_STATUS = SYSTEM_STATUS;
    }

    /**
     * Initializes the begin visit view state.
     */
    @Override
    public void init() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nWhat is the ID of the visitor entering the library? ");
        visitorID = scanner.nextLong();
    }

    /**
     * Processes the command string for begin visit.
     */
    @Override
    public void onEnter() {
        String response = CommandController.processRequest(this.SYSTEM_STATUS,"arrive," + visitorID + ";");
        System.out.println(CommandController.getCommand().parseResponse(response));
        ViewController.setState(new UserMenuViewState(SYSTEM_STATUS));
    }

    /**
     * No operation from this method.
     * @param state: the command to handle
     */
    @Override
    public void change(String state) { }
}
