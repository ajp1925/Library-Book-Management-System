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
    public void init() {
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
     * Get information from the user to register a new user
     */
    public void onEnter() {
        System.out.println("Is all this information correct? (y/n)");
        System.out.printf("First Name: %s\n", firstName);
        System.out.printf("Last Name: %s\n", lastName);
        System.out.printf("Address: %s\n", address);

    }

    /**
     * {@inheritDoc}
     */
    public void change(String state) {
        switch (state) {
            case "y":
                API.registerVisitor(new Visitor(firstName, lastName, address, 0));
                ViewController.setState(new DefaultViewState());
                break;
            case "n":
                onEnter();
                break;
        }
    }
}
