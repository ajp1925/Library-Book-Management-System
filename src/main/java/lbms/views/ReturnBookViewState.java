package lbms.views;

import lbms.controllers.CommandController;
import lbms.controllers.ViewController;

import java.util.Scanner;

/**
 * Created by Chris on 3/12/17.
 */
public class ReturnBookViewState implements State {
    private boolean SYSTEM_STATUS;
    private long visitorID;
    private String books = "";
    private String response;

    /**
     * Constructor for FindBorrowedViewState object.
     * @param SYSTEM_STATUS: the status of the system
     */
    public ReturnBookViewState(boolean SYSTEM_STATUS) {
        this.SYSTEM_STATUS = SYSTEM_STATUS;
    }

    /**
     * Initializes the view.
     */
    @Override
    public void init() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nWhat is the ID of the visitor returning the book? ");
        visitorID = scanner.nextLong();

        do {
            System.out.println("What is the id of the book they are returning?");
            books += "," + scanner.next();
            System.out.println("Is the visitor returning another book?");
            response = scanner.next();
        } while (response.toLowerCase().equals("yes") || response.toLowerCase().equals("y"));
    }

    /**
     * Processes the command.
     */
    @Override
    public void onEnter() {
        String response = CommandController.processRequest(this.SYSTEM_STATUS, "return," + visitorID + books + ";");
        System.out.println(CommandController.getCommand().parseResponse(response));
        ViewController.setState(new BooksViewState(SYSTEM_STATUS));
    }

    /**
     * No operation from this method.
     * @param state: the command to handle
     */
    @Override
    public void change(String state) { }
}
