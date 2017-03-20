package lbms.views;

import lbms.controllers.CommandController;
import lbms.controllers.ViewController;

import java.util.Scanner;

/**
 * This views handles registering a new user.
 * @author Team B
 */
public class RegisterViewState implements State {

    private boolean SYSTEM_STATUS;
    private String firstName;
    private String lastName;
    private String address;
    private long phone;

    /**
     * Constructor for a RegisterViewState object.
     * @param SYSTEM_STATUS: the status of the system
     */
    RegisterViewState(boolean SYSTEM_STATUS) {
        this.SYSTEM_STATUS = SYSTEM_STATUS;
    }

    /**
     * Prompts the user to verify the entered information.
     */
    @Override
    public void init() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nRegister a new user.");
        System.out.print("First Name: ");
        firstName = scanner.nextLine();
        System.out.print("Last Name: ");
        lastName = scanner.nextLine();
        System.out.print("Address: ");
        address = scanner.nextLine();
        System.out.print("Phone Number: ");
        phone = Long.parseLong(scanner.nextLine().replaceAll("[\\D]", ""));
    }

    /**
     * Get information from the user to register a new user
     */
    @Override
    public void onEnter() {
        String response = CommandController.processRequest(this.SYSTEM_STATUS,"register," + firstName + ","
                + lastName + "," + address + "," + phone + ";");
        try {
            System.out.println(CommandController.getCommand().parseResponse(response));
        }
        catch(Exception e) {
            System.out.println(response);
        }

        ViewController.setState(new UserMenuViewState(SYSTEM_STATUS));
    }

    /**
     * No operation from this method.
     * @param state: the command to handle
     */
    @Override
    public void change(String state) {}
}
