package lbms.views;

import lbms.controllers.ViewController;

/**
 * BooksViewState class for viewing books in the system.
 * @author Team B
 */
public class BooksViewState implements State {

    private boolean SYSTEM_STATUS;

    /**
     * Constructor for a BooksViewState object.
     * @param SYSTEM_STATUS: the status of the system
     */
    public BooksViewState(boolean SYSTEM_STATUS) {
        this.SYSTEM_STATUS = SYSTEM_STATUS;
    }

    /**
     * Initialized the BooksViewState.
     */
    @Override
    public void init() {
        System.out.println("\nPlease select a command:");
        System.out.println("search)      Search for a book");
        System.out.println("browse)      Show all available books");

        if (SYSTEM_STATUS) {
            System.out.println("checkout)    Borrow a book");
        }

        System.out.println("checkin)     Return a book");
        System.out.println("return)      Return to main menu");
    }

    @Override
    public void onEnter() {
        // TODO
    }

    /**
     * Changes the state.
     * @param state: the command to handle
     */
    @Override
    public void change(String state) {
         switch(state) {
             case "search":
                 break;
             case "browse":
                 break;
             case "checkin":
                 break;
             case "return":
                 ViewController.setState(new DefaultViewState(SYSTEM_STATUS));
                 break;
             case "checkout":
                 if (SYSTEM_STATUS) {
                     break;
                 }
             default:
                 System.out.println("Command not found\n");
                 this.init();
         }
    }
}
