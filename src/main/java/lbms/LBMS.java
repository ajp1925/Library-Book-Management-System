package lbms;

import lbms.state.StateManager;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import static lbms.state.StateManager.STATE_DEFAULT;

/**
 * Main class to run the Library Book Management System.
 * @author Team B
 */
public class LBMS {

    private static LBMS instance;
    private static ArrayList<Book> books;
    /**
     * Program entry point. Handle command line arguments and start.
     * @param args: the program arguments
     */
    public static void main(String[] args) {
        new LBMS();
    }

    /**
     * Getter for the list of books
     * @return the books
     */
    public ArrayList<Book> getBooks() {
        return books;
    }

    /**
     * Handles user input for the LBMS system.
     */
    public LBMS() {
        instance = this;

        // Deserialize the data.
        try {
            FileInputStream f = new FileInputStream("books.ser"); // TODO file not created yet
            ObjectInputStream in = new ObjectInputStream(f);
            books = (ArrayList<Book>)in.readObject();
        }
        catch(ClassNotFoundException | IOException e) {
            books = new ArrayList<>();
        }

        StateManager.setState(STATE_DEFAULT);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine();
            if (input.matches("(?i)exit|quit")) {
                break;
            }
            StateManager.getState().handleCommand(input);
        }
        scanner.close();


        // Serializes the data.
        try {
            File fl = new File("books.ser");
            FileOutputStream f = new FileOutputStream(fl); // TODO need to create the files to work
            ObjectOutputStream out = new ObjectOutputStream(f);
            out.writeObject(books);
            out.close();
            f.close();
        }
        catch(IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

    }


    /**
     * Executes a checkout of a book by a visitor.
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
