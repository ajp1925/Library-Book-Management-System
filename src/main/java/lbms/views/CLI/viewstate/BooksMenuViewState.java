package lbms.views.CLI.viewstate;

import lbms.controllers.CommandController;
import lbms.views.CLI.CLIView;

/**
 * BooksMenuViewState class for viewing books in the system.
 * @author Team B
 */
public class BooksMenuViewState implements State {
    /**
     * Constructor for a BooksMenuViewState object.
     */
    BooksMenuViewState() { }

    /**
     * Initialized the BooksMenuViewState.
     */
    @Override
    public void init() {
        System.out.println("\nPlease select a command:");
        System.out.println("search)      Search for a book");

        if(CommandController.isOpen()) {
            System.out.println("checkout)    Borrow a book");
        }

        System.out.println("checkin)     Return a book");
        System.out.println("list)        Show all available books");
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
                 CLIView.setState(new BookSearchMenuViewState());
                 break;
             case "list":
                 CLIView.setState(new BooksListViewState());
                 break;
             case "checkin":
                 CLIView.setState(new ReturnBookViewState());
                 break;
             case "return":
                 CLIView.setState(new DefaultViewState());
                 break;
             case "checkout":
             case "borrow":
                 if(CommandController.isOpen()) {
                     CLIView.setState(new BorrowBookViewState());
                     break;
                 }
             default:
                 System.out.println("Command not found\n");
                 this.init();
         }
    }
}
