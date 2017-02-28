package lbms.state;

/**
 * This is the default state which is entered when the system starts.
 */
class StateDefault extends State {

    /**
     * NO-OP
     */
    @Override
    protected void onEnter() {
        // NO-OP
    }

    /**
     * Prompts a user whether to view books or users, or exit
     */
    @Override
    protected void display() {
        System.out.println("Welcome to the Library Book Management System!");
        System.out.println("Please select a command: ");
        System.out.println("books)     View books");
        System.out.println("users)     View users");
        System.out.println("exit)      Exit the LBMS");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleCommand(String command) {
        switch (command) {
            case "books": StateManager.setState(StateManager.STATE_BOOKS); break;
            case "users": StateManager.setState(StateManager.STATE_USERS); break;
        }
    }
}
