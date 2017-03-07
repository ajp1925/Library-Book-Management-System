package lbms.views;

import lbms.controllers.ViewController;

/**
 * This is the default views which is entered when the system starts.
 */
public class DefaultViewState implements State {

    /**
     * Prompts a user whether to views books or users, or exit
     */
    @Override
    public void init() {
        System.out.println("Welcome to the Library Book Management System!");
        System.out.println("\nPlease select a command: ");
        System.out.println("books)     View books");
        System.out.println("users)     View users");
        System.out.println("system)    Edit system");
        System.out.println("exit)      Exit system");
    }

    /**
     * NO-OP
     */
    @Override
    public void onEnter() {
        // NO-OP
    }

    /**
     * {@inheritDoc}
     */
    public void change(String state) {
        switch (state) {
            case "books": ViewController.setState(new BooksViewState()); break;
            case "users": ViewController.setState(new UserMenuViewState()); break;
            case "system": ViewController.setState(new SystemViewState()); break;
            default:
                System.out.println("Command not found\n\n");
                this.init();
        }
    }
}
