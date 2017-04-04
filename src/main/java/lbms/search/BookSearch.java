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

    @Override
    public Predicate<? super Book> createPredicate(String s) {
        switch (this) {
            case BY_AUTHOR:    return book -> book.hasAuthorPartial(s);
            case BY_ISBN:      return book -> book.getIsbn().toString().contains(s);
            case BY_TITLE:     return book -> book.getTitle().contains(s);
            case BY_PUBLISHER: return book -> book.getPublisher().contains(s);
            default:           return book -> true;
        }
    }

    @Override
    public Stream<Book> filterStream(Predicate<? super Book> condition) {
        return toSearch.parallelStream().filter(condition);
    }

    private Collection<Book> toSearch = LBMS.getBooks().values();

    public BookSearch inLibrary() {
        toSearch = LBMS.getBooks().values();
        return this;
    }

    public BookSearch toBuy() {
        toSearch = LBMS.getBooksToBuy();
        return this;
    }
}
