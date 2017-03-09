package lbms.command;

import lbms.models.SystemDateTime;
import lbms.models.Transaction;
import lbms.models.Visitor;
import lbms.search.UserSearch;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Returns a book borrowed by a library visitor.
 * @author Team B
 */
public class Return implements Command {

    private long visitorID;
    private List<Integer> ids = new ArrayList<>();

    /**
     * Constructor for a Return command object.
     * @param request: the request input string
     */
    public Return(String request) {
        String[] split = request.split(",", 1);
        visitorID = Long.parseLong(split[0]);
        ids = Arrays.stream(split[1].split(",")).map(Integer::parseInt).collect(Collectors.toList());
    }

    /**
     * Executes the return command.
     * @return a response string or error message
     */
    @Override
    public String execute() {
        if(UserSearch.BY_ID.findFirst(visitorID) == null) {
            return "invalid-visitor-id;";
        }
        Visitor visitor = UserSearch.BY_ID.findFirst(visitorID);
        if(visitor.getNumCheckedOut() < ids.size()) {
            return "invalid-book-id," + ids.toString().substring(1, ids.size()-1).replaceAll("\\s", "") + ";";
        }

        int i = 1;
        for(Map.Entry<Long, Transaction> longTransactionEntry: visitor.getCheckedOutBooks().entrySet()) {
            if(ids.contains(i)) {
                if(SystemDateTime.getInstance().getDate().isAfter(longTransactionEntry.getValue().getDueDate())) {
                    return "overdue," + longTransactionEntry.getValue().getFine() + "," + i;
                }
            }
        }
        return "success;";
    }

    /**
     * Parses the string for standard output.
     * @param response: the response string from execute
     * @return the output to be printed
     */
    @Override
    public String parseResponse(String response) {
        return null;    //TODO
    }
}
