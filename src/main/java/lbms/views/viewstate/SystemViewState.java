package lbms.views.viewstate;

import lbms.views.CLIView;

/**
 * SystemViewState class.
 * @author Team B
 */
public class SystemViewState implements State {

    private boolean SYSTEM_STATUS;

    /**
     * Constructor for a SystemViewState.
     * @param SYSTEM_STATUS: the status of the system
     */
    SystemViewState(boolean SYSTEM_STATUS) {
        this.SYSTEM_STATUS = SYSTEM_STATUS;
    }

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
                CLIView.setState(new ClockViewState(SYSTEM_STATUS));
                break;
            case "report":
                CLIView.setState(new ReportViewState(SYSTEM_STATUS));
                break;
            case "return":
                CLIView.setState(new DefaultViewState(SYSTEM_STATUS));
                break;
            default:
                System.out.println("Command not found\n");
                this.init();
                break;
        }
    }
}
