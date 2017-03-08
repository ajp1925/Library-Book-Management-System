package lbms.views;

import lbms.API;
import lbms.controllers.CommandController;
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
    private int phone;

    /**
     * Prompts the user to verify the entered information
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
        System.out.println("Phone Number:");
        phone = scanner.nextInt();
    }

    /**
     * Get information from the user to register a new user
     */
    @Override
    public void onEnter() {
        String response = CommandController.processRequest("register," +
                firstName + "," + lastName + "," + address + "," + phone + ";");
        System.out.println(CommandController.getCommand().parseResponse(response));
        ViewController.setState(new DefaultViewState());
    }

    /**
     * NO-OP
     */
    @Override
    public void change(String state) {
        //NO-OP
    }
}
