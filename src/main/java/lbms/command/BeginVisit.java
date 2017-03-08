package lbms.command;

import lbms.API;
import lbms.controllers.CommandController;
import lbms.models.SystemDateTime;
import lbms.models.Visit;
import lbms.models.Visitor;

/**
 * StartVisit class for the start visit command.
 * @author Team B
 */
public class BeginVisit implements Command {

    private long visitorID;

    /**
     * Constructor for BeginVisit command.
     * @param request: the string that holds all the input information
     */
    public BeginVisit(String request) {
        request = request.replaceAll(";$", "");
        String[] arguments = request.split(",");
        visitorID = Long.parseLong(arguments[0]);
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

        Visitor visitor = API.getVisitorByID(visitorID);
        if(API.getVisitorByID(visitorID).getInLibrary()) {
            return "duplicate;";
        }

        Visit v = API.beginVisit(visitor);
        return visitorID + "," + v.getDate().format(SystemDateTime.DATE_FORMAT)+ "," + v.getArrivalTime().format(SystemDateTime.TIME_FORMAT) + ";";
    }

    @Override
    public String parseResponse(String response) {
        String[] fields = response.split(",");
        if (fields[1].equals("duplicate;")) {
            return "\nVisitor " + visitorID + " is already in the library.";
        } else if (fields[1].equals("invalid-id;")) {
            return "\nVisitor " + visitorID + " is not registered in the system.";
        } else {
            return "\nVisitor " + visitorID + " has entered the library on "
                    + fields[2] + " at " + fields[3].replace(";","") + ".";
        }
    }
}
