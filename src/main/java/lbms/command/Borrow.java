package lbms.command;

import lbms.API;
import lbms.models.Transaction;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Borrow class that implements the borrow command.
 * @author Team B
 */
public class Borrow implements Command {

    private int visitorID;
    private ArrayList<Integer> id;

    /**
     * Constructor for a Borrow class.
     * @param request: the request input string
     */
    public Borrow(String request) {
        request = request.replaceAll(";", "");
        String[] arguments = request.split(",");
        visitorID = Integer.parseInt(arguments[0]);
        for (int i = 1; i < arguments.length; i++) {
            id.add(Integer.parseInt(arguments[i]));
        }
    }

    /**
     * Executes the borrow command.
     * @return the response or error message
     */
    @Override
    public String execute() {
        // TODO change error message for invalid ID
        Collection<Transaction> transactions = API.getVisitorByID(visitorID).getCheckedOutBooks().values();
        if(!API.visitorIsRegisteredID(visitorID)) {
            return "invalid-id;";
        }
        if (!API.getVisitorByID(visitorID).canCheckOut()) {
            return "book-limit-exceeded;";
        }
        for (Transaction transaction : transactions) {
            if (transaction.getFine() > 0) {
                return "outstanding-fine," + Double.toString(transaction.getFine()) + ";";
            }
        }
        return "due date;";
    }
}
