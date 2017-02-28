package lbms.state;

import java.util.Scanner;

/**
 * This state handles registering a new user
 */
class StateUsersRegister extends State {

    private String firstName;
    private String lastName;
    private String address;

    /**
     * Get information from the user to register a new user
     */
    @Override
    protected void onEnter() {
        System.out.println("Registering a new user.");
        Scanner scanner = new Scanner(System.in);
        System.out.print("First Name: ");
        firstName = scanner.nextLine();
        System.out.print("Last Name: ");
        lastName = scanner.nextLine();
        System.out.print("Address: ");
        address = scanner.nextLine();
    }

    /**
     * Prompts the user to verify the entered information
     */
    @Override
    protected void display() {
        System.out.println("Is all this information correct? (y/n)");
        System.out.printf("First Name: %s\n", firstName);
        System.out.printf("Last Name: %s\n", lastName);
        System.out.printf("Address: %s\n", address);
    }

    /**
     * {@inheritDoc}
     */
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
