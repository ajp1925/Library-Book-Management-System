package lbms;

import lbms.controllers.CommandController;
import lbms.models.*;
import lbms.views.ViewFactory;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

/**
 * Main class to run the Library Book Management System.
 * @author Team B
 */
public class LBMS {

    public enum StartType {
        GUI, CLI, API;
    }

    public final static LocalTime OPEN_TIME = LocalTime.of(8, 0);
    public final static LocalTime CLOSE_TIME = LocalTime.of(19, 0);

    private static LBMS instance;
    private static HashMap<ISBN, Book> books;
    private static List<Book> lastBookSearch = new ArrayList<>();
    private static List<Book> booksToBuy;
    private static HashMap<Long, Visitor> visitors;
    private static HashMap<Long, Employee> employees;
    private static List<Visit> totalVisits;
    private static List<Transaction> transactions;
    private static HashMap<Long, Visit> currentVisits;
    private static List<Visitor> loggedIn;

    /**
     * Program entry point. Handle command line arguments and start.
     * @param args: the program arguments
     */
    public static void main(String[] args) {
        if (args.length >= 1) {
            new LBMS(StartType.valueOf(args[0].toLowerCase()));
        } else {
            System.out.println("Usage: java LBMS.jar <type>");
            System.out.println("Valid types are: GUI, CLI, API");
        }
    }

    /**
     * Handles user input for the LBMS system.
     */
    public LBMS(StartType type) {
        SystemInit();
        ViewFactory.start(type);
        SystemClose();
    }

