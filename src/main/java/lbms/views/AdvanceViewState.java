package lbms.views;

import lbms.controllers.CommandController;
import lbms.controllers.ViewController;

import java.util.Scanner;

/**
 * Created by Chris on 3/7/17.
 */
public class AdvanceViewState implements State {
    private int days;
    private int hours;
    @Override
    public void init() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nHow many days would you like to advance the clock?");
        days = scanner.nextInt();
        System.out.println("How many hours would you like to advance the clock?");
        hours = scanner.nextInt();
    }

    @Override
    public void onEnter() {
        String[] response = CommandController.processRequest("advance," + days + "," + hours + ";").split(",");
        if (response[1].equals("success;")) {
            System.out.println("\nAdvance success, clock has been moved forward " + days + " day(s) and " + hours + " hour(s).");
        } else if (response[1].equals("invalid-number-of-days")) {
            System.out.println("\nFailure, " + days + " is an invalid number of days to skip.");
        } else {
            System.out.println("\nFailure, " + hours + " is an invalid number of hours to skip.");
        }
        ViewController.setState(new ClockViewState());
    }

    /**
     * NO-OP
     * @param state The command to handle
     */
    @Override
    public void change(String state) {
        // NO-OP
    }
}
