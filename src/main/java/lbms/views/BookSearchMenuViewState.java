package lbms.views;

import lbms.controllers.ViewController;

/**
 * Created by Chris on 3/10/17. TODO
 */
public class BookSearchMenuViewState implements State {
    private boolean SYSTEM_STATUS;

    public BookSearchMenuViewState(boolean SYSTEM_STATUS) {
        this.SYSTEM_STATUS = SYSTEM_STATUS;
    }

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
    public void onEnter() { }

    public void change(String state) {
        switch(state) {
            case "library":
                ViewController.setState(new LibrarySearchViewState(SYSTEM_STATUS));
                break;
            case "store":
                ViewController.setState(new StoreSearchViewState(SYSTEM_STATUS));
                break;
            case "return":
                ViewController.setState(new DefaultViewState(SYSTEM_STATUS));
                break;
            default:
                System.out.println("Command not found\n");
                this.init();
        }
    }
}
