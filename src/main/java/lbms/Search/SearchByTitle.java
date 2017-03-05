package lbms.Search;

import lbms.Book;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Search class that finds books by the title.
 * @author Team B
 */
public class SearchByTitle implements Search {

    private String title;

    /**
     * Constructor for SearchByTitle
     * @param title: the title of the book
     */
    public SearchByTitle(String title) {
        this.title = title;
    }

    /**
     * Finds all the books that match the given title.
     * @param books : the list of books
     * @return a list of books with the given title
     */
    public List<Book> search(HashMap<Long, Book> books) {
        return books.values().parallelStream()
                .filter(book -> book.getTitle().contains(title))
                .collect(Collectors.toList());
    }
}
