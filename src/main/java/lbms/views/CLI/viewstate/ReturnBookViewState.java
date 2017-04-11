package lbms.views.CLI.viewstate;

import lbms.controllers.commandproxy.ProxyCommandController;
import lbms.views.CLI.CLIView;

import java.util.Scanner;

/**
 * ReturnBookViewState class for views package.
 * @author Team B
 */
public class ReturnBookViewState implements State {

    private long visitorID;
    private String books = "";

    /**
     * Initializes the view.
     */
    @Override
    public void init() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nWhat is the ID of the visitor returning the book? ");
        this.visitorID = scanner.nextLong();
        String response;
        do {
            System.out.println("What is the id of the book they are returning?");
            this.books += "," + scanner.next();
            System.out.println("Is the visitor returning another book?");
            response = scanner.next();
        } while (response.toLowerCase().equals("yes") || response.toLowerCase().equals("y"));
    }

    /**
     * Processes the command.
     */
    @Override
    public void onEnter() {
        String response = new ProxyCommandController().processRequest("return," + this.visitorID + this.books + ";");

        try {
            System.out.println(parseResponse(response));
        } catch (Exception e) {
            System.out.println(response);
        }

        CLIView.setState(new BooksMenuViewState());
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
        switch (response.replaceAll(";$", "") .split(",")[0]) {
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
