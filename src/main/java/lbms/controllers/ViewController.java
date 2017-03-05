package lbms.controllers;

import lbms.views.*;

public class ViewController {

    private static State viewState;

    public static void setState(State state) {
        viewState = state;
        viewState.init();
        viewState.onEnter();
    }

    /**
     * @return The current views
     */
    public static State getState() {
        return viewState;
    }

    public static void change(String state) {
        viewState.change(state);
    }
}
