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
    String quantity;
    String ids = "";

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
        String input = "";
        do {
            System.out.println("\nPlease enter the ID of the book to purchase.");
            if(!input.equals("")) {
                System.out.println("Press enter to finish.");
            }

            input = scanner.nextLine();
            if(!input.equals("")) {
                ids += "," + input;
            }

        } while (!input.equals(""));

        System.out.println("What quantity of these books would you like to purchase?");
        quantity = scanner.nextLine();

    }

    /**
     * Processes the command for purchasing a book.
     */
    @Override
    public void onEnter() {
        System.out.println("buy," + quantity + ids + ";");
        String response = CommandController.processRequest(this.SYSTEM_STATUS,"buy," + quantity + ids + ";");
        System.out.println(CommandController.getCommand().parseResponse(response));
        ViewController.setState(new DefaultViewState(SYSTEM_STATUS));

    }

    @Override
    public void change(String state) { }
}
