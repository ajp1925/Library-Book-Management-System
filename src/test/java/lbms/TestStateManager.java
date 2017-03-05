package lbms;

import lbms.views.DefaultViewState;
import lbms.views.State;
import lbms.controllers.ViewController;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestStateManager {

    @Before
    public void setDefaultState() {
        ViewController.setState(new DefaultViewState());
    }

    @Test
    public void defaultStateExists() {
        assertNotNull(ViewController.getState());
    }

    @Test
    public void stateSwitches() {
        State currentState = ViewController.getState();
        ViewController.setState(new DummyState());
        assertNotEquals(currentState, ViewController.getState());
    }

    public class DummyState implements State {
        public void init() {}
        public void onEnter() {}
        public void change(String state) {}
    }
}
