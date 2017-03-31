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
        String[] arguments = request.replaceAll(";$", "").split(",");
        visitorID = Long.parseLong(arguments[0]);
        amount = Double.parseDouble(arguments[1]);
    }

    /**
     * Executes the command for pay fine.
     * @return a response or error message
     */
    @Override
    public String execute() {
        if(UserSearch.BY_ID.findFirst(visitorID) == null) {
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

    /**
     * Parses the response for standard output.
     * @param response: the response string from execute
     * @return the output to be printed
     */
    @Override
    public String parseResponse(String response) {
        String[] fields = response.split(",");
        switch (fields[1]) {
            case "invalid-visitor-id;":
                return "\nVisitor " + visitorID + " is not registered in the system.";
            case "invalid-amount":
                return "The specified amount, " + amount + " is not valid as it is negative or exceeds their balance, " +
                        UserSearch.BY_ID.findFirst(visitorID).getFines() + ".";
            default:
                return "The fine has been successfully paid for. The remaining balance is " +
                        UserSearch.BY_ID.findFirst(visitorID).getFines() + ".";
        }
    }
}
