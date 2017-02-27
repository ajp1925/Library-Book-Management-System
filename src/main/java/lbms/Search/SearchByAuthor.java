package lbms.Search;

import lbms.Book;
import java.util.ArrayList;

/**
 * Search class that finds books with the same author name.
 * @author Team B
 */
public class SearchByAuthor implements Search {

    private String author;

    /**
     * Constructor for SearchByAuthor.
     * @param author: a String of the author
     */
    public SearchByAuthor(String author) { this.author = author; }

    /**
     * Method to find the books that match the given author
     * @param books: the list of books
     * @return A list of books that match the author
     */
    public ArrayList<Book> search(ArrayList<Book> books) {
        ArrayList<Book> matches = new ArrayList<>();
        for(Book b: books) {
            for(String author: b.getAuthors()) {
                if(author.equals(this.author)) {
                    matches.add(b);
                    break; // TODO make sure this doesn't stop outer loop
                }
            }
        }
        return matches;
    }
}
