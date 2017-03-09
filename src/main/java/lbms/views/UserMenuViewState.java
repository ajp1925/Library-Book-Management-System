package lbms.views;

import lbms.controllers.ViewController;

/**
 * UserMenuViewState class.
 * @author Team B
 */
public class UserMenuViewState implements State {

    private boolean SYSTEM_STATUS;

    /**
     * Constructor for a UserMenuViewState object.
     * @param SYSTEM_STATUS
     */
    public UserMenuViewState(boolean SYSTEM_STATUS) {
        this.SYSTEM_STATUS = SYSTEM_STATUS;
    }

    /**
     * Prompts a user to either search or register a user
     */
    @Override
    public void init() {
        System.out.println("\nPlease select a command:");
        System.out.println("search)           Search for a user");
        System.out.println("register)         Register a new user");
        System.out.println("enter library)    Allow a user to enter the library");
        System.out.println("exit library)     Have a user leave the library");
        System.out.println("borrowed)         Find the books a user has borrowed");
        System.out.println("return)           Return to main menu");
    }

    @Override
    public void onEnter() {
        // NO-OP TODO
    }

    /**
     * Changes the state of the system.
     * @param state: the command to handle
     */
    @Override
    public void change(String state) {
        switch (state) {
            case "search":
                break;
            case "register":
                ViewController.setState(new RegisterViewState(SYSTEM_STATUS));
                break;
            case "enter library":
            case "enter":
                ViewController.setState(new BeginVisitViewState(SYSTEM_STATUS));
                break;
            case "exit library":
            case "leave":
                ViewController.setState(new EndVisitViewState(SYSTEM_STATUS));
                break;
            case "borrowed":
                ViewController.setState(new FindBorrowedViewState(SYSTEM_STATUS));
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
