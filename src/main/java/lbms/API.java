package lbms;

import lbms.search.Search;

import java.util.List;

/**
 * API class contains methods that the Command package uses.
 */
public class API {

    /**
     * Registers a visitor in the system.
     * @param visitor: the visitor to be registered
     */
    public static void registerVisitor(Visitor visitor) {
        LBMS.getVisitors().add(visitor);
    }

    /**
     * Gets a visitor from the system.
     * @param visitorID: the ID of the visitor
     * @return the visitor with the given ID
     */
    public static Visitor getVisitor(int visitorID) {
        return LBMS.getVisitors().parallelStream()
                .filter(visitor -> visitor.getVisitorID() == visitorID)
                .findFirst().orElse(null);
    }

    /**
     * Purchases a book for the library.
     * @param book: the book to be purchased
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
