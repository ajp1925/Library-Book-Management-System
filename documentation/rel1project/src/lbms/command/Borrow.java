package lbms.command;

import lbms.LBMS;
import lbms.models.Book;
import lbms.models.SystemDateTime;
import lbms.models.Transaction;
import lbms.models.Visitor;
import lbms.search.UserSearch;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Borrow class that implements the borrow command.
 * @author Team B
 */
public class Borrow implements Command {

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
            if(arguments.length < 2) {
                throw new NumberFormatException();
            }
            visitorID = Long.parseLong(arguments[0]);
            id = new ArrayList<>();
            for(int i = 1; i < arguments.length; i++) {
                id.add(Integer.parseInt(arguments[i]));
            }
        }
        catch(NumberFormatException e) {
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
        if(UserSearch.BY_ID.findFirst(visitorID) == null) {
            return "invalid-visitor-id;";
        }
        else if(UserSearch.BY_ID.findFirst(visitorID).getFines() > 0) {
            return "outstanding-fine," +
                    new DecimalFormat("#.00").format(UserSearch.BY_ID.findFirst(visitorID).getFines()) + ";";
        }
        String invalidIDs = "";
        String temp = "";
        for(Integer i: id) {
            if(!UserSearch.BY_ID.findFirst(visitorID).canCheckOut()) {
                return "book-limit-exceeded;";
            }
            temp = checkOutBook(i, visitorID);
            try {
                if(temp.contains("id-error")) {
                    String[] error = temp.split(",");
                    invalidIDs += error[1];
                }
            }
            catch(NullPointerException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
        if(invalidIDs.length() > 1) {
            String output = "invalid-book-id,";
            output += invalidIDs;
            //output = output.substring(0,output.length() - 1);
            output += ";";
            return output;
        }
        else {
            return temp + ";";
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
        if(fields[1].equals("invalid-visitor-id;")) {
            return "\nVisitor " + visitorID + " is not registered in the system.";
        }
        else if(fields[1].equals("outstanding-fine")) {
            return "\nVisitor " + visitorID + " has to pay " +
                    new DecimalFormat("#.00").format(UserSearch.BY_ID.findFirst(visitorID).getFines()) + " before they " +
                            "can borrow more books.";
        }
        else if(fields[1].equals("book-limit-exceeded;")) {
            return "\nVisitor " + visitorID + " has borrowed the maximum number of books or the borrow request would " +
                    "cause the visitor to exceed 5 borrowed books.";
        }
        else if(fields[1].equals("invalid-book-id")) {
            return "\nOne of more of the book IDs specified do not match the IDs for the most recent library book search.";
        }
        else {
            return "\nThe books have been successfully borrowed and will be due on " + fields[2] + ".";
        }
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
        }
        catch(Exception e) {
            return "id-error," + id;
        }

        if(v.canCheckOut()) {
            v.checkOut(t);
            b.checkOut();
            ArrayList<Transaction> transactions = LBMS.getTransactions();
            transactions.add(t);
            return t.getDueDate().format(SystemDateTime.DATE_FORMAT);
        }
        return "unknown-error";
    }
}
