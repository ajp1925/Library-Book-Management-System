package lbms.views.viewstate;

import lbms.controllers.CommandController;
import lbms.views.CLIView;

import java.util.Scanner;

/**
 * ReturnBookViewState class for views package.
 * @author Team B
 */
public class ReturnBookViewState implements State {

    private boolean SYSTEM_STATUS;
    private long visitorID;
    private String books = "";

    /**
     * Constructor for FindBorrowedViewState object.
     * @param SYSTEM_STATUS: the status of the system
     */
    ReturnBookViewState(boolean SYSTEM_STATUS) {
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
        String response;
        do {
            System.out.println("What is the id of the book they are returning?");
            books += "," + scanner.next();
            System.out.println("Is the visitor returning another book?");
            response = scanner.next();
        } while(response.toLowerCase().equals("yes") || response.toLowerCase().equals("y"));
    }

    /**
     * Processes the command.
     */
    @Override
    public void onEnter() {
        String response = CommandController.processRequest(this.SYSTEM_STATUS, "return," + visitorID + books + ";");

        try {
            System.out.println(parseResponse(response));
        } catch (Exception e) {
            System.out.println(response);
        }

        CLIView.setState(new BooksMenuViewState(SYSTEM_STATUS));
    }

    /**
     * No operation from this method.
     * @param state: the command to handle
     */
    @Override
    public void change(String state) {}

    /**
     * Parses the string for standard output.
     * @param response: the response string from execute
     * @return the output to be printed
     */
    public String parseResponse(String response) {
        switch(response.replaceAll(";$", "") .split(",")[0]) {
            case "invalid-visitor-id":
                return "Invalid visitor ID entered.";
            case "invalid-book-id":
                return "Invalid book ID entered.";
            case "success":
                return "Book(s) successfully returned.";
            case "overdue":
                return "This book is overdue.";
            default:
                return "Unknown option/command.";
        }
    }
}
