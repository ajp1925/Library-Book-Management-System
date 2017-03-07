package lbms.views;

import lbms.API;
import lbms.models.Visitor;
import lbms.controllers.ViewController;

import java.util.Scanner;

/**
 * This views handles registering a new user
 */
public class RegisterViewState implements State {

    private String firstName;
    private String lastName;
    private String address;

    /**
     * Prompts the user to verify the entered information
     */
    @Override
    public void init() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nRegistering a new user.");
        System.out.print("First Name: ");
        firstName = scanner.nextLine();
        System.out.print("Last Name: ");
        lastName = scanner.nextLine();
        System.out.print("Address: ");
        address = scanner.nextLine();
    }

    /**
     * Get information from the user to register a new user
     */
    @Override
    public void onEnter() {
        System.out.println("\nIs all this information correct? (y/n)");
        System.out.printf("First Name: %s\n", firstName);
        System.out.printf("Last Name: %s\n", lastName);
        System.out.printf("Address: %s\n", address);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void change(String state) {
        switch (state) {
            case "y":
                API.registerVisitor(new Visitor(firstName, lastName, address, 0));
                ViewController.setState(new DefaultViewState());
                break;
            case "n":
                onEnter();
                break;
            default:
                System.out.println("Command not found\n");
                this.onEnter();
        }
    }
}
