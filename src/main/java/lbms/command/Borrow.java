package lbms.command;

import lbms.API;
import lbms.LBMS;
import lbms.models.Book;
import lbms.models.SystemDateTime;
import lbms.models.Transaction;
import lbms.models.Visitor;
import lbms.search.BookSearch;
import lbms.search.UserSearch;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Borrow class that implements the borrow command.
 * @author Team B
 */
public class Borrow implements Command {

    private long visitorID;
    private ArrayList<Long> id;

    /**
     * Constructor for a Borrow class.
     * @param request: the request input string
     */
    public Borrow(String request) {
        request = request.replaceAll(";$", "");
        String[] arguments = request.split(",");
        visitorID = Long.parseLong(arguments[0]);
        id = new ArrayList<>();
        for(int i = 1; i < arguments.length; i++) {
            id.add(Long.parseLong(arguments[i]));
        }
    }

    /**
     * Executes the borrow command.
     * @return the response or error message
     */
    @Override
    public String execute() {
        if(!(UserSearch.BY_ID.findFirst(visitorID) != null)) {
            return " invalid-visitor-id;";
        }
        else if (UserSearch.BY_ID.findFirst(visitorID).getFines() > 0) {
            return "outstanding-fine," + new DecimalFormat("#.00").format(UserSearch.BY_ID.findFirst(visitorID).getFines());
        }
        String invalidIDs = "{";
        String temp = "";
        for(Long l: id) {
            if(!UserSearch.BY_ID.findFirst(visitorID).canCheckOut()) {
                return "book-limit-exceeded;";
            }
            temp = checkOutBook(l, visitorID);
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
            output = output.substring(0,output.length() - 1);
            output += "};";
            return output;
        }
        else {
            return temp + ";";
        }
    }

    @Override
    public String parseResponse(String response) {
        return null;    //TODO
    }

    /**
     * Checks out a book for a visitor.
     * @param isbn: the isbn of the book to checkout
     * @param visitorID: the ID of the visitor checking out the book
     * @return a string of the response message
     */
    private static String checkOutBook(long isbn, long visitorID) {
        Transaction t = new Transaction(isbn, visitorID);
        Visitor v = UserSearch.BY_ID.findFirst(visitorID);
        List<Book> l = BookSearch.BY_ISBN.search(isbn);
        Book b;
        if(l.size() == 0) {
            return "id-error," + isbn;
        }
        else {
            b = l.get(0);
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
