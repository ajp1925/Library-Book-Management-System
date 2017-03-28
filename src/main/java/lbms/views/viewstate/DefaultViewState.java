package lbms.views.viewstate;

import lbms.views.CLIView;

/**
 * This is the default views which is entered when the system starts.
 * @author Team B
 */
public class DefaultViewState implements State {

    private boolean SYSTEM_STATUS;

    /**
     * Constructor for a DefaultViewState.
     * @param SYSTEM_STATUS: the status of the system
     */
    public DefaultViewState(boolean SYSTEM_STATUS) {
        this.SYSTEM_STATUS = SYSTEM_STATUS;
    }

    /**
     * Prompts a user whether to views books or users, or exit.
     */
    @Override
    public void init() {
        System.out.println("\nWelcome to the Library Book Management System!");

        if(!SYSTEM_STATUS) {
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
        switch(state) {
            case "books":
                CLIView.setState(new BooksMenuViewState(SYSTEM_STATUS));
                break;
            case "users":
                CLIView.setState(new UserMenuViewState(SYSTEM_STATUS));
                break;
            case "system":
                CLIView.setState(new SystemViewState(SYSTEM_STATUS));
                break;
            default:
                System.out.println("Command not found\n\n");
                this.init();
                break;
        }
    }
}
