package lbms.command;

import lbms.LBMS;
import lbms.models.SystemDateTime;
import lbms.models.Visit;
import lbms.models.Visitor;
import lbms.search.UserSearch;

/**
 * EndVisit class for end visit command.
 * @author Team B
 */
public class EndVisit implements Command {

    private long visitorID;

    /**
     * Constructor for an EndVisit command class.
     * @param request: the request input string
     */
    public EndVisit(String request) {
        visitorID = Long.parseLong(request.replaceAll(";$", ""));
    }

    /**
     * Executes the EndVisit command.
     * @return the response string or error message
     */
    @Override
    public String execute() {
        if(UserSearch.BY_ID.findFirst(visitorID) == null) {
            Visitor visitor = UserSearch.BY_ID.findFirst(visitorID);
            if(visitor.getInLibrary()) {
                Visit visit = LBMS.endVisit(visitor);
                long s = visit.getDuration().getSeconds();
                String duration = String.format("%02d:%02d:%02d", s / 3600, (s % 3600) / 60, (s % 60));
                return visitorID + "," + visit.getDepartureTime().format(SystemDateTime.TIME_FORMAT) + "," +
                        duration + ";";
            }
            return "invalid-id;";
        }
        return "invalid-id;";
    }

    /**
     * Parses the response for standard output.
     * @param response: the response string from execute
     * @return the output to be printed
     */
    @Override
    public String parseResponse(String response) {
        String[] fields = response.split(",");
        if(fields[1].equals("invalid-id;")) {
            return "\nVisitor " + visitorID + " is not in the library.";
        }
        else {
            return "\nVisitor " + visitorID + " has left the library at "
                    + fields[2] + " and was in the library for " + fields[3];
        }
    }
}
