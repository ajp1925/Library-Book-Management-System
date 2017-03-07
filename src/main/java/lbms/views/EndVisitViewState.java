package lbms.views;

import lbms.controllers.CommandController;
import lbms.controllers.ViewController;

import java.util.Scanner;

/**
 * Created by Chris on 3/7/17.
 */
public class EndVisitViewState implements State {
    private long visitorID;

    @Override
    public void init() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nWhat is the ID of the visitor exiting the library?");
        visitorID = scanner.nextLong();
    }

    @Override
    public void onEnter() {
        String[] response = CommandController.processRequest("depart," + visitorID + ";").split(",");
        if (response[1].equals("invalid-id;")) {
            System.out.println("\nVisitor " + visitorID + " is not in the library.");
        } else {
            System.out.println("\nVisitor " + visitorID + " has left the library at "
                    + response[2] + " and was in the library for " + response[3]);
        }
        ViewController.setState(new UserMenuViewState());
    }

    /**
     * NO-OP
     * @param state The command to handle
     */
    @Override
    public void change(String state) {
        // NO-OP
    }

}



