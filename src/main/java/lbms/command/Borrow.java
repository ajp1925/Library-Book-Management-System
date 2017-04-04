package lbms.command;

import lbms.LBMS;
import lbms.models.Book;
import lbms.models.SystemDateTime;
import lbms.models.Transaction;
import lbms.models.Visitor;
import lbms.search.UserSearch;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Borrow class that implements the borrow command.
 * @author Team B TODO -> change for R2
 */
public class Borrow implements Command, Undoable {

    private long visitorID;
    private ArrayList<Integer> id;

    /**
     * Constructor for a Borrow class.
     * @param request: the request input string
     * @throws MissingParametersException: missing parameters
     */
    public Borrow(String request) throws MissingParametersException {
        String[] arguments = request.split(",");
        try {
            if (arguments.length < 2) {
                throw new NumberFormatException();
            }
            visitorID = Long.parseLong(arguments[0]);
            id = new ArrayList<>();
            for (int i = 1; i < arguments.length; i++) {
                id.add(Integer.parseInt(arguments[i]));
            }
        } catch (NumberFormatException e) {
            throw new MissingParametersException("missing-parameters,visitor-ID,{ids}");
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
        if (UserSearch.BY_ID.findFirst(visitorID) == null) {
            return "invalid-visitor-id;";
        } else if (UserSearch.BY_ID.findFirst(visitorID).getFines() > 0) {
            return "outstanding-fine," +
                    new DecimalFormat("#.00").format(UserSearch.BY_ID.findFirst(visitorID).getFines()) + ";";
        }
        StringBuilder invalidIDs = new StringBuilder();
        String temp = "";
        for (Integer i: id) {
            if (!UserSearch.BY_ID.findFirst(visitorID).canCheckOut()) {
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
