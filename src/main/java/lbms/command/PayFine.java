package lbms.command;

import lbms.LBMS;
import lbms.search.UserSearch;

import java.text.DecimalFormat;

/**
 * PayFine class for the pay fine command.
 * @author Team B
 */
public class PayFine implements Undoable {

    private long visitorID;
    private double amount;

    /**
     * Constructor for a PayFine command object.
     * @param request: the request string to be processed
     */
    public PayFine(String request) {
        long clientID;
        String[] arguments = request.split(",");
        if (arguments.length == 2) {
            clientID = Long.parseLong(arguments[0]);
            this.amount = Double.parseDouble(arguments[1]);
            this.visitorID = LBMS.getSessions().get(clientID).getV().getVisitorID();
        } else if (arguments.length == 3) {
            this.amount = Double.parseDouble(arguments[1]);
            this.visitorID = Long.parseLong(arguments[2]);
        }
    }

    /**
     * Executes the command for pay fine.
     * @return a response or error message
     */
    @Override
    public String execute() {
        if (UserSearch.BY_ID.findFirst(this.visitorID) == null) {
            return ",invalid-visitor-id;";
        }
        double balance = UserSearch.BY_ID.findFirst(visitorID).getFines();
        if (this.amount < 0 || this.amount > balance) {
            return ",invalid-amount," + this.amount + "," + new DecimalFormat("#.00").format(balance) + ";";
        } else {
            double newBalance = balance - this.amount;
            UserSearch.BY_ID.findFirst(this.visitorID).payFines(this.amount);
            return ",success," + new DecimalFormat("#.00").format(newBalance) + ";";
        }
    }

    /**
     * Un-executes the command.
     * @return null if successful, a string if it fails
     */
    @Override
    public String unExecute() {
        // TODO test this
        UserSearch.BY_ID.findFirst(this.visitorID).payFines(-this.amount);
        return null;
    }
}
