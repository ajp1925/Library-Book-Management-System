package lbms.views;

import lbms.controllers.CommandController;
import lbms.controllers.ViewController;

/**
 * Created by Chris on 3/7/17.
 */
public class ClockViewState implements State {
    private boolean SYSTEM_STATUS;

    public ClockViewState(boolean SYSTEM_STATUS) {
        this.SYSTEM_STATUS = SYSTEM_STATUS;
    }

    @Override
    public void init() {
        String response = CommandController.processRequest("datetime;");
        System.out.println(CommandController.getCommand().parseResponse(response));
        System.out.println("clock)      View system time");
        System.out.println("advance)    Fast-forward clock");
        System.out.println("reset)      Reset the clock to current time");
        System.out.println("return)     Return to main menu");
    }

    /**
     * NO-OP
     */
    @Override
    public void onEnter() {
        // NO-OP
    }

    @Override
    public void change(String state) {
        switch (state) {
            case "clock": this.init(); break;
            case "advance": ViewController.setState(new AdvanceViewState(SYSTEM_STATUS)); break;
            case "reset": ViewController.setState(new ResetViewState(SYSTEM_STATUS)); break;
            case "return": ViewController.setState(new DefaultViewState(SYSTEM_STATUS)); break;
            default:
                System.out.println("Command not found\n");
                this.init();
        }
    }
}
