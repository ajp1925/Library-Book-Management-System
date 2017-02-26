package lbms;

import lbms.state.State;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Main class to run the Library Book Management System.
 * @author Team B
 */
public class LBMS {

    private static LBMS instance;
    private static ArrayList<Book> books;
    private static State state; // TODO: Set default state

    /**
     * Program entry point. Handle command line arguments and start.
     * @param args: the program arguments
     */
    public static void main(String[] args) {
        new LBMS();
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    public LBMS() {
        instance = this;
        books = new ArrayList<>();

        // TODO (Nick): Set default state.

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine();

            if (input.matches("(?i)exit|quit")) {
                break;
            }

            state.handleCommand(input);
        }
        scanner.close();
    }


    /**
     * Executes a checkout of a book by a visitor
     *
     * @param visitor the visitor checking out the book
     * @param book the book being checked out
     * @return a Transaction if a successful Transaction is made, else return null
     */
    public Transaction checkOut(Visitor visitor, Book book) {
        if (book.canCheckOut() && visitor.canCheckOut()) {
            Transaction transaction = new Transaction(book.getIsbn(), visitor.getVisitorID(), null, null);       // PLACEHOLDER dates
            book.checkOut();
            visitor.checkOut(transaction);
            return transaction;
        }

        return null;
    }
}
