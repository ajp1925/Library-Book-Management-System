package lbms.views.CLI.viewstate;

import lbms.controllers.CommandController;
import lbms.views.CLI.CLIView;

import java.util.Scanner;

/**
 * Report View for fiew package
 */
public class ReportViewState implements State {

    private Integer days = null;


    /**
     * Prompts a user whether to views books or users, or exit
     */
    @Override
    public void init() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nHow many days would you like a report for? Type \"all\" to get all statistics.");
        String input = scanner.nextLine();

        try {
            days = Integer.parseInt(input);
        }
        catch(Exception e){
            days = null;
        }
    }

    /**
     * Method handles the state after initialization.
     */
    @Override
    public void onEnter() {
        String response;
        if(days == null) {
            response = CommandController.processRequest("report;");
            System.out.println("\nSystem report:");
        }
        else {
            response = CommandController.processRequest("report," + days + ";");
            System.out.println("\nSystem report for " + days + " days:");
        }

        try {
            System.out.println(parseResponse(response));
        }
        catch(Exception e) {
            System.out.println(response);
        }

        CLIView.setState(new SystemViewState());
    }

    /**
     * No operation for this method.
     * @param state: the command to handle
     */
    public void change(String state) {}

    /**
     * Parses the response for standard output.
     * @param response: the response string from execute
     * @return the output to be printed
     */
    public String parseResponse(String response) {
        String[] fields = response.split(",");
        return fields[fields.length - 1];
    }
}
