package lbms.views;

import lbms.command.AdvanceTime;
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
        System.out.println(
                AdvanceTime.parseResponse(
                        CommandController.processRequest("advance," + days + "," + hours + ";"), days, hours)
        );
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
