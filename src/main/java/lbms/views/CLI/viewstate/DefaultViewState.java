package lbms.views.CLI.viewstate;

import lbms.controllers.commandproxy.ProxyCommandController;
import lbms.views.CLI.CLIView;

/**
 * This is the default views which is entered when the system starts.
 * @author Team B
 */
public class DefaultViewState implements State {

    /**
     * Prompts a user whether to views books or users, or exit.
     */
    @Override
    public void init() {
        System.out.println("\nWelcome to the Library Book Management System!");

        if (!ProxyCommandController.isOpen()) {
            System.out.println("We are currently closed but here you can still access a few commands.");
        }

        System.out.println("\nPlease select a command: ");
        System.out.println("books)     View books");
        System.out.println("users)     View users");
        System.out.println("system)    Edit system");
        System.out.println("exit)      Exit system");
    }

    /**
     * No operation from this method.
     */
    @Override
    public void onEnter() {}

    /**
     * Changes the state.
     * @param state: the command to handle
     */
    public void change(String state) {
        switch (state) {
            case "books":
                CLIView.setState(new BooksMenuViewState());
                break;
            case "users":
                CLIView.setState(new UserMenuViewState());
                break;
            case "system":
                CLIView.setState(new SystemViewState());
                break;
            default:
                System.out.println("Command not found\n\n");
                init();
                break;
        }
    }
}
