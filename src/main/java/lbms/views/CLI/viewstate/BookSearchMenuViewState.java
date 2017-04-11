package lbms.views.CLI.viewstate;

import lbms.views.CLI.CLIView;

/**
 * Book Search Menu view for views package.
 * @author Team B
 */
public class BookSearchMenuViewState implements State {

    /**
     * Initializes the state.
     */
    @Override
    public void init() {
        System.out.println("\nPlease select a command:");
        System.out.println("library)    Search for a book in the library");
        System.out.println("store)      Search for a book in the store");
        System.out.println("return)     Return to main menu");
    }

    /**
     * No operation from this method.
     */
    @Override
    public void onEnter() {}

    /**
     * Method to change states.
     * @param state: the command to handle
     */
    public void change(String state) {
        switch (state) {
            case "library":
                CLIView.setState(new LibrarySearchViewState());
                break;
            case "store":
                CLIView.setState(new StoreSearchViewState());
                break;
            case "return":
                CLIView.setState(new BooksMenuViewState());
                break;
            default:
                System.out.println("Command not found\n");
                this.init();
        }
    }
}
