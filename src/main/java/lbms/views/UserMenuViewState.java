package lbms.views;

import lbms.controllers.ViewController;

/**
 * Intermediate views which allows choosing whether to search for a user or register a new one.
 */
public class UserMenuViewState implements State {

    /**
     * Prompts a user to either search or register a user
     */
    public void init() {
        System.out.println("Please select a command:");
        System.out.println("search)    search for a user");
        System.out.println("register)  Register a new user");
    }

    /**
     * NO-OP
     */
    public void onEnter() {
        // NO-OP
    }

    /**
     * {@inheritDoc}
     */
    public void change(String state) {
        switch (state) {
            case "search": break;
            case "register": ViewController.setState(new RegisterViewState()); break;
        }
    }
}
