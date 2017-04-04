package lbms.views.CLI.viewstate;

import lbms.views.CLI.CLIView;

/**
 * UserMenuViewState class.
 * @author Team B
 */
public class UserMenuViewState implements State {

    private boolean SYSTEM_STATUS;

    /**
     * Constructor for a UserMenuViewState object.
     * @param SYSTEM_STATUS: boolean status of the system
     */
    UserMenuViewState(boolean SYSTEM_STATUS) {
        this.SYSTEM_STATUS = SYSTEM_STATUS;
    }

    /**
     * Prompts a user to either search or register a user
     */
    @Override
    public void init() {
        System.out.println("\nPlease select a command:");

        if(SYSTEM_STATUS) {
            System.out.println("enter library)    Allow a user to enter the library");
            System.out.println("exit library)     Have a user leave the library");
        }

        System.out.println("register)         Register a new user");
        System.out.println("list)             List all the users in the system");
        System.out.println("borrowed)         Find the books a user has borrowed");
        System.out.println("return)           Return to main menu");
    }

    /**
     * No operation from this method.
     */
    @Override
    public void onEnter() { }

    /**
     * Changes the state of the system.
     * @param state: the command to handle
     */
    @Override
    public void change(String state) {
        switch (state) {
            case "register":
                CLIView.setState(new RegisterViewState(SYSTEM_STATUS));
                break;
            case "list":
                CLIView.setState(new UserListViewState(SYSTEM_STATUS));
                break;
            case "borrowed":
                CLIView.setState(new FindBorrowedViewState(SYSTEM_STATUS));
                break;
            case "return":
                CLIView.setState(new DefaultViewState(SYSTEM_STATUS));
                break;
            case "enter library":
            case "enter":
                if(SYSTEM_STATUS) {
                    CLIView.setState(new BeginVisitViewState(true));
                    break;
                }
            case "exit library":
            case "leave":
                if(SYSTEM_STATUS) {
                    CLIView.setState(new EndVisitViewState(true));
                    break;
                }
            default:
                System.out.println("Command not found\n");
                this.init();
                break;
        }
    }
}
