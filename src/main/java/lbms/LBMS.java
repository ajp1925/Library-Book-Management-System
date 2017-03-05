package lbms;

import lbms.state.StateManager;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import static lbms.state.StateManager.STATE_DEFAULT;

/**
 * Main class to run the Library Book Management System.
 * @author Team B
 */
public class LBMS {

    private static LBMS instance;
    private static ArrayList<Book> books;
    private static ArrayList<Visitor> visitors;
    private static ArrayList<Visit> visits;
    private static ArrayList<Transaction> transactions;

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
            FileInputStream f = new FileInputStream("data.ser");
            ObjectInputStream in = new ObjectInputStream(f);
            books = (ArrayList<Book>)in.readObject();
            visitors = (ArrayList<Visitor>)in.readObject();
            visits = (ArrayList<Visit>)in.readObject();
            transactions = (ArrayList<Transaction>)in.readObject();
            //time = (SystemDateTime)in.readObject(); // TODO: Set SystemDateTime
        }
        catch(ClassNotFoundException | IOException e) {
            books = makeBooks();
            visitors = new ArrayList<>();
            visits = new ArrayList<>();
            transactions = new ArrayList<>();
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
            File fl = new File("data.ser");
            FileOutputStream f = new FileOutputStream(fl); // TODO need to create the files to work
            ObjectOutputStream out = new ObjectOutputStream(f);
            out.writeObject(books);
            out.writeObject(visitors);
            out.writeObject(visits);
            out.writeObject(transactions);
            out.writeObject(SystemDateTime.getInstance());
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
            Transaction transaction = new Transaction(book.getIsbn(), visitor.getVisitorID());       // PLACEHOLDER dates
            book.checkOut();
            visitor.checkOut(transaction);
            return transaction;
        }
        return null;
    }

    private ArrayList<Book> makeBooks() {
        ArrayList<Book> output = new ArrayList<>();
        try {
            File books = new File("books.txt");
            Scanner s = new Scanner(books);
            String line;
            String[] parts;
            String title, publisher;
            ArrayList<String> authors;
            long isbn;
            int pageCount, numberOfCopies, copiesCheckedOut;
            Calendar publishDate = null;
            while (s.hasNextLine()) {
                int i = 1;
                line = s.nextLine();
                parts = line.split(",");
                isbn = Long.parseLong(parts[0]);
                title = "";
                authors = new ArrayList<>();
                publisher = "";
                while (parts[i].charAt(0) != '{') {
                    if (parts[i].charAt(0) == '"' && parts[i].charAt(parts[i].length()-1) == '"'){
                        title = parts[i].substring(1, parts[i].length()-1);
                    }
                    else if (parts[i].charAt(0) == '"') {
                        title = title + parts[i].substring(1) + ", ";
                    }
                    else if (parts[i].charAt(parts[i].length()-1) == '"') {
                        title = title + parts[i].substring(0, parts[i].length()-1);
                    }
                    else {
                        title = title + parts[i].substring(1) + ",";
                    }
                    i += 1;
                }
                for (int in = 2; in < parts.length; in++) {
                    if (parts[in].charAt(0) == '{' && parts[in].charAt(parts[in].length()-1) == '}') {
                        authors.add(parts[in].substring(1, parts[in].length()-1));
                        break;
                    }
                    else if (parts[in].charAt(0) == '{') {
                        authors.add(parts[in].substring(1, parts[in].length()));
                    }
                    else if (parts[in].charAt(parts[in].length()-1) == '}') {
                        authors.add(parts[in].substring(0, parts[in].length()-1));
                        break;
                    }
                    else if (authors.size() > 0) {
                        authors.add(parts[in]);
                    }
                }
                for (int in = 2; in < parts.length; in++) {
                    if (parts[in].charAt(parts[in].length()-1) == '}') {
                        publisher = parts[in+1];
                    }
                }
                if (parts[parts.length-3].length() == 10) {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        Date date = format.parse(parts[parts.length - 3]);
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(date);
                        publishDate = calendar;
                    }
                    catch (ParseException e) {
                        System.out.println(e);
                    }
                }
                if (parts[parts.length-3].length() == 7) {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
                    try {
                        Date date = format.parse(parts[parts.length - 3]);
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(date);
                        publishDate = calendar;
                    }
                    catch (ParseException e) {
                        System.out.println(e);
                    }
                }
                if (parts[parts.length-3].length() == 4) {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy");
                    try {
                        Date date = format.parse(parts[parts.length - 3]);
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(date);
                        publishDate = calendar;
                    }
                    catch (ParseException e) {
                        System.out.println(e);
                    }
                }
                pageCount = Integer.parseInt(parts[parts.length-2]);
                // TODO create a book object
                Book b = new Book(isbn, title, authors, publisher, publishDate, pageCount, 0, 0);
                output.add(b);
            }
        }
        catch (FileNotFoundException e) {
            System.out.println(e);
        }
        return output;
    }
}
