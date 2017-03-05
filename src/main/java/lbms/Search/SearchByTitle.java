package lbms.Search;

import lbms.Book;
import java.util.ArrayList;
import java.util.HashMap;

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
     * @param books: the list of books
     * @return a list of books with the given title
     */
    public ArrayList<Book> search(HashMap<Long, Book> books) {
        ArrayList<Book> matches = new ArrayList<>();
        for(Long key: books.keySet()) {
            if(books.get(key).getTitle().equals(this.title)) {
                matches.add(books.get(key));
            }
        }
        return matches;
    }
}
