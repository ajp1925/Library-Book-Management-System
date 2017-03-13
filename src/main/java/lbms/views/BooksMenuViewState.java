package lbms.views;

import lbms.controllers.ViewController;
import lbms.models.SystemDateTime;

/**
 * BooksMenuViewState class for viewing books in the system.
 * @author Team B
 */
public class BooksMenuViewState implements State {

    private boolean SYSTEM_STATUS;

    /**
     * Constructor for a BooksMenuViewState object.
     * @param SYSTEM_STATUS: the status of the system
     */
    BooksMenuViewState(boolean SYSTEM_STATUS) {
        this.SYSTEM_STATUS = SYSTEM_STATUS;
    }

    /**
     * Initialized the BooksMenuViewState.
     */
    @Override
    public void init() {
        System.out.println("\nPlease select a command:");
        System.out.println("search)      Search for a book");
        System.out.println("browse)      Show all available books");

        if(SYSTEM_STATUS) {
            System.out.println("checkout)    Borrow a book");
        }

        System.out.println("checkin)     Return a book");
        System.out.println("return)      Return to main menu");
    }

    /**
     * No operation from this method.
     */
    @Override
    public void onEnter() { }

    /**
     * Changes the state.
     * @param state: the command to handle
     */
    @Override
    public void change(String state) {
         switch(state) {
             case "search":
                 ViewController.setState(new BookSearchMenuViewState(SYSTEM_STATUS));
                 break;
             case "browse":
                 // TODO
                 break;
             case "checkin":
                 ViewController.setState(new ReturnBookViewState(SYSTEM_STATUS));
                 break;
             case "return":
                 ViewController.setState(new DefaultViewState(SYSTEM_STATUS));
                 break;
             case "checkout":
                 if (SYSTEM_STATUS) {
                     ViewController.setState(new BorrowBookViewState(SYSTEM_STATUS));
                     break;
                 }
             default:
                 System.out.println("Command not found\n");
                 this.init();
         }
    }
}
