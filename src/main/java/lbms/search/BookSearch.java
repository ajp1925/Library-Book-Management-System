package lbms.search;

import lbms.LBMS;
import lbms.models.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public enum BookSearch {
    BY_AUTHOR,
    BY_ISBN,
    BY_TITLE;

    public List<Book> search(long isbn) {
        return search(Long.toString(isbn));
    }

    public List<Book> search(String s) {
        switch (this) {
            case BY_AUTHOR: return find(book -> book.hasAuthorPartial(s));
            case BY_ISBN:   return find(book -> Long.toString(book.getIsbn()).contains(s));
            case BY_TITLE:  return find(book -> book.getTitle().contains(s));
        }
        return new ArrayList<>();
    }

    private List<Book> find(Predicate<? super Book> condition) {
        return LBMS.getBooks().values().parallelStream().filter(condition).collect(Collectors.toList());
    }
}
