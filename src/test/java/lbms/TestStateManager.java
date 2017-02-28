package lbms;

import lbms.state.State;
import lbms.state.StateManager;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestStateManager {

    @Before
    public void setDefaultState() {
        StateManager.setState(StateManager.STATE_DEFAULT);
    }

    @Test
    public void defaultStateExists() {
        assertNotNull(StateManager.getState());
    }

    @Test
    public void stateSwitches() {
        State currentState = StateManager.getState();
        StateManager.setState(new DummyState());
        assertNotEquals(currentState, StateManager.getState());
    }

    public class DummyState extends State {
        @Override protected void onEnter() {}
        @Override protected void display() {}
        @Override public void handleCommand(String command) {}
    }
}
