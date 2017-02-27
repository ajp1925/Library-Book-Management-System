package lbms.state;

/**
 * Abstract representation of a state.
 *
 * Note: This is not an interface, as to ensure correct access modifiers.
 */
public abstract class State {

    /**
     * Called once, when a state is first entered.
     */
    protected abstract void init();

    /**
     * Called every time the state is entered.
     */
    protected abstract void enter();

    /**
     * Handle a command passed to the state
     * @param command The command to handle
     */
    public abstract void handleCommand(String command);
}
