package lbms.views;

import lbms.controllers.CommandController;
import lbms.controllers.ViewController;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by 15bar on 3/12/2017.
 */
public class PurchaseBookViewState implements State{
    private boolean SYSTEM_STATUS;
    int quantity;
    String ids = "";
    String response;

    /**
     * Constructor for an PurchaseBookViewState.
     * @param SYSTEM_STATUS: the current status of the system
     */
    public PurchaseBookViewState(boolean SYSTEM_STATUS) {
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

        do {
            System.out.println("\nPlease enter the ID of the book to purchase.");
            ids += "," + scanner.next();
            System.out.println("\nAre you buying another book?");
            response = scanner.next();
        } while (response.toLowerCase().equals("yes") || response.toLowerCase().equals("y"));

    }

    /**
     * Processes the command for purchasing a book.
     */
    @Override
    public void onEnter() {
        String response = CommandController.processRequest(this.SYSTEM_STATUS,"buy," + quantity + ids + ";");

        try {
            System.out.println("\n" + CommandController.getCommand().parseResponse(response));
        } catch (Exception e) {
            System.out.println(response);
        }

        ViewController.setState(new BooksMenuViewState(SYSTEM_STATUS));

    }

    @Override
    public void change(String state) { }
}
