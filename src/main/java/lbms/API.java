package lbms;

import lbms.models.Book;
import lbms.models.SystemDateTime;
import lbms.models.Visit;
import lbms.models.Visitor;
import lbms.search.Search;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * Facade class for interacting with the system.
 * @author Team B
 */
public class API {

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
    public static boolean visitorIsRegisteredName(String name) {
        return getVisitorByName(name) != null;
    }

    /**
     * Checks whether a visitor is registered with the system.
     * @param address: the address of the visitor
     * @return True if a visitor is registered
     */
    public static boolean visitorIsRegisteredAddress(String address) {
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
    public static Visitor getVisitorByName(String name) {
        return LBMS.getVisitors().parallelStream()
                .filter(visitor -> visitor.getName().equals(name))
                .findFirst().orElse(null);
    }

    /**
     * Gets a visitor by their address.
     * @param address: the address of a visitor
     * @return the visitor object of the visitor with the given address
     */
    public static Visitor getVisitorByAddress(String address) {
        return LBMS.getVisitors().parallelStream()
                .filter(visitor -> visitor.getAddress().equals(address))
                .findFirst().orElse(null);
    }

    /**
     * Buys a book for the library
     * @param book: The book to buy
     */
    public static void buyBook(Book book) {
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
    public static LocalDate getSystemDate() {
        return SystemDateTime.getInstance().getDate();
    }

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
    public static void addHoursToSystemTime(long hours) {
        SystemDateTime.getInstance().plusHours(hours);
    }

    /**
     * Adds days to the system time.
     * @param days: the number of days to be added to the system
     */
    public static void addDaysToSystemTime(long days) {
        SystemDateTime.getInstance().plusDays(days);
    }

    /**
     * Adds a current visit to the LBMS.
     * @param visitorID: the id of the visitor at the library
     * @return the visit object
     */
    public static Visit beginVisit(int visitorID) {
        Visit v = new Visit(visitorID);
        LBMS.addVisit(v);
        return v;
    }

    /**
     * Buys *quantity* of each book listed in *ids*
     * @param quantity
     * @param ids
     * @return
     */
    public static String processPurchaseOrder(int quantity, List<Integer> ids) {
        String booksBought = "";
        for( Integer id : ids ) {
            for( Book b : LBMS.getBooksToBuy() ) {
                if( b.getTempID().equals(id)) {
                    for( int i = quantity; i > 0; i-- ) { buyBook(b); }
                    booksBought += ("," + b.toString() + Integer.toString(quantity)); //TODO ensure book.toString is of proper format
                    break;
                }
            }
        }
        return booksBought;
    }
}
