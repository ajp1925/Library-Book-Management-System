package lbms.views;

import lbms.controllers.CommandController;
import lbms.controllers.ViewController;
import java.util.Scanner;

/**
 * Advance view state for the views package.
 * @author Team B
 */
public class AdvanceViewState implements State {

    private boolean SYSTEM_STATUS;
    private int days;
    private int hours;

    /**
     * Constructor for an AdvanceViewState.
     * @param SYSTEM_STATUS: the current status of the system
     */
    public AdvanceViewState(boolean SYSTEM_STATUS) {
        this.SYSTEM_STATUS = SYSTEM_STATUS;
    }

    /**
     * Initializes AdvanceViewState.
     */
    @Override
    public void init() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nHow many days would you like to advance the clock?");
        days = scanner.nextInt();
        System.out.println("How many hours would you like to advance the clock?");
        hours = scanner.nextInt();
    }

    /**
     * Processes the command for advancing the time.
     */
    @Override
    public void onEnter() {
        String response = CommandController.processRequest("advance," + days + "," + hours + ";");
        System.out.println(CommandController.getCommand().parseResponse(response));
        ViewController.setState(new ClockViewState(SYSTEM_STATUS));
    }

    @Override
    public void change(String state) {
        // NO-OP TODO what is this method?
    }
}
