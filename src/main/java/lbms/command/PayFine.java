package lbms.command;

import lbms.search.UserSearch;

import java.text.DecimalFormat;

/**
 * PayFine class for the pay fine command.
 * @author Team B
 */
public class PayFine implements Command {

    private long visitorID;
    private double amount;

    /**
     * Constructor for a PayFine command object.
     * @param request: the request string to be processed
     */
    public PayFine(String request) {
        request = request.replaceAll(";$", "");
        String[] arguments = request.split(",");
        visitorID = Long.parseLong(arguments[0]);
        amount = Double.parseDouble(arguments[1]);
    }

    /**
     * Executes the command for pay fine.
     * @return a response or error message
     */
    @Override
    public String execute() {
        if(!(UserSearch.BY_ID.findFirst(visitorID) != null)) {
            return "invalid-visitor-id;";
        }
        double balance = UserSearch.BY_ID.findFirst(visitorID).getFines();
        if(amount < 0 || amount > balance) {
            return "invalid-amount," + amount + "," + new DecimalFormat("#.00").format(balance) + ";";
        }
        else {
            double newBalance = balance - amount;
            UserSearch.BY_ID.findFirst(visitorID).payFines(amount);
            return "success," + new DecimalFormat("#.00").format(newBalance) + ";";
        }
    }

    @Override
    public String parseResponse(String response) {
        return null;    //TODO
    }
}
