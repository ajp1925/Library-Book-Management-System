package lbms.views.CLI.viewstate;

import lbms.controllers.commandproxy.ProxyCommandController;
import lbms.views.CLI.CLIView;

/**
 * UserMenuViewState class.
 * @author Team B
 */
public class UserMenuViewState implements State {

    /**
     * Prompts a user to either search or register a user
     */
    @Override
    public void init() {
        System.out.println("\nPlease select a command:");

        if(ProxyCommandController.isOpen()) {
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
                CLIView.setState(new RegisterViewState());
                break;
            case "list":
                CLIView.setState(new UserListViewState());
                break;
            case "borrowed":
                CLIView.setState(new FindBorrowedViewState());
                break;
            case "return":
                CLIView.setState(new DefaultViewState());
                break;
            case "enter library":
            case "enter":
                if(ProxyCommandController.isOpen()) {
                    CLIView.setState(new BeginVisitViewState());
                    break;
                }
            case "exit library":
            case "leave":
                if(ProxyCommandController.isOpen()) {
                    CLIView.setState(new EndVisitViewState());
                    break;
                }
            default:
                System.out.println("Command not found\n");
                this.init();
                break;
        }
    }
}
