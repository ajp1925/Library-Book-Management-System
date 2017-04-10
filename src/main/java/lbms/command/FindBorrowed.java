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
        this.visitorID = Long.decode(request);
    }

    /**
     * Executes the find borrowed command.
     * @return a response or error message
     */
    @Override
    public String execute() {
        if (UserSearch.BY_ID.findFirst(this.visitorID) == null) {
            return "invalid-visitor-id;";
        }

        Visitor visitor = UserSearch.BY_ID.findFirst(this.visitorID);
        String s = "";
        s += visitor.getNumCheckedOut();
        final int[] id = {1};

        Book b;
        LBMS.getLastBookSearch().clear();
        for (Transaction t: visitor.getCheckedOutBooks().values()) {
            b = BookSearch.BY_ISBN.findAll(t.getIsbn().toString()).get(0);
            LBMS.getLastBookSearch().add(b);
            s += "\n";
            s += id[0]++ + "," + t.getIsbn() + "," + b.getTitle() + "," + t.getDate();
        }
        return s + ";";
    }
}
