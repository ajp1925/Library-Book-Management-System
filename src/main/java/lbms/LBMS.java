package lbms;

import lbms.controllers.commandproxy.ProxyCommandController;
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
 * 2 different "modes": API, GUI
 * API: used for directly sending requests and receiving responses.
 * GUI: graphical-user-interface that is based on the API functionality
 *
 * Rochester Institute of Technology
 * SWEN-262 Section: 3, Team B
 * @author Charles Barber   crb7054@rit.edu
 * @author Nicholas Feldman ncf1362@rit.edu
 * @author Christopher Lim  cxl2436@rit.edu
 * @author Anthony Palumbo  ajp1925@rit.edu
 * @author Edward Wong      exw4141@rit.edu
 */
public class LBMS {

    /** StartType enum for determining how the program should be run. */
    public enum StartType { GUI, API }

    /** SearchService enum used for searching for books to buy. */
    public enum SearchService { LOCAL, GOOGLE }

    /** Constants for the opening and closing time. */
    public final static LocalTime OPEN_TIME = LocalTime.of(8, 0);
    public final static LocalTime CLOSE_TIME = LocalTime.of(19, 0);

    /** Data that is serialized on a clean exit. */
    private static HashMap<ISBN, Book> books = new HashMap<>();
    private static List<Book> booksToBuy = new ArrayList<>();
    private static HashMap<Long, Visitor> visitors = new HashMap<>();
    private static HashMap<Long, Employee> employees = new HashMap<>();
    private static List<Visit> totalVisits = new ArrayList<>();
    private static List<Transaction> transactions = new ArrayList<>();

    /** Data that is used during runtime, but not serialized. */
    private static HashMap<Long, Visit> currentVisits = new HashMap<>();
    private static HashMap<Long, Session> sessions = new HashMap<>();
    private static List<Book> lastBookSearch = new ArrayList<>();
    private static long totalSessions = 0;

    /**
     * Program entry point. Handle command line arguments and start.
     * @param args: the program arguments
     */
    public static void main(String[] args) {
        StartType type;
        try {
            type = StartType.valueOf(args[0].toUpperCase());
            new LBMS(type);
        } catch (ArrayIndexOutOfBoundsException e) {
            new LBMS(StartType.GUI);
        } catch (IllegalArgumentException e) {
            System.out.println("Usage: java -jar LBMS.jar <type>");
            System.out.println("Valid types are: GUI or API");
            System.exit(1);
        } finally {
            System.gc();
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
     * Initializes the system.
     * Warnings are suppressed for reading in the serialization.
     */
    @SuppressWarnings("unchecked")
    private void SystemInit() {
        // Deserialize the data.
        try {
            FileInputStream f = new FileInputStream("data.ser");
            ObjectInputStream in = new ObjectInputStream(f);
            books = (HashMap<ISBN, Book>)in.readObject();
            booksToBuy = (ArrayList<Book>)in.readObject();
            visitors = (HashMap<Long, Visitor>)in.readObject();
            employees = (HashMap<Long, Employee>)in.readObject();
            totalVisits = (ArrayList<Visit>)in.readObject();
            transactions = (ArrayList<Transaction>)in.readObject();
            SystemDateTime.getInstance((LocalDateTime)in.readObject()).start();
        } catch (ClassNotFoundException | IOException e) {
            books = new HashMap<>();
            booksToBuy = makeBooks();
            visitors = new HashMap<>();
            employees = new HashMap<>();
            totalVisits = new ArrayList<>();
            transactions = new ArrayList<>();
            SystemDateTime.getInstance(null).start();

            // Admin account creation.
            Employee employee = new Employee(new Visitor("firstname", "lastname", "admin",
                    "password", "address", new PhoneNumber(585,123,1234)));
            visitors.put(employee.getVisitor().getVisitorID(), employee.getVisitor());
            employees.put(employee.getVisitor().getVisitorID(), employee);
        }
        System.gc(); // Collects any unused data and takes out the trash!
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
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Closes the library by removing all current visitors.
     */
    private static void LibraryClose() {
        // Departs all the visitors when the library closes.
        ProxyCommandController pcc = new ProxyCommandController();
        for (Visit visit: currentVisits.values()) {
            pcc.processRequest("depart," + visit.getVisitor().getVisitorID() + ";");
        }
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

            while (s.hasNextLine()) {
                i = 1;
                line = s.nextLine();
                parts = line.split(",");
                isbn = new ISBN(parts[0]);
                title = "";
                authors = new ArrayList<>();
                publisher = "";

                while (parts[i].charAt(0) != '{') {
                    if (parts[i].charAt(0) == '"' && parts[i].charAt(parts[i].length()-1) == '"'){
                        title = parts[i].substring(1, parts[i].length()-1);
                    } else if (parts[i].charAt(0) == '"') {
                        title = title + parts[i].substring(1) + ", ";
                    } else if (parts[i].charAt(parts[i].length()-1) == '"') {
                        title = title + parts[i].substring(0, parts[i].length()-1);
                    } else {
                        title = title + parts[i].substring(1) + ",";
                    }
                    i++;
                }

                for (int in = 2; in < parts.length; in++) {
                    if (parts[in].charAt(0) == '{' && parts[in].charAt(parts[in].length()-1) == '}') {
                        authors.add(parts[in].substring(1, parts[in].length()-1));
                        break;
                    } else if (parts[in].charAt(0) == '{') {
                        authors.add(parts[in].substring(1, parts[in].length()));
                    } else if (parts[in].charAt(parts[in].length()-1) == '}') {
                        authors.add(parts[in].substring(0, parts[in].length()-1));
                        break;
                    } else if (authors.size() > 0) {
                        authors.add(parts[in]);
                    }
                }

                for (int in = 3; in < parts.length; in++) {
                    if (parts[in].charAt(0) == '"' && parts[in].charAt(parts[in].length()-1) == '"'){
                        publisher = parts[in].substring(1, parts[in].length()-1);
                        break;
                    } else if (parts[in].charAt(0) == '"') {
                        publisher = publisher + parts[in].substring(1) + ",";
                    } else if (parts[in].charAt(parts[in].length()-1) == '"' && parts[in+1].matches(".*\\d+.*")) {
                        publisher = publisher + parts[in].substring(0, parts[in].length()-1);
                        break;
                    } else {
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output;
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
     * Getter for the sessions.
     * @return the hash map of sessions
     */
    public static HashMap<Long, Session> getSessions() {
        return sessions;
    }

    /**
     * Getter for the total number of sessions.
     * @return the total number of sessions
     */
    public static long getTotalSessions() {
        return totalSessions;
    }

    /**
     * Increments the total number of sessions by one.
     */
    public static void incrementSessions() {
        totalSessions++;
    }
}
