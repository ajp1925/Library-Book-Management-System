package lbms.views.CLI.viewstate;

import lbms.controllers.CommandController;
import lbms.views.CLI.CLIView;

import java.util.Scanner;

/**
 * Interacts with user to orchestrate a library search.
 * @author Team B
 */
public class LibrarySearchViewState implements State{

    private boolean SYSTEM_STATUS;
    private String commandString = "info";
    private String[] prompts = {
            "\nPlease enter the title of the book to search for:",
            "\nPlease enter the author(s) of the book to search for (comma separated):",
            "\nPlease enter the isbn of the book to search for:",
            "\nPlease enter the publisher of the book to search for:",
            "\nPlease enter the sort-order for the resulting books:",
    };

    /**
     * Constructor for an LibrarySearchViewState.
     * @param SYSTEM_STATUS the current status of the system
     */
    LibrarySearchViewState(boolean SYSTEM_STATUS) {
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
        String input;
        String optionalArgumentPrompt = "(Press enter to search only with what you've input so far)";
        for(String prompt : prompts) {
            System.out.println(prompt);
            if(!prompt.equals(prompts[0]) && !prompt.equals(prompts[1])) {
                System.out.println(optionalArgumentPrompt);
            }

            input = scanner.nextLine();
            if(input.equals("")) {
                break;
            }
            else {
                if(prompt.equals(prompts[1])) {
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

        try {
            System.out.println(parseResponse(response));
        }
        catch(Exception e){
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

    /**
     * Parses the response for standard output.
     * @param response: the response string from execute
     * @return the output to be printed
     */
    public String parseResponse(String response) {
        String[] fields = response.replace(";", "").split("\n", 2);

        if (fields.length == 1) {
            if (fields[0].endsWith("0")) {
                return "No books match query.";
            } else {
                return response;
            }
        }
        else {
            return fields[1];
        }
    }
}