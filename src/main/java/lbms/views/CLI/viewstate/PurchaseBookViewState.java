package lbms.views.CLI.viewstate;

import lbms.controllers.commandproxy.ProxyCommandController;
import lbms.models.Book;
import lbms.search.BookSearch;
import lbms.views.CLI.CLIView;

import java.util.List;
import java.util.Scanner;

/**
 * PurchaseBookViewState class for views package.
 * @author Team B
 */
public class PurchaseBookViewState implements State {

    private int quantity;
    private String ids = "";

    /**
     * Initializes the PurchaseBook StateController
     */
    @Override
    public void init() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nWhat quantity of these books would you like to purchase?");
        this.quantity = scanner.nextInt();
        String response;
        do {
            System.out.println("\nPlease enter the ID of the book to purchase.");
            this.ids += "," + scanner.next();
            System.out.println("\nAre you buying another book?");
            response = scanner.next();
        } while (response.toLowerCase().equals("yes") || response.toLowerCase().equals("y"));
    }

    /**
     * Processes the command for purchasing a book.
     */
    @Override
    public void onEnter() {
        String response = new ProxyCommandController().processRequest("buy," + this.quantity + this.ids + ";");

        try {
            System.out.println("\n" + parseResponse(response));
        } catch (Exception e) {
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
        try {
            response = response.replaceAll(";$", "");
            String[] fields = response.split(",");
            if (fields[1].equals("success")) {
                String output = "Book(s) purchased, ";
                List<Book> books;
                for (int i = 2; i < fields.length; i++) {
                    try {
                        books = BookSearch.BY_ISBN.findAll(Long.parseLong(fields[i]));
                        output += books.get(0).getTitle() + " * " + fields[1] + "\n";
                    } catch(NumberFormatException e) {}
                }
                return output;
            }
            return null;
        } catch (Exception e) {
            return "failure;";
        }
    }
}
