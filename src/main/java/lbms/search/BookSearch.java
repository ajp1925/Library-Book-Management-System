package lbms.search;

import lbms.LBMS;
import lbms.models.Book;

import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Searches the books.
 * @author Team B
 */
public enum BookSearch implements Search<Book> {
    BY_AUTHOR,
    BY_ISBN,
    BY_TITLE,
    BY_PUBLISHER;

    private Collection<Book> toSearch = LBMS.getBooks().values();

    /**
     * Creates a predicate for the search.
     * @param s: the string used for the search
     * @return the predicate that was created
     */
    @Override
    public Predicate<? super Book> createPredicate(String s) {
        switch (this) {
            case BY_AUTHOR:
                return book -> book.hasAuthorPartial(s);
            case BY_ISBN:
                return book -> book.getIsbn().toString().contains(s);
            case BY_TITLE:
                return book -> book.getTitle().toLowerCase().contains(s.toLowerCase());
            case BY_PUBLISHER:
                return book -> book.getPublisher().toLowerCase().contains(s.toLowerCase());
            default:
                return book -> true;
        }
    }

    /**
     * Creates a stream that is filtered by the given condition predicate.
     * @param condition: the predicate for the filter
     * @return a stream of books
     */
    @Override
    public Stream<Book> filterStream(Predicate<? super Book> condition) {
        return toSearch.parallelStream().filter(condition);
    }

    /**
     * Creates an instance of this enum.
     * @return a BookSearch enum object.
     */
    public BookSearch toBuy() {
        toSearch = LBMS.getBooksToBuy();
        return this;
    }

    /**
     * Creates an instance of this enum.
     * @return a BookSearch enum object for searching books already in the libary.
     */
    public BookSearch inLibrary() {
        toSearch = LBMS.getBooks().values();
        return this;
    }
}
