package lbms.Search;

import lbms.Book;
import java.util.ArrayList;

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
    public ArrayList<Book> search(ArrayList<Book> books) {
        ArrayList<Book> matches = new ArrayList<>();
        for(Book b: books) {
            if(b.getIsbn() == this.isbn) {
                matches.add(b);
            }
        }
        return matches;
    }
}
