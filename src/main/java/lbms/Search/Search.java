package lbms.Search;

import lbms.Book;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Interface to model search classes on.
 * @author Team B
 */
public interface Search {

    /**
     * Finds the books with the given search criteria.
     * @param books: the list of books
     * @return a list of books that match
     */
    ArrayList<Book> search(HashMap<Long, Book> books);

}
