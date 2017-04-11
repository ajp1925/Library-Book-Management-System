package lbms.views.CLI.viewstate;

import lbms.controllers.commandproxy.ProxyCommandController;
import lbms.views.CLI.CLIView;

import java.util.Scanner;

/**
 * Advance view state for the views package.
 * @author Team B
 */
public class AdvanceViewState implements State {

    private int days;
    private int hours;

    /**
     * Initializes AdvanceViewState.
     */
    @Override
    public void init() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nHow many days would you like to advance the clock?");
        this.days = scanner.nextInt();
        System.out.println("How many hours would you like to advance the clock?");
        this.hours = scanner.nextInt();
    }

    /**
     * Processes the command for advancing the time.
     */
    @Override
    public void onEnter() {
        String response = new ProxyCommandController().processRequest("advance," + this.days + "," +
                this.hours + ";");

        try {
            System.out.println(parseResponse(response));
        } catch (Exception e) {
            System.out.println(response);
        }

        CLIView.setState(new ClockViewState());
    }

    /**
     * No operation for this method.
     * @param state: the command to handle
     */
    @Override
    public void change(String state) {}

    /**
     * Parses the response for advance time.
     * @param response: the response string from execute
     * @return output for advance time
     */
    public String parseResponse(String response) {
        String[] fields = response.split(",");
        switch (fields[1]) {
            case "success;":
                return "\nAdvance success, clock has been moved forward " + this.days + " day(s) and " + this.hours
                        + " hour(s).";
            case "invalid-number-of-days":
                return "\nFailure, " + this.days + " is an invalid number of days to skip.";
            default:
                return "\nFailure, " + this.hours + " is an invalid number of hours to skip.";
        }
    }
}
