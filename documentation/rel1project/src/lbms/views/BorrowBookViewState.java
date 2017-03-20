package lbms.views;

import lbms.controllers.CommandController;
import lbms.controllers.ViewController;

import java.util.Scanner;

/**
 * BorrowBookViewState class for views package.
 * @author Team B
 */
public class BorrowBookViewState implements State {

    private boolean SYSTEM_STATUS;
    private long visitorID;
    private String books = "";

    /**
     * Constructor for a BorrowBookViewState object.
     * @param SYSTEM_STATUS: the status of the system
     */
    BorrowBookViewState(boolean SYSTEM_STATUS) {
        this.SYSTEM_STATUS = SYSTEM_STATUS;
    }

    /**
     * Initializes the state.
     */
    @Override
    public void init() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nWhat is the ID of the visitor borrowing the book? ");
        visitorID = scanner.nextLong();
        String response;
        do {
            System.out.println("What is the id of the book they are borrowing?");
            books += "," + scanner.next();
            System.out.println("Is the visitor borrowing another book?");
            response = scanner.next();
        } while(response.toLowerCase().equals("yes") || response.toLowerCase().equals("y"));
    }

    /**
     * Method handles what happens after the state is initialized.
     */
    @Override
    public void onEnter() {
        String response = CommandController.processRequest(this.SYSTEM_STATUS, "borrow," + visitorID + books
                + ";");

        try {
            System.out.println(CommandController.getCommand().parseResponse(response));
        }
        catch(Exception e) {
            System.out.println(response);
        }

        ViewController.setState(new BooksMenuViewState(SYSTEM_STATUS));
    }

    /**
     * No operation for this method.
     * @param state: the command to handle
     */
    @Override
    public void change(String state) {}
}
