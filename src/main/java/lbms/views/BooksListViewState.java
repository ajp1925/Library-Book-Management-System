package lbms.views;

import lbms.LBMS;
import lbms.controllers.ViewController;
import lbms.models.Book;

/**
 * Created by Chris on 3/13/17.
 */
public class BooksListViewState implements State {
    private boolean SYSTEM_STATUS;

    public BooksListViewState(boolean SYSTEM_STATUS) {
        this.SYSTEM_STATUS = SYSTEM_STATUS;
    }

    @Override
    public void init() {
        System.out.println();

        if (LBMS.getBooks().isEmpty()) {
            System.out.println("No books are owned by the library.");
        } else {
            for (Book book : LBMS.getBooks().values()) {
                System.out.println(book.toString());
            }
        }

        ViewController.setState(new BooksMenuViewState(SYSTEM_STATUS));
    }

    @Override
    public void onEnter() {

    }

    @Override
    public void change(String state) {

    }
}
