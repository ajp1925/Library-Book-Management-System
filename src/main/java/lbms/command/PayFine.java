package lbms.command;

/**
 * Created by anthony on 3/5/17.
 */
public class PayFine implements Command {

    private int visitorID;
    private double amount;

    public PayFine(int visitorID, double amount) {
        this.visitorID = visitorID;
        this.amount = amount;
    }

    public void execute() {
        // TODO Edward
    }

}
