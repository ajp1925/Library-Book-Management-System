package lbms.views.CLI;

import lbms.views.View;
import lbms.views.CLI.viewstate.DefaultViewState;
import lbms.views.CLI.viewstate.State;

import java.util.Scanner;

/**
 * Created by Chris on 3/28/17.
 */
public class CLIView implements View {
    private static CLIView instance = null;
    private static State viewState;

    private CLIView() {}

    public static CLIView getInstance() {
        if(instance == null) {
            instance = new CLIView();
        }
        return instance;
    }

    @Override
    public void run() {
        Scanner s = new Scanner(System.in);
        String input = "";

        CLIView.setState(new DefaultViewState());

        while(true) {
            System.out.print("> ");
            input = s.nextLine();
            if (input.matches("(?i)exit|quit")) { break; }
            CLIView.change(input);
        }
    }

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
