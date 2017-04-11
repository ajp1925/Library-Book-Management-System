package lbms.views.CLI.viewstate;

import lbms.controllers.commandproxy.ProxyCommandController;
import lbms.models.Visitor;
import lbms.search.UserSearch;
import lbms.views.CLI.CLIView;

import java.util.Scanner;

/**
 * This views handles registering a new user.
 * @author Team B
 */
public class RegisterViewState implements State {

    private String firstName;
    private String lastName;
    private String address;
    private long phone;

    /**
     * Prompts the user to verify the entered information.
     */
    @Override
    public void init() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nRegister a new user.");
        System.out.print("First Name: ");
        this.firstName = scanner.nextLine();
        System.out.print("Last Name: ");
        this.lastName = scanner.nextLine();
        System.out.print("Address: ");
        this.address = scanner.nextLine();
        System.out.print("Phone Number: ");
        this.phone = Long.parseLong(scanner.nextLine().replaceAll("[\\D]", ""));
    }

    /**
     * Get information from the user to register a new user
     */
    @Override
    public void onEnter() {
        String response = new ProxyCommandController().processRequest("register," + this.firstName + ","
                + this.lastName + "," + this.address + "," + this.phone + ";");
        try {
            System.out.println(parseResponse(response));
        } catch (Exception e) {
            System.out.println(response);
        }

        CLIView.setState(new UserMenuViewState());
    }

    /**
     * No operation from this method.
     * @param state: the command to handle
     */
    @Override
    public void change(String state) {}

    /**
     * Parses the response for standard output.
     * @param response: the response string from execute
     * @return the output to be printed
     */
    public String parseResponse(String response) {
        String[] fields = response.split(",");
        if (fields[1].equals("duplicate;")) {
            return "This user already exists in the system.";
        } else {
            // TODO fix the search under this line, may work may not????
            Visitor visitor = UserSearch.BY_ID.findFirst(fields[0]);
            return String.format("\nNew visitor created on %s:\n\tName: %s\n\tAddress: %s\n\tPhone: %s\n\tVisitor " +
                            "ID: %d", fields[2].replace(";", ""), visitor.getName(), visitor.getAddress(),
                    visitor.getPhoneNumber(), visitor.getVisitorID());
        }
    }
}
