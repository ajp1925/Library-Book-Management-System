package lbms.command;

import lbms.LBMS;
import lbms.models.Book;
import lbms.models.Transaction;
import lbms.models.Visitor;
import lbms.search.BookSearch;
import lbms.search.UserSearch;

/**
 * Queries for a list of books currently borrowed by a specific visitor.
 * @author Team B TODO -> change for R2
 */
public class FindBorrowed implements Command {

    private long visitorID;

    /**
     * Constructor for FindBorrowed class.
     * @param request: the request String for the command
     */
    public FindBorrowed(String request) {
        visitorID = Long.decode(request);
    }

    /**
     * Executes the find borrowed command.
     * @return a response or error message
     */
    @Override
    public String execute() {
        if(UserSearch.BY_ID.findFirst(visitorID) == null) {
            return "invalid-visitor-id;";
        }

        Visitor visitor = UserSearch.BY_ID.findFirst(visitorID);
        String s = "";
        s += visitor.getNumCheckedOut();
        final int[] id = {1};

        Book b;
        LBMS.getLastBookSearch().clear();
        for(Transaction t: visitor.getCheckedOutBooks().values()) {
            b = BookSearch.BY_ISBN.findAll(t.getIsbn()).get(0);
            LBMS.getLastBookSearch().add(b);
            s += "\n";
            s += id[0]++ + "," + t.getIsbn() + "," + b.getTitle() + "," + t.getDate();
        }

        return s + ";";
    }

    /**
     * Parses the response for standard output.
     * @param response: the response string from execute
     * @return the output to be printed
     */
    @Override
    public String parseResponse(String response) {
        String[] fields = response.replace(";", "").split("\n", 2);
        if (fields.length == 1) {
            if (fields[0].endsWith("0")) {
                return "\nVisitor " + visitorID + " has no borrowed books.";
            } else {
                return "\nVisitor " + visitorID + " is not valid.";
            }
        }
        else {
            return "\n" + fields[1];
        }
    }
}
