package lbms.search;

import lbms.LBMS;
import lbms.models.Book;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Searches the books.
 * @author Team B
 */
public enum BookSearch implements Search<Book> {
    BY_AUTHOR,
    BY_ISBN,
    BY_TITLE,
    BY_PUBLISHER;

    /**
     * Searches the books by l.
     * @param l: the isbn of the book
     * @return a list of books that match
     */
    public List<Book> search(long l) {
        return search(Long.toString(l));
    }

    /**
     * Searches the books by author, isbn, or title.
     * @param s: the string to search for
     * @return a list of books that match
     */
    @Override
    public List<Book> search(String s) {
        switch(this) {
            case BY_AUTHOR:
                return find(book -> book.hasAuthorPartial(s));
            case BY_ISBN:
                return find(book -> Long.toString(book.getIsbn()).contains(s));
            case BY_TITLE:
                return find(book -> book.getTitle().contains(s));
            case BY_PUBLISHER:
                return find(book -> book.getPublisher().contains(s));
        }
        return new ArrayList<>();
    }

    /**
     * Finds the books.
     * @param condition: the condition that must be true
     * @return a list of books that match
     */
    private List<Book> find(Predicate<? super Book> condition) {
        return LBMS.getBooks().values().parallelStream().filter(condition).collect(Collectors.toList());
    }

    /**
     * Finds the books from the book store.
     * @param s: the string used for searching
     * @return a list of books that match the search
     */
    public List<Book> searchBookstoBuy(String s) {
        switch(this) {
            case BY_AUTHOR:
                return findBooksToBuy(book -> book.hasAuthorPartial(s));
            case BY_ISBN:
                return findBooksToBuy(book -> Long.toString(book.getIsbn()).contains(s));
            case BY_TITLE:
                return findBooksToBuy(book -> book.getTitle().contains(s));
            case BY_PUBLISHER:
                return findBooksToBuy(book -> book.getPublisher().contains(s));
        }
        return new ArrayList<>();
    }

    /**
     * Finds the books.
     * @param condition: the condition that must be true
     * @return a list of books that match
     */
    private List<Book> findBooksToBuy(Predicate<? super Book> condition) {
        return LBMS.getBooksToBuy().parallelStream().filter(condition).collect(Collectors.toList());
    }
}
