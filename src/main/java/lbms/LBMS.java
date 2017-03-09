package lbms;

import lbms.controllers.CommandController;
import lbms.controllers.ViewController;
import lbms.models.*;
import lbms.views.DefaultViewState;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.time.LocalTime;

/**
 * Main class to run the Library Book Management System.
 * @author Team B
 */
public class LBMS {

    private static LBMS instance;

    // These are being overwritten on startup, but we instantiate
    // them for the sake of null safety and testing.
    private static HashMap<Long, Book> books = new HashMap<>();
    private static ArrayList<Book> booksToBuy = new ArrayList<>();
    private static ArrayList<Visitor> visitors = new ArrayList<>();
    private static ArrayList<Visit> totalVisits = new ArrayList<>();
    private static ArrayList<Transaction> transactions = new ArrayList<>();
    private static HashMap<Long, Visit> currentVisits = new HashMap<>();
    private boolean shutdown = false;
    private static boolean SYSTEM_STATUS = true;
    private int initial = 0;
    private final static LocalTime openTime = LocalTime.of(8, 0);
    private final static LocalTime closeTime = LocalTime.of(19, 0);

    /**
     * Program entry point. Handle command line arguments and start.
     * @param args: the program arguments
     */
    public static void main(String[] args) {
        boolean console;
        try {
            console = Boolean.parseBoolean(args[0]);
        }
        catch(ArrayIndexOutOfBoundsException e) {
            console = true;
        }
        new LBMS(console);
    }

    /**
     * Handles user input for the LBMS system.
     */
    public LBMS(boolean console) {
        SystemInit();
        Scanner s = new Scanner(System.in);
        if(console) {
            while(true) {
                // Check if library is open
                if(SystemDateTime.getInstance().getTime().isAfter(openTime) &&
                        SystemDateTime.getInstance().getTime().isBefore(closeTime)) {

                    // Check if library just opened or system start
                    if(initial == 0 || initial == 1) {
                        ViewController.setState(new DefaultViewState(true));
                        initial = 2;
                    }
                }
                else {
                    // Check if library just closed or system start
                    if(initial == 0 || initial == 2) {
                        SystemClose();
                        ViewController.setState(new DefaultViewState(false));
                        initial = 1;
                    }
                }

                System.out.print("> ");
                String input = s.nextLine();
                if(input.matches("(?i)exit|quit") || shutdown) {
                    break;
                }
                ViewController.change(input);
            }

        }
        else {
            String input;
            do {
                System.out.print("> ");
                input = s.nextLine();
                System.out.println(CommandController.processRequest(input));
            } while(!input.matches("(?i)exit|quit"));
        }
        s.close();
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
            String line;
            String[] parts;
            String title, publisher;
            ArrayList<String> authors;
            long isbn;
            int pageCount;
            Calendar publishDate = null;
            while(s.hasNextLine()) {
                int i = 1;
                line = s.nextLine();
                parts = line.split(",");
                isbn = Long.parseLong(parts[0]);
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
                    i += 1;
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
                for(int in = 2; in < parts.length; in++) {
                    if(parts[in].charAt(parts[in].length()-1) == '}') {
                        publisher = parts[in+1];
                    }
                }
                if(parts[parts.length-3].length() == 10) {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        Date date = format.parse(parts[parts.length - 3]);
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(date);
                        publishDate = calendar;
                    }
                    catch(ParseException e) {
                        e.printStackTrace();
                    }
                }
                if(parts[parts.length-3].length() == 7) {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
                    try {
                        Date date = format.parse(parts[parts.length - 3]);
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(date);
                        publishDate = calendar;
                    }
                    catch(ParseException e) {
                        e.printStackTrace();
                    }
                }
                if(parts[parts.length-3].length() == 4) {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy");
                    try {
                        Date date = format.parse(parts[parts.length - 3]);
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(date);
                        publishDate = calendar;
                    }
                    catch(ParseException e) {
                        e.printStackTrace();
                    }
                }
                pageCount = Integer.parseInt(parts[parts.length-2]);
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
            books = (HashMap<Long, Book>)in.readObject();
            booksToBuy = (ArrayList<Book>)in.readObject();
            visitors = (ArrayList<Visitor>)in.readObject();
            totalVisits = (ArrayList<Visit>) in.readObject();
            transactions = (ArrayList<Transaction>)in.readObject();
            SystemDateTime.setInstance((SystemDateTime) in.readObject());
        }
        catch(ClassNotFoundException | IOException e) {
            books = new HashMap<Long, Book>();
            booksToBuy = makeBooks();
            visitors = new ArrayList<>();
            totalVisits = new ArrayList<>();
            transactions = new ArrayList<>();
        }

        currentVisits = new HashMap<Long, Visit>();
        SystemDateTime systemDateTime = SystemDateTime.getInstance();
        systemDateTime.start();
    }

    /**
     * Serializes the data in the system for future startup.
     */
    private void SystemClose() {
        System.out.println("\nLibrary is now closing!");

        SystemDateTime.getInstance().stopClock();

        // Departs all the visitors when the library closes.
        for(Visit visit: currentVisits.values()) {
            endVisit(visit.getVisitor());
        }

        // Serializes the data.
        try {
            File fl = new File("data.ser");
            FileOutputStream f = new FileOutputStream(fl);
            ObjectOutputStream out = new ObjectOutputStream(f);
            out.writeObject(books);
            out.writeObject(booksToBuy);
            out.writeObject(visitors);
            out.writeObject(totalVisits);
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
     * Ends and removes the visit from LBMS
     * @return the visit object removed
     */
    // TODO: Find a better place for me
    public static Visit endVisit(Visitor visitor) {
        Visit visit = LBMS.getCurrentVisits().remove(visitor.getVisitorID());
        visit.depart();
        LBMS.getTotalVisits().add(visit);
        return visit;
    }

    /**
     * Used to halt the LBMS system.
     */
    public void shutdown() {
        shutdown = true;
    }

    /**
     * Getter for the hash map of books
     * @return the books
     */
    public static HashMap<Long, Book> getBooks() {
        return books;
    }

    /**
     * Getter for the books to be purchased by the library.
     * @return the array list of books that can be purchased
     */
    public static ArrayList<Book> getBooksToBuy() {
        return booksToBuy;
    }

    /**
     * Getter for the visitors.
     * @return an array list of visitors of the library
     */
    public static ArrayList<Visitor> getVisitors() {
        return visitors;
    }

    /**
     * Getter for the visits made by visitors.
     * @return the array list of visits
     */
    public static ArrayList<Visit> getTotalVisits() {
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
    public static ArrayList<Transaction> getTransactions() {
        return transactions;
    }
}
