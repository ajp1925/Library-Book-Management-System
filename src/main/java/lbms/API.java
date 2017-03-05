package lbms;

import lbms.search.Search;

import java.util.List;

public class API {

    public static void registerVisitor(Visitor visitor) {
        LBMS.getVisitors().add(visitor);
    }

    public static Visitor getVisitor(int visitorID) {
        return LBMS.getVisitors().parallelStream()
                .filter(visitor -> visitor.getVisitorID() == visitorID)
                .findFirst().orElse(null);
    }

    public static void buyBook(Book book) {
        LBMS.getBooks().put(book.getIsbn(), book);
    }

    public static List<Book> findBooks(Search search) {
        return search.search(LBMS.getBooks());
    }
}
