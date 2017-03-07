package lbms.views;

import lbms.controllers.CommandController;
import lbms.controllers.ViewController;

import java.util.Scanner;

/**
 * Created by Chris on 3/7/17.
 */
public class BeginVisitViewState implements State {

    private long visitorID;

    @Override
    public void init() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nWhat is the ID of the visitor entering the library?");
        visitorID = scanner.nextLong();
    }

    @Override
    public void onEnter() {
        String[] response = CommandController.processRequest("arrive," + visitorID + ";").split(",");
        if (response[1].equals("duplicate;")) {
            System.out.println("\nVisitor " + visitorID + " is already in the library.");
        } else if (response[1].equals("invalid-id;")) {
            System.out.println("\nVisitor " + visitorID + " is not registered in the system.");
        } else {
            System.out.println("\nVisitor " + visitorID + " has entered the library.");
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
