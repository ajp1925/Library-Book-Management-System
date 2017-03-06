package lbms.command;

import lbms.API;
import lbms.models.SystemDateTime;
import lbms.models.Visit;

/**
 * Created by anthony on 3/5/17.
 */
public class EndVisit implements Command {

    private int visitorID;

    public EndVisit(String request) {
        visitorID = Integer.parseInt(request.replace(";", ""));
    }

    @Override
    public String execute() {
        if (API.visitorIsRegisteredID(visitorID) && API.getVisitorByID(visitorID).getInLibrary()) {
            Visit visit = API.endVisit(visitorID);
            return visitorID + "," + visit.getDepartureTime().format(SystemDateTime.TIME_FORMAT) + ", duration;" ;   //TODO add duration
        }

        return "invalid-id;";
    }

}
