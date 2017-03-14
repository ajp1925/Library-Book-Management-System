package lbms.views;

import lbms.controllers.CommandController;
import lbms.controllers.ViewController;

import java.util.Scanner;

/**
 * Interacts with user to orchestrate a book store search.
 * @author Team B
 */
public class StoreSearchViewState implements State {
    private boolean SYSTEM_STATUS;

    private String commandString = "search";
    private String optionalArgumentPrompt = "(Press enter to search only with what you've input so far)";
    private String[] prompts = {
            "\nPlease enter the title of the book to search for:",
            "\nPlease enter the author(s) of the book to search for (comma separated):",
            "\nPlease enter the isbn of the book to search for:",
            "\nPlease enter the publisher of the book to search for:",
            "\nPlease enter the sort-order for the resulting books:",
    };

    /**
     * Constructor for an StoreSearchViewState.
     * @param SYSTEM_STATUS the current status of the system
     */
    public StoreSearchViewState(boolean SYSTEM_STATUS) {
        this.SYSTEM_STATUS = SYSTEM_STATUS;
    }

    /**
     * Produces the command string based on user input.
     */
    @Override
    public void init() {
        System.out.println("\nYou are now searching the store.");
        System.out.println("(Enter \"*\" to skip any step)");

        Scanner scanner = new Scanner(System.in);
        String input = "";

        for(String prompt : prompts) {
            System.out.println(prompt);
            if(!prompt.equals(prompts[0])) {
                System.out.println(optionalArgumentPrompt);
            }

            input = scanner.nextLine();
            if(input.equals("")) {
                break;
            }
            else if(input.equals("*")) {
                commandString += " *";
            }
            else {
                commandString += "," + input;
            }
        }
    }

    /**
     * Processes the command for searching the bookstore.
     */
    @Override
    public void onEnter() {
        String response = CommandController.processRequest(this.SYSTEM_STATUS, commandString + ";");

        try {
            System.out.println(CommandController.getCommand().parseResponse(response));
        } catch (Exception e) {
            System.out.println(response);
        }

        displayMenu();
    }

    public void displayMenu() {
        System.out.println("\nPlease select a command:");
        System.out.println("purchase)    Buy a book for the library from these search results");
        System.out.println("search)      Search the store again");
        System.out.println("return)      Return to main menu");
    }

    @Override
    public void change(String state) {
        switch(state) {
            case "purchase":
                ViewController.setState(new PurchaseBookViewState(SYSTEM_STATUS));
                break;
            case "search":
                ViewController.setState(new StoreSearchViewState(SYSTEM_STATUS));
                break;
            case "return":
                ViewController.setState(new BookSearchMenuViewState(SYSTEM_STATUS));
                break;
            default:
                System.out.println("Command not found\n");
                this.displayMenu();
        }
    }
}
