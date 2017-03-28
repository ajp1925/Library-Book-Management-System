package lbms.views.viewstate;

import lbms.controllers.CommandController;
import lbms.views.CLIView;

import java.util.Scanner;

/**
 * PurchaseBookViewState class for views package.
 * @author Team B
 */
public class PurchaseBookViewState implements State {

    private boolean SYSTEM_STATUS;
    private int quantity;
    private String ids = "";

    /**
     * Constructor for an PurchaseBookViewState.
     * @param SYSTEM_STATUS: the current status of the system
     */
    PurchaseBookViewState(boolean SYSTEM_STATUS) {
        this.SYSTEM_STATUS = SYSTEM_STATUS;
    }

    /**
     * Initializes the PurchaseBook State
     */
    @Override
    public void init() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nWhat quantity of these books would you like to purchase?");
        quantity = scanner.nextInt();
        String response;
        do {
            System.out.println("\nPlease enter the ID of the book to purchase.");
            ids += "," + scanner.next();
            System.out.println("\nAre you buying another book?");
            response = scanner.next();
        } while(response.toLowerCase().equals("yes") || response.toLowerCase().equals("y"));
    }

    /**
     * Processes the command for purchasing a book.
     */
    @Override
    public void onEnter() {
        String response = CommandController.processRequest(this.SYSTEM_STATUS,"buy," + quantity + ids + ";");

        try {
            System.out.println("\n" + CommandController.getCommand().parseResponse(response));
        }
        catch(Exception e) {
            System.out.println(response);
        }

        CLIView.setState(new BooksMenuViewState(SYSTEM_STATUS));
    }

    /**
     * No operation for this method.
     * @param state: the command to handle
     */
    @Override
    public void change(String state) {}
}
