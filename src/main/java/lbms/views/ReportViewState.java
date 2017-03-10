package lbms.views;

import lbms.controllers.CommandController;
import lbms.controllers.ViewController;

import javax.swing.text.View;
import java.util.Scanner;

/**
 * Created by Chris on 3/10/17.
 */
public class ReportViewState implements State {
    private boolean SYSTEM_STATUS;
    private String input = null;
    private Integer days = null;

    /**
     * Constructor for a SystemViewState.
     * @param SYSTEM_STATUS: the status of the system
     */
    public ReportViewState(boolean SYSTEM_STATUS) {
        this.SYSTEM_STATUS = SYSTEM_STATUS;
    }

    /**
     * Prompts a user whether to views books or users, or exit
     */
    @Override
    public void init() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nHow many days would you like a report for? Type \"all\" to get all statistics.");
        input = scanner.nextLine();

        try {
            days = Integer.parseInt(input);
        } catch (Exception e){
            days = null;
        }
    }

    @Override
    public void onEnter() {
        String response;
        if (days == null) {
            response = CommandController.processRequest(this.SYSTEM_STATUS, "report;");
        } else {
            response = CommandController.processRequest(this.SYSTEM_STATUS, "report," + days + ";");
        }

        System.out.println("\n" + CommandController.getCommand().parseResponse(response));
        ViewController.setState(new SystemViewState(SYSTEM_STATUS));
    }
    public void change(String state) { }
}
