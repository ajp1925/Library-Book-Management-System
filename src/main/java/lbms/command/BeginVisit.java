package lbms.command;

import lbms.API;
import lbms.models.SystemDateTime;
import lbms.models.Visit;

/**
 * StartVisit class for the start visit command.
 * @author Team B
 */
public class BeginVisit implements Command {

    private int visitorID;

    /**
     * Constructor for BeginVisit command.
     * @param request: the string that holds all the input information
     */
    public BeginVisit(String request) {
        request = request.replaceAll(";", "");
        String[] arguments = request.split(",");
        visitorID = Integer.parseInt(arguments[0]);
    }

    /**
     * Executes the BeginVisit command.
     * @return response or error message
     */
    @Override
    public String execute() {
        if(!API.visitorIsRegisteredID(visitorID)) {
            return "invalid-id;";
        }

        if(API.getVisitorByID(visitorID).getInLibrary()) {
            return "duplicate;";
        }

        Visit v = API.beginVisit(visitorID);
        return visitorID + "," + v.getDate().format(SystemDateTime.DATE_FORMAT)+ "," + v.getArrivalTime().format(SystemDateTime.TIME_FORMAT) + ";";
    }
}
