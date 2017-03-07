package lbms.command;

import lbms.API;
import lbms.models.Book;
import lbms.models.Visitor;
import lbms.search.SearchByISBN;

/**
 * Queries for a list of books currently borrowed by a specific visitor.
 * @author Team B
 */
public class FindBorrowed implements Command {

    private long visitorID;

    /**
     * Constructor for FindBorrowed class.
     * @param request: the request String for the command
     */
    public FindBorrowed(String request) {
        request = request.replaceAll(";$", "");
        visitorID = Long.decode(request);
    }

    /**
     * Executes the find borrowed command.
     * @return a response or error message
     */
    @Override
    public String execute() {
        if (!API.visitorIsRegisteredID(visitorID)) {
            return "invalid-visitor-id;";
        }

        Visitor visitor = API.getVisitorByID(visitorID);
        StringBuilder sb = new StringBuilder();
        sb.append(visitor.getNumCheckedOut()).append(",\n");
        final int[] id = {1};

        visitor.getCheckedOutBooks().forEach((isbn, transaction) -> {
            Book book = API.findBooks(new SearchByISBN(isbn)).get(0);
            sb.append(id[0]++).append(",");
            sb.append(isbn).append(",");
            sb.append(book.getTitle()).append(",");
            sb.append(transaction.getDate()).append("\n");
        });

        return sb.append(";").toString();
    }
}
