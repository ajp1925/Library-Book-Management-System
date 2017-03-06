package lbms.search;

import lbms.models.Book;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * search class that finds the books with the given ISBN number.
 * @author Team B
 */
public class SearchByISBN implements Search {

    private String isbn;

    /**
     * Constructor for SearchByISBN.
     * @param isbn: the ISBN number
     */
    public SearchByISBN(Long isbn) {
        this.isbn = Long.toString(isbn);
    }

    /**
     * Constructor for SearchByISBN that uses a String.
     * @param isbn: a String of the ISBN
     */
    public SearchByISBN(String isbn) {
        this.isbn = isbn;
    }

    /**
     * Method to search the books and find the matches.
     * @param books: the list of books
     * @return a list of matching books
     */
    @Override
    public List<Book> search(HashMap<Long, Book> books) {
        return books.values().parallelStream()
                .filter(book -> Long.toString(book.getIsbn()).contains(isbn))
                .collect(Collectors.toList());
    }
}
