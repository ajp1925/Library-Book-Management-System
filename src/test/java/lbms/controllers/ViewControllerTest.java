package lbms.controllers;

import lbms.views.DefaultViewState;
import lbms.views.State;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

public class ViewControllerTest {

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
        @Override public void init() {}
        @Override public void onEnter() {}
        @Override public void change(String state) {}
    }
}
