package lbms.views.CLI.viewstate;

import lbms.controllers.commandproxy.ProxyCommandController;
import lbms.views.CLI.CLIView;

import java.util.Scanner;

/**
 * EndVisitViewState class for views package.
 * @author Team B
 */
public class EndVisitViewState implements State {

    private long visitorID;

    /**
     * Initializes the view.
     */
    @Override
    public void init() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("\nWhat is the ID of the visitor exiting the library? ");
        this.visitorID = scanner.nextLong();
    }

    /**
     * Processes the command.
     */
    @Override
    public void onEnter() {
        String response = new ProxyCommandController().processRequest("depart," + this.visitorID + ";");

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
        String[] fields = response.split(",");
        if (fields[1].equals("invalid-id;")) {
            return "\nVisitor " + this.visitorID + " is not in the library.";
        } else {
            return "\nVisitor " + this.visitorID + " has left the library at "
                    + fields[2] + " and was in the library for " + fields[3];
        }
    }
}



