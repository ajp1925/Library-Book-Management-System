package lbms;

import lbms.models.*;
import lbms.search.Search;
import lbms.search.SearchByISBN;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Facade class for interacting with the system.
 * @author Team B
 */
public class API {

    public static DecimalFormat df = new DecimalFormat("#.00");

    /**
     * Registers a visitor with the system, if they are not already registered
     * @param visitor: The visitor to register
     * @return true if successfully registered, false if duplicate
     */
    public static boolean registerVisitor(Visitor visitor) {
        if (!visitorIsRegisteredID(visitor.getVisitorID()) && !visitorIsRegisteredName(visitor.getName()) &&
                visitorIsRegisteredAddress(visitor.getAddress())) {
            LBMS.getVisitors().add(visitor);
            return true;
        }
        return false;
    }

    /**
     * Checks whether a visitor is registered with the system.
     * @param id: the id of the visitor
     * @return True if a visitor is registered
     */
    public static boolean visitorIsRegisteredID(long id) {
        return getVisitorByID(id) != null;
    }

    /**
     * Checks whether a visitor is registered with the system.
     * @param name: the name of the visitor
     * @return True if a visitor is registered
     */
    private static boolean visitorIsRegisteredName(String name) {
        return getVisitorByName(name) != null;
    }

    /**
     * Checks whether a visitor is registered with the system.
     * @param address: the address of the visitor
     * @return True if a visitor is registered
     */
    private static boolean visitorIsRegisteredAddress(String address) {
        return getVisitorByAddress(address) != null;
    }

    /**
     * Gets a visitor from their unique ID
     * @param visitorID: the visitor ID
     * @return The Visitor if it exists, or null
     */
    public static Visitor getVisitorByID(long visitorID) {
        return LBMS.getVisitors().parallelStream()
                .filter(visitor -> visitor.getVisitorID() == visitorID)
                .findFirst().orElse(null);
    }

    /**
     * Gets the visitor with the same name.
     * @param name: the first and last name combined of the visitor
     * @return the visitor object
     */
    private static Visitor getVisitorByName(String name) {
        return LBMS.getVisitors().parallelStream()
                .filter(visitor -> visitor.getName().equals(name))
                .findFirst().orElse(null);
    }

    /**
     * Gets a visitor by their address.
     * @param address: the address of a visitor
     * @return the visitor object of the visitor with the given address
     */
    private static Visitor getVisitorByAddress(String address) {
        return LBMS.getVisitors().parallelStream()
                .filter(visitor -> visitor.getAddress().equals(address))
                .findFirst().orElse(null);
    }

    /**
     * Buys a book for the library
     * @param book: The book to buy
     */
    private static void buyBook(Book book) {
        LBMS.getBooks().put(book.getIsbn(), book);
    }

    /**
     * Finds the books based on the given search method.
     * @param search: the searched method to be used
     * @return a list of books that the search provided
     */
    public static List<Book> findBooks(Search search) {
        return search.search(LBMS.getBooks());
    }

    /**
     * Gets the LocalDateTime for the system
     * @return the system time
     */
    public static LocalDate getSystemDate() { return SystemDateTime.getInstance().getDate(); }

    /**
     * Gets the LocalDateTime for the system
     * @return the system time
     */
    public static LocalTime getSystemTime() {
        return SystemDateTime.getInstance().getTime();
    }

    /**
     * Adds hours to the system time.
     * @param hours: the number of hours to add
     */
    public static void addHoursToSystemDateTime(long hours) {
        SystemDateTime.getInstance().plusHours(hours);
    }

    /**
     * Adds days to the system time.
     * @param days: the number of days to be added to the system
     */
    public static void addDaysToSystemDateTime(long days) {
        SystemDateTime.getInstance().plusDays(days);
    }

    /**
     * Adds a current visit to the LBMS.
     * @param visitor: the visitor at the library
     * @return the new visit object
     */
    public static Visit beginVisit(Visitor visitor) {
        Visit visit = new Visit(visitor);
        LBMS.getCurrentVisits().put(visitor.getVisitorID(), visit);
        return visit;
    }

    /**
     * Ends and removes the visit from LBMS
     * @return the visit object removed
     */
    public static Visit endVisit(Visitor visitor) {
        Visit visit = LBMS.getCurrentVisits().remove(visitor.getVisitorID());
        visit.depart();
        LBMS.getTotalVisits().add(visit);
        return visit;
    }

    /**
     * Buys *quantity* of each book listed in *ids*
     * @param quantity: the quantity of books to be purchased
     * @param ids: the ids of the books to be purchased
     * @return a response string
     */
    public static String processPurchaseOrder(int quantity, List<Long> ids) {
        String booksBought = quantity + "";
        Search s;
        for(Long id: ids) {
            s = new SearchByISBN(id);
            Book b = findBooks(s).get(0);
            for(int i = quantity; i > 0; i--) {
                buyBook(b);
            }
            booksBought += ("," + b.toString() + "," + Integer.toString(quantity)) + '\n';
        }
        return (quantity * ids.size()) + booksBought;
    }

    /**
     * Finds the fines due for a visitor.
     * @param visitorID: the id of the visitor
     * @return the ammount of fines due
     */
    public static double visitorFines(long visitorID) {
        return getVisitorByID(visitorID).getFines();
    }

    /**
     * Checks out a book for a visitor.
     * @param isbn: the isbn of the book to checkout
     * @param visitorID: the ID of the visitor checking out the book
     * @return a string of the response message
     */
    public static String checkOutBook(long isbn, long visitorID) {
        Transaction t = new Transaction(isbn, visitorID);
        Visitor v = getVisitorByID(visitorID);
        Search s = new SearchByISBN(isbn);
        List<Book> l = findBooks(s);
        Book b;
        if(l.size() == 0) {
            return "id-error," + isbn;
        }
        else {
            b = l.get(0);
        }
        if(v.canCheckOut()) {
            v.checkOut(t);
            b.checkOut();
            ArrayList<Transaction> transactions = LBMS.getTransactions();
            transactions.add(t);
            return t.getDueDate().format(SystemDateTime.DATE_FORMAT);
        }
        return "unknown-error";
    }
}
