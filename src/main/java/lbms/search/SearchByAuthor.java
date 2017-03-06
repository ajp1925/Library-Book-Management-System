package lbms.search;

import lbms.models.Book;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * search class that finds books with the same author name.
 * @author Team B
 */
public class SearchByAuthor implements Search {

    private String author;

    /**
     * Constructor for SearchByAuthor.
     * @param author: a String of the author
     */
    public SearchByAuthor(String author) {
        this.author = author;
    }

    /**
     * Method to find the books that match the given author.
     * @param books: the list of books
     * @return a list of the matches
     */
    @Override
    public List<Book> search(HashMap<Long, Book> books) {
        return books.values().parallelStream()
                .filter(book -> book.hasAuthorPartial(author))
                .collect(Collectors.toList());
    }
}
