package lbms.search;

import lbms.Book;

import java.util.HashMap;
import java.util.List;

/**
 * Interface to model search classes on.
 * @author Team B
 */
public interface Search {

    /**
     * Finds the books with the given search criteria.
     * @param books : the list of books
     * @return a list of books that match
     */
    List<Book> search(HashMap<Long, Book> books);

}
