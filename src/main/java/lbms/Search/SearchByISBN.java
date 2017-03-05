package lbms.Search;

import lbms.Book;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Search class that finds the books with the given ISBN number.
 * @author Team B
 */
public class SearchByISBN implements Search {

    private int isbn;

    /**
     * Constructor for SearchByISBN.
     * @param isbn: the ISBN number
     */
    public SearchByISBN(int isbn) {
        this.isbn = isbn;
    }

    /**
     * Finds all the books with the given isbn.
     * @param books: the list of books
     * @return a list of books that match the ISBN number
     */
    public ArrayList<Book> search(HashMap<Long, Book> books) {
        ArrayList<Book> matches = new ArrayList<>();
        for(Long key: books.keySet()) {
            if(books.get(key).getIsbn() == this.isbn) {
                matches.add(books.get(key));
            }
        }
        return matches;
    }
}
