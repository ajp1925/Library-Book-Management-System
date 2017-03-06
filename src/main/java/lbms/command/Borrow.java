package lbms.command;

import lbms.API;
import lbms.models.Transaction;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by anthony on 3/5/17.
 */
public class Borrow implements Command {

    private int visitorID;
    private ArrayList<Long> ids;

    public Borrow(String request) {
        request = request.replaceAll(";", "");
        String[] arguments = request.split(",");
        visitorID = Integer.parseInt(arguments[0]);
        for (int i = 1; i < arguments.length; i++) {
            ids.add(Long.parseLong(arguments[i]));
        }
    }

    @Override
    public String execute() {
        // TODO Edward
        Collection<Transaction> transactions = API.getVisitorByID(visitorID).getCheckedOutBooks().values();
        if(!API.visitorIsRegisteredID(visitorID)) {
            return "invalid-visitor-id;";
        }
        if () {
            String response = "invalid-book-id,";
            for (Long id : ids) {
                response = response + Long.toString(id) + ",";
            }
            response = response.replaceAll(",$", "");
            return response;
        }
        if (!API.getVisitorByID(visitorID).canCheckOut()) {
            return "book-limit-exceeded";
        }
        for (Transaction transaction : transactions) {
            if (transaction.getFine() > 0) {
                return "outstanding fine," + Double.toString(transaction.getFine());
            }
        }
        return "";
    }

}
