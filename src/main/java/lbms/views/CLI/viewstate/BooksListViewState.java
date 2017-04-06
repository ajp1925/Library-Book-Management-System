package lbms.views.CLI.viewstate;

import lbms.LBMS;
import lbms.models.Book;
import lbms.views.CLI.CLIView;

/**
 * BooksListViewState class for views package.
 * @author Team B
 */
public class BooksListViewState implements State {

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

        CLIView.setState(new BooksMenuViewState());
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
