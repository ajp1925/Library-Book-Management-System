package lbms.command;

import lbms.API;
import lbms.models.SystemDateTime;
import lbms.models.Visit;
import lbms.models.Visitor;

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
        if(API.visitorIsRegisteredID(visitorID)) {
            Visitor visitor = API.getVisitorByID(visitorID);
            if(visitor.getInLibrary()) {
                Visit visit = API.endVisit(visitor);
                long s = visit.getDuration().getSeconds();
                String duration = String.format("%02d:%02d:%02d", s / 3600, (s % 3600) / 60, (s % 60));
                return visitorID + "," + visit.getDepartureTime().format(SystemDateTime.TIME_FORMAT) + "," +
                        duration + ";";
            }
            return "invalid-id;";
        }
        return "invalid-id;";
    }
}
