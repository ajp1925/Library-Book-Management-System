package lbms;

import lbms.models.Book;
import lbms.LBMS;
import lbms.models.SystemDateTime;
import lbms.models.Visitor;
import lbms.search.Search;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Facade class for interacting with the system.
 */
public class API {

    /**
     * Registers a visitor with the system, if they are not already registered
     * @param visitor The visitor to register
     */
    public static void registerVisitor(Visitor visitor) {
        if (!visitorIsRegistered(visitor)) LBMS.getVisitors().add(visitor);
    }

    /**
     * Check whether a visitor is registered with the system.
     * @param visitor The visitor to check
     * @return True if a visitor is registered
     */
    public static boolean visitorIsRegistered(Visitor visitor) {
        return getVisitor(visitor.getVisitorID()) != null;
    }

    /**
     * Gets a visitor from their unique ID
     * @param visitorID the visitor ID
     * @return The Visitor if it exists, or null
     */
    public static Visitor getVisitor(int visitorID) {
        return LBMS.getVisitors().parallelStream()
                .filter(visitor -> visitor.getVisitorID() == visitorID)
                .findFirst().orElse(null);
    }

    /**
     * Buys a book for the library
     * @param book The book to buy
     */
    public static void buyBook(Book book) {
        LBMS.getBooks().put(book.getIsbn(), book);
    }

    /**
     * Finds the books based on the given search method.
     * @param search the searched method to be used
     * @return a list of books that the search provided
     */
    public static List<Book> findBooks(Search search) {
        return search.search(LBMS.getBooks());
    }

    /**
     * Gets the LocalDateTime for the system
     * @return the system time
     */
    public static LocalDateTime getSystemDateTime() {
        return SystemDateTime.getInstance().getDateTime();
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
}
