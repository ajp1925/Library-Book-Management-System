package lbms.views;

import lbms.controllers.ViewController;

public class UserMenuViewState implements State {

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
        System.out.println("return)           Return to main menu");
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
    @Override
    public void change(String state) {
        switch (state) {
            case "search": break;
            case "register": ViewController.setState(new RegisterViewState()); break;
            case "enter library": case "enter": ViewController.setState(new BeginVisitViewState()); break;
            case "exit library": ViewController.setState(new EndVisitViewState()); break;
            case "return": ViewController.setState(new DefaultViewState()); break;
            default:
                System.out.println("Command not found\n");
                this.init();
        }
    }
}
