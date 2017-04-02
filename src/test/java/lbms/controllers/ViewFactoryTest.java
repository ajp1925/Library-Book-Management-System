package lbms.controllers;

import lbms.views.CLIView;
import lbms.views.viewstate.DefaultViewState;
import lbms.views.viewstate.State;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

public class ViewFactoryTest {

    @Before
    public void setDefaultState() {
        CLIView.setState(new DefaultViewState(true));
    }

    @Test
    public void defaultStateExists() {
        assertNotNull(CLIView.getState());
    }

    @Test
    public void stateSwitches() {
        State currentState = CLIView.getState();
        CLIView.setState(new DummyState());
        assertNotEquals(currentState, CLIView.getState());
    }

    public class DummyState implements State {
        @Override public void init() {}
        @Override public void onEnter() {}
        @Override public void change(String state) {}
    }
}
