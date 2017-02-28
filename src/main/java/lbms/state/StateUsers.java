package lbms.state;

/**
 * Intermediate state which allows choosing whether to search for a user or register a new one.
 */
class StateUsers extends State {

    /**
     * NO-OP
     */
    @Override
    protected void onEnter() {
        // NO-OP
    }

    /**
     * Prompts a user to either search or register a user
     */
    @Override
    protected void display() {
        System.out.println("Please select a command:");
        System.out.println("search)    Search for a user");
        System.out.println("register)  Register a new user");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleCommand(String command) {
        switch (command) {
            case "search": break;
            case "register": StateManager.setState(StateManager.STATE_USERS_REGISTER); break;
        }
    }
}
