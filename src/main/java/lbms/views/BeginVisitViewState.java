package lbms.views;

import lbms.command.BeginVisit;
import lbms.controllers.CommandController;
import lbms.controllers.ViewController;

import java.util.Scanner;

/**
 * Created by Chris on 3/7/17.
 */
public class BeginVisitViewState implements State {
    private boolean SYSTEM_STATUS;
    private long visitorID;

    public BeginVisitViewState(boolean SYSTEM_STATUS) {
        this.SYSTEM_STATUS = SYSTEM_STATUS;
    }

    @Override
    public void init() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("\nWhat is the ID of the visitor entering the library? ");
        visitorID = scanner.nextLong();
    }

    @Override
    public void onEnter() {
        String response = CommandController.processRequest("arrive," + visitorID + ";");
        System.out.println(CommandController.getCommand().parseResponse(response));
        ViewController.setState(new UserMenuViewState(SYSTEM_STATUS));
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
