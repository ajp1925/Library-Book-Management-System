package lbms.state;

public class StateManager {

    public static State STATE_DEFAULT = new StateDefault();
    public static State STATE_USERS = new StateUsers();
    public static State STATE_BOOKS = new StateBooks();
    public static State STATE_USERS_REGISTER = new StateUsersRegister();

    private static State currentState;

    public static void setState(State state) {
        currentState = state;
        state.onEnter();
        state.display();
    }

    public static State getState() {
        return currentState;
    }
}
