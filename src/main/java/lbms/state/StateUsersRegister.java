package lbms.state;

import java.util.Scanner;

class StateUsersRegister extends State {

    private String firstName;
    private String lastName;
    private String address;

    @Override
    public void onEnter() {
        System.out.println("Registering a new user.");
        Scanner scanner = new Scanner(System.in);
        System.out.print("First Name: ");
        firstName = scanner.nextLine();
        System.out.print("Last Name: ");
        lastName = scanner.nextLine();
        System.out.print("Address: ");
        address = scanner.nextLine();
    }

    @Override
    protected void display() {
        System.out.println("Is all this information correct? (y/n)");
        System.out.printf("First Name: %s\n", firstName);
        System.out.printf("Last Name: %s\n", lastName);
        System.out.printf("Address: %s\n", address);
    }

    @Override
    public void handleCommand(String command) {
        switch (command) {
            case "y":
                // TODO: Save user
                StateManager.setState(StateManager.STATE_USERS);
                break;
            case "n":
                onEnter();
                break;
        }
    }
}
