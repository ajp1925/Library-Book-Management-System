package lbms.views;

import lbms.controllers.ViewController;

/**
 * This is the default views which is entered when the system starts.
 * @author Team B
 */
public class DefaultViewState implements State {

    boolean SYSTEM_STATUS;

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
        if(SYSTEM_STATUS) {
            System.out.println("\nWelcome to the Library Book Management System!");
            System.out.println("\nPlease select a command: ");
            System.out.println("books)     View books");
            System.out.println("users)     View users");
            System.out.println("system)    Edit system");
            System.out.println("exit)      Exit system");
        }
        else {
            System.out.println("\nWelcome to the Library Book Management System after hours!");
            System.out.println("We are currently closed but here you can access a few system commands.");
            System.out.println("\nPlease select a command: ");
            System.out.println("clock)    View system time");
            System.out.println("stats)    View system statistics");
            System.out.println("exit)     Exit System");
        }
    }

    @Override
    public void onEnter() {
        // NO-OP TODO
    }

    /**
     * Changes the state.
     * @param state: the command to handle
     */
    public void change(String state) {
        if(SYSTEM_STATUS) {
            switch(state) {
                case "books":
                    ViewController.setState(new BooksViewState(true));
                    break;
                case "users":
                    ViewController.setState(new UserMenuViewState(true));
                    break;
                case "system":
                    ViewController.setState(new SystemViewState(true));
                    break;
                default:
                    System.out.println("Command not found\n\n");
                    this.init();
            }
        }
        else {
            switch(state) {
                case "clock":
                    ViewController.setState(new ClockViewState(false));
                    break;
                case "stats":
                    break;
                default:
                    System.out.println("Command not found\n");
                    this.init();
            }
        }
    }
}
