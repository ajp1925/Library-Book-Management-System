package lbms.controllers;

import lbms.views.*;

/**
 * Controller for the views package.
 * @author Team B
 */
public class ViewController {

    private static State viewState;

    /**
     * Sets the state of the system.
     * @param state: the state to be set
     */
    public static void setState(State state) {
        viewState = state;
        viewState.flush();
        viewState.init();
        viewState.onEnter();
    }

    /**
     * Getter for the viewState variable.
     * @return the viewState
     */
    public static State getState() {
        return viewState;
    }

    /**
     * Changes the current state.
     * @param state: the state to be changed
     */
    public static void change(String state) {
        viewState.change(state);
    }
}
