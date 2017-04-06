package lbms.views.CLI.viewstate;

import lbms.controllers.CommandController;
import lbms.search.UserSearch;
import lbms.views.CLI.CLIView;

import java.text.DecimalFormat;
import java.util.Scanner;

/**
 * BorrowBookViewState class for views package.
 * @author Team B
 */
public class BorrowBookViewState implements State {

    private long visitorID;
    private String books = "";

    /**
     * Constructor for a BorrowBookViewState object.
     */
    BorrowBookViewState() {}

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
        String response = CommandController.processRequest("borrow," + visitorID + books +";");

        try {
            System.out.println(parseResponse(response));
        }
        catch(Exception e) {
            System.out.println(response);
        }

        CLIView.setState(new BooksMenuViewState());
    }

    /**
     * No operation for this method.
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
        switch (fields[1]) {
            case "invalid-visitor-id;":
                return "\nVisitor " + visitorID + " is not registered in the system.";
            case "outstanding-fine":
                return "\nVisitor " + visitorID + " has to pay " +
                        new DecimalFormat("#.00").format(UserSearch.BY_ID.findFirst(visitorID).getFines()) + " before they " +
                        "can borrow more books.";
            case "book-limit-exceeded;":
                return "\nVisitor " + visitorID + " has borrowed the maximum number of books or the borrow request would " +
                        "cause the visitor to exceed 5 borrowed books.";
            case "invalid-book-id":
                return "\nOne of more of the book IDs specified do not match the IDs for the most recent library book search.";
            default:
                return "\nThe books have been successfully borrowed and will be due on " + fields[2] + ".";
        }
    }
}
