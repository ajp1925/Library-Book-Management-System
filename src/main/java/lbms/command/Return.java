package lbms.command;

import lbms.LBMS;
import lbms.models.Book;
import lbms.models.SystemDateTime;
import lbms.models.Transaction;
import lbms.models.Visitor;
import lbms.search.UserSearch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Returns a book borrowed by a library visitor.
 * @author Team B TODO -> change for R2
 */
public class Return implements Command, Undoable {

    private long visitorID;
    private List<Integer> ids = new ArrayList<>();

    /**
     * Constructor for a Return command object.
     * @param request: the request input string
     */
    public Return(String request) {
        request = request.replaceAll(";$", "").replaceAll("\"", "");
        String[] split = request.split(",", 2);
        this.visitorID = Long.parseLong(split[0]);
        this.ids = Arrays.stream(split[1].split(",")).map(Integer::parseInt).collect(Collectors.toList());
    }

    /**
     * Executes the return command.
     * @return a response string or error message
     */
    @Override
    public String execute() {
        if (UserSearch.BY_ID.findFirst(this.visitorID) == null) {
            return "invalid-visitor-id;";
        }
        Visitor visitor = UserSearch.BY_ID.findFirst(this.visitorID);
        ArrayList<Integer> nonBooks = new ArrayList<>();
        for (Integer id : this.ids) {
            if (LBMS.getLastBookSearch().size() <= id) {
                try {
                    Book b = LBMS.getLastBookSearch().get(id - 1);
                    visitor.getCheckedOutBooks().get(b.getIsbn());
                } catch (Exception e) {
                    nonBooks.add(id);
                }
            } else {
                nonBooks.add(id);
            }
        }
        if (nonBooks.size() > 0) {
            String output = "invalid-book-id,";
            for (Integer i: nonBooks) {
                output += i + ",";
            }
            output = output.replaceAll(",$", "");
            return output + ";";
        }

        if (visitor.getFines() > 0.0) {
            String output = "overdue," + String.format("%.2f", visitor.getFines()) + ",";
            for (Transaction t: visitor.getCheckedOutBooks().values()) {
                if (SystemDateTime.getInstance(null).getDate().isAfter(t.getDueDate())) {
                    output += LBMS.getLastBookSearch().indexOf(LBMS.getBooks().get(t.getIsbn())) + ",";
                }
            }
            return output.replaceAll(",$", ";");
        }

        for (Integer i: this.ids) {
            Book b = LBMS.getLastBookSearch().get(i - 1);
            b.returnBook();
            Transaction t = visitor.getCheckedOutBooks().get(b.getIsbn());
            LBMS.getVisitors().get(visitorID).returnBook(t);
            t.closeTransaction();
        }

        return "success;";
    }

    /**
     * Un-executes the command.
     * @return null if successful, a string if it failed
     */
    @Override
    public String unExecute() {
        // TODO
        return null;
    }
}
