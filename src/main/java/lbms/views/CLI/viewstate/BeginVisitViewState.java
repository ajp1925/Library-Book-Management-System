package lbms.views.CLI.viewstate;

import lbms.controllers.CommandController;
import lbms.views.CLI.CLIView;

import java.util.Scanner;

/**
 * BeginVisitViewState class that processes the begin visit command.
 * @author Team B
 */
public class BeginVisitViewState implements State {

    private long visitorID;

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
        String response = CommandController.processRequest("arrive," + visitorID + ";");

        try {
            System.out.println(parseResponse(response));
        } catch (Exception e) {
            System.out.println(response);
        }

        CLIView.setState(new UserMenuViewState());
    }

    /**
     * No operation from this method.
     * @param state: the command to handle
     */
    @Override
    public void change(String state) {}

    /**
     * Parses the response for begin visit.
     * @param response: the response string from execute
     * @return a string for output
     */
    public String parseResponse(String response) {
        String[] fields = response.split(",");
        switch (fields[1]) {
            case "duplicate;":
                return "\nVisitor " + visitorID + " is already in the library.";
            case "invalid-id;":
                return "\nVisitor " + visitorID + " is not registered in the system.";
            default:
                return "\nVisitor " + visitorID + " has entered the library on "
                        + fields[2] + " at " + fields[3].replace(";", "") + ".";
        }
    }
}
