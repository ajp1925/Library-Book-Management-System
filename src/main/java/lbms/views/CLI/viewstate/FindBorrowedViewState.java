package lbms.views.CLI.viewstate;

import lbms.controllers.CommandController;
import lbms.views.CLI.CLIView;

import java.util.Scanner;

/**
 * FindBorrowedViewState class.
 * @author Team B
 */
public class FindBorrowedViewState implements State {

    private long visitorID;

    /**
     * Constructor for FindBorrowedViewState object.
     */
    FindBorrowedViewState() {

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
        String response = CommandController.processRequest("borrowed," + visitorID + ";");

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
     * Parses the response for standard output.
     * @param response: the response string from execute
     * @return the output to be printed
     */
    public String parseResponse(String response) {
        String[] fields = response.replace(";", "").split("\n", 2);
        if (fields.length == 1) {
            if (fields[0].endsWith("0")) {
                return "\nVisitor " + visitorID + " has no borrowed books.";
            } else {
                return "\nVisitor " + visitorID + " is not valid.";
            }
        }
        else {
            return "\n" + fields[1];
        }
    }
}
