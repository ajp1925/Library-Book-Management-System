package lbms.views;

import lbms.controllers.CommandController;
import lbms.controllers.ViewController;
import java.util.Scanner;

/**
 * EndVisitViewState class for views package.
 * @author Team B
 */
public class EndVisitViewState implements State {

    private boolean SYSTEM_STATUS;
    private long visitorID;

    /**
     * Constructor for an EndVisitViewState object.
     * @param SYSTEM_STATUS: the status of the system
     */
    public EndVisitViewState(boolean SYSTEM_STATUS) {
        this.SYSTEM_STATUS = SYSTEM_STATUS;
    }

    /**
     * Initializes the view.
     */
    @Override
    public void init() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nWhat is the ID of the visitor exiting the library? ");
        visitorID = scanner.nextLong();
    }

    /**
     * Processes the command.
     */
    @Override
    public void onEnter() {
        String response = CommandController.processRequest(this.SYSTEM_STATUS + "," + "depart," + visitorID + ";");
        System.out.println(CommandController.getCommand().parseResponse(response));
        ViewController.setState(new UserMenuViewState(SYSTEM_STATUS));
    }

    @Override
    public void change(String state) {
        // NO-OP TODO
    }
}



