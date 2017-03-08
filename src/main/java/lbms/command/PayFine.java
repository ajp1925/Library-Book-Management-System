package lbms.command;

import lbms.API;

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
        if (!API.visitorIsRegisteredID(visitorID)) {
            return "invalid-visitor-id;";
        }
        double balance = API.getVisitorByID(visitorID).getFines();
        if(amount < 0 || amount > balance) {
            return "invalid-amount," + amount + "," + API.df.format(balance) + ";";
        }
        else {
            double newBalance = balance - amount;
            API.getVisitorByID(visitorID).payFines(amount);
            return "success," + API.df.format(newBalance) + ";";
        }
    }

    @Override
    public String parseResponse(String response) {
        return null;    //TODO
    }
}
