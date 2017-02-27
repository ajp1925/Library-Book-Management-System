package lbms.state;

import java.util.HashMap;

public class StateManager {

    public static State STATE_DEFAULT = new StateDefault();

    private static State currentState;

    private static HashMap<State, Boolean> initializationMap = new HashMap<>();

    public static void setState(State state) {
        if (initializationMap.get(state) == null || !initializationMap.get(state)) {
            state.init();
            initializationMap.put(state, true);
        }
        currentState = state;
        currentState.enter();
    }

    public static State getState() {
        return currentState;
    }
}
