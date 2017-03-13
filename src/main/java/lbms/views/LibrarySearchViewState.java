package lbms.views;

import lbms.command.LibrarySearch;
import lbms.controllers.CommandController;
import lbms.controllers.ViewController;

import java.util.Scanner;

/**
 * Interacts with user to orchestrate a library search.
 * @author Team B
 */
public class LibrarySearchViewState implements State{
    private boolean SYSTEM_STATUS;

    private String commandString = "info";
    private String optionalArgumentPrompt = "(Press enter to search only with what you've input so far)";
    private String[] prompts = {
            "\nPlease enter the title of the book to search for:",
            "\nPlease enter the author(s) of the book to search for (comma separated):",
            "\nPlease enter the isbn of the book to search for:",
            "\nPlease enter the publisher of the book to search for:",
            "\nPlease enter the sort-order for the resulting books:",
    };

    public LibrarySearchViewState(boolean SYSTEM_STATUS) {
        this.SYSTEM_STATUS = SYSTEM_STATUS;
    }

    /**
     * Produces the command string based on user input.
     */
    @Override
    public void init() {
        System.out.println("\nYou are now searching the library");
        System.out.println("(Enter \"*\" to skip any step)");

        Scanner scanner = new Scanner(System.in);
        String input = "";

        for(String prompt : prompts) {
            System.out.println(prompt);
            if(!prompt.equals(prompts[0]) && !prompt.equals(prompts[1])) {
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
                if(prompt.equals(prompts[1]))
                {
                    commandString += ",{" + input + "}";
                }
                else {
                    commandString += "," + input;
                }

            }
        }
    }

    /**
     * Processes the command for searching the library.
     */
    @Override
    public void onEnter() {
        String response = CommandController.processRequest(this.SYSTEM_STATUS, commandString + ";");
        System.out.println(CommandController.getCommand().parseResponse(response));
        ViewController.setState(new BookSearchMenuViewState(SYSTEM_STATUS));
    }

    @Override
    public void change(String state) {

    }
}
