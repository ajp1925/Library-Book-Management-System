package lbms.state;

class StateDefault extends State {

    @Override
    protected void init() {
        // NO-OP
    }

    @Override
    protected void enter() {
        System.out.println("Welcome to the Library Book Management System!");
        System.out.println("Please select a command: ");
        System.out.println("books)  View books");
        System.out.println("users)  View users");
        System.out.println("exit)   Exit the LBMS");
    }

    @Override
    public void handleCommand(String command) {
        switch (command) {
            case "books":
                System.out.println("Not Yet Implemented"); // TODO: View books
                break;
            case "users":
                System.out.println("Not Yet Implemented"); // TODO: View Users
                break;
        }
    }
}
