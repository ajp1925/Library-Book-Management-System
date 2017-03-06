package lbms.command;

import lbms.API;
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
        if(!API.visitorIsRegistered(visitorID)) {
            return "arrive,invalid-id;";
        }
        if(API.getVisitor(visitorID).getInLibrary()) {
            return "arrive,duplicate;";
        }
        Visit v = API.beginVisit(visitorID);
        return "arrive," + visitorID + "," + v.getDate() + "," + v.getArrivalTime() + ";";
    }
}
