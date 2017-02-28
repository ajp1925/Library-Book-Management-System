package lbms.state;

class StateDefault extends State {

    @Override
    public void onEnter() {
        // NO-OP
    }

    @Override
    protected void display() {
        System.out.println("Welcome to the Library Book Management System!");
        System.out.println("Please select a command: ");
        System.out.println("books)     View books");
        System.out.println("users)     View users");
        System.out.println("exit)      Exit the LBMS");
    }

    @Override
    public void handleCommand(String command) {
        switch (command) {
            case "books": StateManager.setState(StateManager.STATE_BOOKS); break;
            case "users": StateManager.setState(StateManager.STATE_USERS); break;
        }
    }
}
