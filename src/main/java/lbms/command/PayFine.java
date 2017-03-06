package lbms.command;

import lbms.API;
import lbms.models.Transaction;

import java.util.Collection;

/**
 * Created by anthony on 3/5/17.
 */
public class PayFine implements Command {

    private int visitorID;
    private double amount;

    public PayFine(String request) {
        request = request.replaceAll(";", "");
        String[] arguments = request.split(",");
        visitorID = Integer.parseInt(arguments[0]);
        amount = Double.parseDouble(arguments[1]);
    }

    @Override
    public String execute() {
        // TODO Edward
        double balance = 0.00;
        Collection<Transaction> transactions = API.getVisitorByID(visitorID).getCheckedOutBooks().values();
        if (!API.visitorIsRegisteredID(visitorID)) {
            return "invalid-visitor-id";
        }
        for (Transaction transaction : transactions) {
            balance += transaction.getFine();
        }
        if (amount < 0 || amount > balance) {
            return "invalid-amount," + Double.toString(amount) + "," + Double.toString(balance);
        }
        return "success," + Double.toString(balance);
    }

}
