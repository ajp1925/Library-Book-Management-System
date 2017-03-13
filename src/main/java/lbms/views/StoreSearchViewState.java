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
        System.out.println(CommandController.getCommand().parseResponse(response));
        ViewController.setState(new StoreSearchCompletedViewState(SYSTEM_STATUS));
    }

    @Override
    public void change(String state) { }
}
