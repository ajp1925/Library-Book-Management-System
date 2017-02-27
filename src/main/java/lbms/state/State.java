package lbms.state;

public abstract class State {

    public static State STATE_DEFAULT = new StateDefault();

    public abstract void handleCommand(String command);

}
