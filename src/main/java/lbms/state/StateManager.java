package lbms.state;

public class StateManager {

    public static Class STATE_DEFAULT = StateDefault.class;
    public static Class STATE_USERS = StateUsers.class;
    public static Class STATE_BOOKS = StateBooks.class;
    public static Class STATE_USERS_REGISTER = StateUsersRegister.class;

    private static State currentState;

    /**
     * Creates a new instance of the given state class and enters.
     * @param clazz The state to switch to
     */
    public static void setState(Class<? extends State> clazz) {
        try {
            currentState = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        currentState.onEnter();
        currentState.display();
    }

    /**
     * @return The current state
     */
    public static State getState() {
        return currentState;
    }
}
