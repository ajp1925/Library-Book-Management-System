package lbms.command;

import lbms.API;
import lbms.models.SystemDateTime;
import lbms.models.Visit;

/**
 * EndVisit class for end visit command.
 * @author Team B
 */
public class EndVisit implements Command {

    private int visitorID;

    /**
     * Constructor for an EndVisit class.
     * @param request: the request input string
     */
    public EndVisit(String request) {
        visitorID = Integer.parseInt(request.replace(";$", ""));
    }

    /**
     * Executes the EndVisit command.
     * @return the response string or error message
     */
    @Override
    public String execute() {
        if (API.visitorIsRegisteredID(visitorID) && API.getVisitorByID(visitorID).getInLibrary()) {
            Visit visit = API.endVisit(visitorID);
            return visitorID + "," + visit.getDepartureTime().format(SystemDateTime.TIME_FORMAT) + ", duration;" ;   //TODO add duration
        }
        return "invalid-id;";
    }
}
