package lbms.Search;

import lbms.Book;
import java.util.ArrayList;

/**
 * Search class that finds the books with the given ISBN number.
 * @author Team B
 */
public class SearchByISBN implements Search {

    private int isbn;

    public SearchByISBN(int isbn) {
        this.isbn = isbn;
    }

    public ArrayList<Book> search() {
        // TODO
        return null;
    }
}
