package lbms.Search;

import lbms.Book;
import java.util.ArrayList;

/**
 * Created by Chris on 2/23/17.
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
