package lbms.Search;

import lbms.Book;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

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
    public SearchByAuthor(String author) {
        this.author = author;
    }

    /**
     * Method to find the books that match the given author
     * @param books : the list of books
     * @return A list of books that match the author
     */
//    public List<Book> search(HashMap<Long, Book> books) {
//        ArrayList<Book> matches = new ArrayList<>();
//        for(Long key: books.keySet()) {
//            for(String author: books.get(key).getAuthors()) {
//                if(author.equals(this.author)) {
//                    matches.add(books.get(key));
//                    break; // TODO make sure this doesn't stop outer loop
//                }
//            }
//        }
//        return matches;
//    }

    public List<Book> search(HashMap<Long, Book> books) {
        return books.values().parallelStream()
                .filter(book -> book.hasAuthorPartial(author))
                .collect(Collectors.toList());
    }
}
