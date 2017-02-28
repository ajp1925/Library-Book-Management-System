package lbms.state;

public class StateManager {

    public static State STATE_DEFAULT = new StateDefault();

    private static State currentState;

    public static void setState(State state) {
        currentState = state;
        state.display();
    }

    public static State getState() {
        return currentState;
    }
}
