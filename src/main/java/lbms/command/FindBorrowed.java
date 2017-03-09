package lbms.command;

import lbms.models.Book;
import lbms.models.Visitor;
import lbms.search.BookSearch;
import lbms.search.UserSearch;

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
        if(UserSearch.BY_ID.findFirst(visitorID) == null) {
            return "invalid-visitor-id;";
        }

        Visitor visitor = UserSearch.BY_ID.findFirst(visitorID);
        StringBuilder sb = new StringBuilder();
        sb.append(visitor.getNumCheckedOut()).append(",\n");
        final int[] id = {1};

        visitor.getCheckedOutBooks().forEach((isbn, transaction) -> {
            Book book = BookSearch.BY_ISBN.search(isbn).get(0);
            sb.append(id[0]++).append(",");
            sb.append(isbn).append(",");
            sb.append(book.getTitle()).append(",");
            sb.append(transaction.getDate()).append("\n");
        });

        return sb.append(";").toString();
    }

    /**
     * Parses the response for standard output.
     * @param response: the response string from execute
     * @return the output to be printed
     */
    @Override
    public String parseResponse(String response) {
        String[] fields = response.split(",", 3);
        if(fields[1].equals("invalid-visitor-id;")) {
            return "\nVisitor " + visitorID + " is not valid.";
        }
        else {
            String result = "\n";
            String[] books = fields[3].split("<nl>");
            for(String book: books) {
                result += "\tbook";
            }
            return result;
        }
    }
}
