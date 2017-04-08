package lbms.command;

import lbms.LBMS;
import lbms.controllers.commandproxy.ProxyCommandController;
import lbms.controllers.commandproxy.RealCommandController;
import lbms.models.Book;
import lbms.models.SystemDateTime;
import lbms.models.Transaction;
import lbms.models.Visitor;
import lbms.search.BookSearch;
import lbms.search.UserSearch;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Borrow class that implements the borrow command.
 * @author Team B TODO -> change for R2
 */
public class Borrow implements Command, Undoable {

    private long clientID;
    private long visitorID;
    private ArrayList<Integer> ids = new ArrayList<>();

    /**
     * Constructor for a Borrow class.
     * @param request: the request input string
     * @throws MissingParametersException: missing parameters
     */
    public Borrow(String request) throws MissingParametersException {
        String[] allArguments = request.split(",");
        if (allArguments.length < 2) {
            throw new MissingParametersException("missing-parameters,clientID,{ids}");
        }
        clientID = Long.parseLong(allArguments[0]);
        String[] arguments = Arrays.copyOfRange(allArguments, 1, allArguments.length);

        if (arguments.length < 1) {
            throw new MissingParametersException("missing-parameters,{ids}");
        }
        int index = 0;
        if(arguments[index].startsWith("{")) {
            while(!arguments[index].endsWith("}")) {
                ids.add(Integer.parseInt(arguments[index++].replaceAll("[{}]", "")));
            }
            ids.add(Integer.parseInt(arguments[index].replaceAll("[{}]", "")));
        }
        else {
            throw new MissingParametersException("missing-parameters,{ids}");
        }

        if(index < arguments.length - 1) {
            visitorID = Long.parseLong(arguments[index+1]);
        }
        else {
            visitorID = LBMS.getSessions().get(clientID).getV().getVisitorID();
        }

    }

    /**
     * Executes the borrow command.
     * @return the response or error message
     *
     * TODO make sure only x people can borrow x copies of books
     */
    @Override
    public String execute() {
        if(!ProxyCommandController.assistanceAuthorized(visitorID, clientID)) {
            return "not-authorized;";
        }

        if (UserSearch.BY_ID.findFirst(visitorID) == null) {
            return "invalid-visitor-id;";
        } else if (UserSearch.BY_ID.findFirst(visitorID).getFines() > 0) {
            return "outstanding-fine," +
                    new DecimalFormat("#.00").format(UserSearch.BY_ID.findFirst(visitorID).getFines()) + ";";
        }
        StringBuilder invalidIDs = new StringBuilder();
        String temp = "";
        for (Integer i: ids) {
            if (!UserSearch.BY_ID.findFirst(visitorID).canCheckOut()) {
                return "book-limit-exceeded;";
            }
            if(i <= LBMS.getLastBookSearch().size() && LBMS.getLastBookSearch().get(i - 1).getCopiesAvailable() < 1) {
                return "book-limit-exceeded;";
            }
            temp = checkOutBook(i, visitorID);
            try {
                if (temp.contains("id-error")) {
                    String[] error = temp.split(",");
                    invalidIDs.append(error[1]);
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
        if (invalidIDs.length() > 1) {
            String output = "invalid-book-id,";
            output += invalidIDs;
            //output = output.substring(0,output.length() - 1);
            output += ";";
            return output;
        } else {
            return temp + ";";
        }
    }

    @Override
    public void unExecute() {
        // TODO
    }

    /**
     * Checks out a book for a visitor.
     * @param id: the temp id of the book
     * @param visitorID: the ID of the visitor checking out the book
     * @return a string of the response message
     */
    private String checkOutBook(int id, long visitorID) {
        Book b;
        Visitor v;
        Transaction t;
        try {
            b = LBMS.getLastBookSearch().get(id - 1);
            t = new Transaction(b.getIsbn(), visitorID);
            v = UserSearch.BY_ID.findFirst(visitorID);
        } catch (Exception e) {
            return "id-error," + id;
        }

        if (v.canCheckOut()) {
            v.checkOut(t);
            b.checkOut();
            List<Transaction> transactions = LBMS.getTransactions();
            transactions.add(t);
            return t.getDueDate().format(SystemDateTime.DATE_FORMAT);
        }
        return "unknown-error";
    }
}