    /**
     * Creates the books to be purchased from the input file.
     * @return an array list of books that the library can purchase
     */
    private ArrayList<Book> makeBooks() {
        ArrayList<Book> output = new ArrayList<>();
        try {
            InputStream inputStream = LBMS.class.getClassLoader().getResourceAsStream("books.txt");
            Scanner s = new Scanner(inputStream);
            String[] parts;
            String line, title, publisher;
            ArrayList<String> authors;
            ISBN isbn;
            int pageCount, i;
            Calendar publishDate = null;

            while(s.hasNextLine()) {
                i = 1;
                line = s.nextLine();
                parts = line.split(",");
                isbn = new ISBN(parts[0]);
                title = "";
                authors = new ArrayList<>();
                publisher = "";

                while(parts[i].charAt(0) != '{') {
                    if(parts[i].charAt(0) == '"' && parts[i].charAt(parts[i].length()-1) == '"'){
                        title = parts[i].substring(1, parts[i].length()-1);
                    }
                    else if(parts[i].charAt(0) == '"') {
                        title = title + parts[i].substring(1) + ", ";
                    }
                    else if(parts[i].charAt(parts[i].length()-1) == '"') {
                        title = title + parts[i].substring(0, parts[i].length()-1);
                    }
                    else {
                        title = title + parts[i].substring(1) + ",";
                    }
                    i++;
                }

                for(int in = 2; in < parts.length; in++) {
                    if(parts[in].charAt(0) == '{' && parts[in].charAt(parts[in].length()-1) == '}') {
                        authors.add(parts[in].substring(1, parts[in].length()-1));
                        break;
                    }
                    else if(parts[in].charAt(0) == '{') {
                        authors.add(parts[in].substring(1, parts[in].length()));
                    }
                    else if(parts[in].charAt(parts[in].length()-1) == '}') {
                        authors.add(parts[in].substring(0, parts[in].length()-1));
                        break;
                    }
                    else if(authors.size() > 0) {
                        authors.add(parts[in]);
                    }
                }

                for(int in = 3; in < parts.length; in++) {
                    if(parts[in].charAt(0) == '"' && parts[in].charAt(parts[in].length()-1) == '"'){
                        publisher = parts[in].substring(1, parts[in].length()-1);
                        break;
                    }
                    else if(parts[in].charAt(0) == '"') {
                        publisher = publisher + parts[in].substring(1) + ",";
                    }
                    else if(parts[in].charAt(parts[in].length()-1) == '"' && parts[in+1].matches(".*\\d+.*")) {
                        publisher = publisher + parts[in].substring(0, parts[in].length()-1);
                        break;
                    }
                    else {
                        publisher = publisher + parts[in].substring(1) + ",";
                    }
                }

                try {
                    if (parts[parts.length - 2].length() == 10) {
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        Date date = format.parse(parts[parts.length - 2]);
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(date);
                        publishDate = calendar;
                    } else if (parts[parts.length - 2].length() == 7) {
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
                        Date date = format.parse(parts[parts.length - 2]);
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(date);
                        publishDate = calendar;
                    } else if (parts[parts.length - 2].length() == 4) {
                        SimpleDateFormat format = new SimpleDateFormat("yyyy");
                        Date date = format.parse(parts[parts.length - 2]);
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(date);
                        publishDate = calendar;
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                pageCount = Integer.parseInt(parts[parts.length-1]);
                output.add(new Book(isbn, title, authors, publisher, publishDate, pageCount, 0, 0));
            }
            inputStream.close();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        return output;
    }

    /**
     * Initializes the system.
     */
    private void SystemInit() {
        instance = this;

        // Deserialize the data.
        try {
            FileInputStream f = new FileInputStream("data.ser");
            ObjectInputStream in = new ObjectInputStream(f);
            books = (HashMap<ISBN, Book>) in.readObject();
            booksToBuy = (ArrayList<Book>) in.readObject();
            visitors = (HashMap<Long, Visitor>) in.readObject();
            employees = (HashMap<Long, Employee>) in.readObject();
            totalVisits = (ArrayList<Visit>) in.readObject();
            transactions = (ArrayList<Transaction>) in.readObject();
            SystemDateTime.getInstance((LocalDateTime)in.readObject()).start();
        } catch (ClassNotFoundException | IOException e) {
            books = new HashMap<>();
            booksToBuy = makeBooks();
            visitors = new HashMap<>();
            employees = new HashMap<>();
            totalVisits = new ArrayList<>();
            transactions = new ArrayList<>();
            SystemDateTime.getInstance(null).start();
        }
        currentVisits = new HashMap<>();
    }

    /**
     * Serializes the data in the system for future startup.
     */
    private void SystemClose() {
        SystemDateTime.getInstance(null).stopClock();

        LibraryClose();

        // Serializes the data.
        try {
            File fl = new File("data.ser");
            FileOutputStream f = new FileOutputStream(fl);
            ObjectOutputStream out = new ObjectOutputStream(f);
            out.writeObject(books);
            out.writeObject(booksToBuy);
            out.writeObject(visitors);
            out.writeObject(employees);
            out.writeObject(totalVisits);
            out.writeObject(transactions);
            out.writeObject(SystemDateTime.getInstance(null).getDateTime());
            out.close();
            f.close();
        }
        catch(IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static void LibraryClose() {
        // Departs all the visitors when the library closes.
        for(Visit visit: currentVisits.values()) {
            CommandController.processRequest(false, "depart," +
                    visit.getVisitor().getVisitorID() + ";");
        }
    }

    /**
     * Getter for the hash map of books
     * @return the books
     */
    public static HashMap<ISBN, Book> getBooks() {
        return books;
    }

    /**
     * Getter for the last set of books from a search.
     * @return the last books returned from a search
     */
    public static List<Book> getLastBookSearch() {
        return lastBookSearch;
    }

    /**
     * Getter for the books to be purchased by the library.
     * @return the array list of books that can be purchased
     */
    public static List<Book> getBooksToBuy() {
        return booksToBuy;
    }

    /**
     * Getter for the visitors.
     * @return a hash map of visitors of the library
     */
    public static HashMap<Long, Visitor> getVisitors() {
        return visitors;
    }

    /**
     * Getter for the employees.
     * @return a hash map of employees of the library
     */
    public static HashMap<Long, Employee> getEmployees() {
        return employees;
    }

    /**
     * Getter for the visits made by visitors.
     * @return the array list of visits
     */
    public static List<Visit> getTotalVisits() {
        return totalVisits;
    }


    /**
     * Getter for the currentVisits.
     * @return hash map of the current visits
     */
    public static HashMap<Long, Visit> getCurrentVisits() {
        return currentVisits;
    }

    /**
     * Getter for the transactions.
     * @return an array list of transactions
     */
    public static List<Transaction> getTransactions() {
        return transactions;
    }

    /**
     * Used for generating account IDs.
     * @return the total number of accounts in the library
     */
    public static long totalAccounts() {
        return visitors.size() + employees.size();
    }

    /**
     * Getter for logged in visitors/employees.
     * @return the list of logged in visitors/employees
     */
    public static List<Visitor> getLoggedIn() {
        return loggedIn;
    }

    /**
     * Gets the Employee object based on the visitor account given.
     * @param v: the visitor account of the employee
     * @return the employee account
     */
    public static Employee getEmployeeByLoggedIn(Visitor v) {
        if(loggedIn.contains(v)) {
            for(Employee e: employees.values()) {
                if(e.getVisitor() == v) {
                    return e;
                }
            }
        }
        return null;
    }
}
