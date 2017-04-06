package lbms.views.CLI.viewstate;

import lbms.views.CLI.CLIView;

/**
 * SystemViewState class.
 * @author Team B
 */
public class SystemViewState implements State {

    /**
     * Prompts a user whether to views books or users, or exit
     */
    @Override
    public void init() {
        System.out.println("\nPlease select a command: ");
        System.out.println("clock)     View system time");
        System.out.println("report)    View system statistics");
        System.out.println("return)    Return to main menu");
    }

    /**
     * No operation from this method.
     */
    @Override
    public void onEnter() {}

    /**
     * Changes the state of the system.
     * @param state: the command to handle
     */
    public void change(String state) {
        switch(state) {
            case "clock":
                CLIView.setState(new ClockViewState());
                break;
            case "report":
                CLIView.setState(new ReportViewState());
                break;
            case "return":
                CLIView.setState(new DefaultViewState());
                break;
            default:
                System.out.println("Command not found\n");
                this.init();
                break;
        }
    }
}
