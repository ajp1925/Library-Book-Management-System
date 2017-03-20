package lbms.views;

import lbms.LBMS;
import lbms.controllers.ViewController;
import lbms.models.Book;

/**
 * BooksListViewState class for views package.
 * @author Team B
 */
public class BooksListViewState implements State {

    private boolean SYSTEM_STATUS;

    /**
     * Constructor for BooksListViewState.
     * @param SYSTEM_STATUS: the status of the system
     */
    BooksListViewState(boolean SYSTEM_STATUS) {
        this.SYSTEM_STATUS = SYSTEM_STATUS;
    }

    /**
     * Initializes the state.
     */
    @Override
    public void init() {
        System.out.println();

        if(LBMS.getBooks().isEmpty()) {
            System.out.println("No books are owned by the library.");
        }
        else {
            for(Book book : LBMS.getBooks().values()) {
                System.out.println(book.toString());
            }
        }

        ViewController.setState(new BooksMenuViewState(SYSTEM_STATUS));
    }

    /**
     * No operation for this method.
     */
    @Override
    public void onEnter() {}

    /**
     * No operation for this method.
     * @param state: the command to handle
     */
    @Override
    public void change(String state) {}
}
