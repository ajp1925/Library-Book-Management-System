package lbms.state;

class StateUsers extends State {

    @Override
    public void onEnter() {
        // NO-OP
    }

    @Override
    protected void display() {
        System.out.println("Please select a command:");
        System.out.println("search)    Search for a user");
        System.out.println("register)  Register a new user");
    }

    @Override
    public void handleCommand(String command) {
        switch (command) {
            case "search": break;
            case "register": StateManager.setState(StateManager.STATE_USERS_REGISTER); break;
        }
    }
}
