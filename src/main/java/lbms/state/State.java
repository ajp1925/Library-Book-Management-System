package lbms.state;

/**
 * Abstract representation of a state.
 *
 * Note: This is not an interface, as to ensure correct access modifiers.
 */
public abstract class State {

    /**
     * Updates the view. Should only be called internally.
     */
    protected abstract void display();

    /**
     * Handle a command passed to the state
     * @param command The command to handle
     */
    public abstract void handleCommand(String command);
}
