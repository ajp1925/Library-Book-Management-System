package lbms.command;

import lbms.LBMS;
import lbms.models.Book;
import lbms.models.SystemDateTime;
import lbms.models.Transaction;
import lbms.models.Visitor;
import lbms.search.UserSearch;

import java.util.ArrayList;
import java.util.List;

/**
 * Returns a book borrowed by a library visitor.
 * @author Team B
 */
public class Return implements Undoable {

    private long visitorID;
    private long clientID;
    private List<Integer> ids = new ArrayList<>();

    /**
     * Constructor for a Return command object.
     * @param request: the request input string
     */
    public Return(String request) {
        int count = 0;
        String[] arguments = request.split(",");
        for (int i = 0; !arguments[i].startsWith("{"); i++) {
            count++;
        }
        if (count == 1) {
            this.clientID = Long.parseLong(arguments[0]);
            this.visitorID = LBMS.getSessions().get(this.clientID).getV().getVisitorID();
            for (int i = 1; i < arguments.length; i++) {
                if (arguments[i].startsWith("{")) {
                    this.ids.add(Character.getNumericValue(arguments[i].charAt(1)));
                } else if (arguments[i].endsWith("}")) {
                    this.ids.add(Character.getNumericValue(arguments[i].charAt(0)));
                } else {
                    this.ids.add(Integer.parseInt(arguments[i]));
                }
            }
            //this.ids = Arrays.stream(arguments[1].split(",")).map(Integer::parseInt).collect(Collectors.toList());
        } else if (count == 2) {
            this.visitorID = Long.parseLong(arguments[1]);
            for (int i = 2; i < arguments.length; i++) {
                if (arguments[i].startsWith("{")) {
                    this.ids.add(Character.getNumericValue(arguments[i].charAt(1)));
                } else if (arguments[i].endsWith("}")) {
                    this.ids.add(Character.getNumericValue(arguments[i].charAt(0)));
                } else {
                    this.ids.add(Integer.parseInt(arguments[i]));
                }
            }
            //this.ids = Arrays.stream(arguments[2].split(",")).map(Integer::parseInt).collect(Collectors.toList());
        }
        /*
        request = request.replaceAll(";$", "").replaceAll("\"", "");
        String[] split = request.split(",", 2);
        this.visitorID = Long.parseLong(split[0]);
        this.ids = Arrays.stream(split[1].split(",")).map(Integer::parseInt).collect(Collectors.toList());
        */
    }

    /**
     * Executes the return command.
     * @return a response string or error message
     */
    @Override
    public String execute() {
        if (UserSearch.BY_ID.findFirst(this.visitorID) == null) {
            LBMS.getSessions().get(clientID).popUndoable();
            return ",invalid-visitor-id;";
        }
        Visitor visitor = UserSearch.BY_ID.findFirst(this.visitorID);
        ArrayList<Integer> nonBooks = new ArrayList<>();
        for (Integer id : this.ids) {
            if (LBMS.getSessions().get(this.clientID).getBookSearch().size() >= id) {
                try {
                    Book b = LBMS.getSessions().get(this.clientID).getBookSearch().get(id - 1);
                    visitor.getCheckedOutBooks().get(b.getIsbn());
                } catch (Exception e) {
                    nonBooks.add(id);
                }
            } else {
                nonBooks.add(id);
            }
        }
        if (nonBooks.size() > 0) {
            String output = ",invalid-book-id,";
            LBMS.getSessions().get(clientID).popUndoable();
            for (Integer i: nonBooks) {
                output += i + ",";
            }
            output = output.replaceAll(",$", "");
            return output + ";";
        }

        if (visitor.getFines() > 0.0) {
            String output = ",overdue," + String.format("%.2f", visitor.getFines()) + ",";
            for (Transaction t: visitor.getCheckedOutBooks().values()) {
                if (SystemDateTime.getInstance(null).getDate().isAfter(t.getDueDate())) {
                    output += LBMS.getSessions().get(this.clientID).getBookSearch().indexOf(LBMS.getBooks().get(t.getIsbn())) + 1 + ",";
                }
            }
            LBMS.getSessions().get(clientID).popUndoable();
            return output.replaceAll(",$", ";");
        }

        for (Integer i: this.ids) {
            Book b = LBMS.getSessions().get(this.clientID).getBookSearch().get(i - 1);
            b.returnBook();
            Transaction t = visitor.getCheckedOutBooks().get(b.getIsbn());
            LBMS.getVisitors().get(visitorID).returnBook(t);
            t.closeTransaction();
        }

        return ",success;";
    }

    /**
     * Un-executes the command.
     * @return null if successful, a string if it failed
     */
    @Override
    public String unExecute() {
        // TODO test this
        Visitor visitor = UserSearch.BY_ID.findFirst(this.visitorID);
        for (Integer id : this.ids) {
            Book b = LBMS.getSessions().get(this.clientID).getBookSearch().get(id - 1);
            System.out.println(b.getCopiesAvailable());
            b.undoReturnBook();
            System.out.println(b.getCopiesAvailable());
            Transaction t = visitor.getPreviousCheckedOutBooks().get(b.getIsbn());
            System.out.println(visitor.getFines());
            System.out.println(visitor.getPreviousCheckedOutBooks());
            System.out.println(visitor.getCheckedOutBooks());
            LBMS.getVisitors().get(this.visitorID).undoReturnBook(t);
            System.out.println(visitor.getFines());
            System.out.println(visitor.getPreviousCheckedOutBooks());
            System.out.println(visitor.getCheckedOutBooks());
        }
        return null;
    }
}
