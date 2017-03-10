package lbms.views;

import lbms.controllers.CommandController;
import lbms.controllers.ViewController;
import java.util.Scanner;

/**
 * FindBorrowedViewState class.
 * @author Team B
 */
public class FindBorrowedViewState implements State {

    private boolean SYSTEM_STATUS;
    private long visitorID;

    /**
     * Constructor for FindBorrowedViewState object.
     * @param SYSTEM_STATUS: the status of the system
     */
    public FindBorrowedViewState(boolean SYSTEM_STATUS) {
        this.SYSTEM_STATUS = SYSTEM_STATUS;
    }

    /**
     * Initializes the view.
     */
    @Override
    public void init() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nWhat is the ID of the visitor you are querying?");
        visitorID = scanner.nextLong();
    }

    /**
     * Processes the command.
     */
    @Override
    public void onEnter() {
        String response = CommandController.processRequest(this.SYSTEM_STATUS,"borrowed," + visitorID + ";");
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
