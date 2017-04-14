package lbms.command;

import lbms.LBMS;
import lbms.controllers.commandproxy.ProxyCommandController;
import lbms.models.SystemDateTime;
import lbms.models.Visit;
import lbms.models.Visitor;
import lbms.search.UserSearch;

/**
 * StartVisit class for the start visit command.
 * @author Team B
 */
public class BeginVisit implements Command, Undoable {

    private long clientID;
    private long visitorID;

    /**
     * Constructor for BeginVisit command.
     * //@param visitorID: the id of the visitor that is beginning a visit
     */
    public BeginVisit(String request) {
        String[] arguments = request.split(",");
        if (arguments.length == 1) {
            this.clientID = Long.parseLong(arguments[0]);
            this.visitorID = LBMS.getSessions().get(this.clientID).getV().getVisitorID();
        }
        else if (arguments.length == 2) {
            this.clientID = Long.parseLong(arguments[0]);
            this.visitorID = Long.parseLong(arguments[1]);
        }
    }

    /**
     * Executes the BeginVisit command.
     * @return response or error message
     */
    @Override
    public String execute() {

        if (UserSearch.BY_ID.findFirst(this.visitorID) == null) {
            return ",invalid-id;";
        }

        if (!ProxyCommandController.assistanceAuthorized(this.visitorID, this.clientID)) {
            return ",not-authorized;";
        }

        Visitor visitor = UserSearch.BY_ID.findFirst(this.visitorID);
        if (UserSearch.BY_ID.findFirst(this.visitorID).getInLibrary()) {
            return ",duplicate;";
        }

        Visit v = beginVisit(visitor);
        return "," + String.format("%010d", this.visitorID) + "," + v.getDate().format(SystemDateTime.DATE_FORMAT)+ ","
                + v.getArrivalTime().format(SystemDateTime.TIME_FORMAT) + ";";
    }

    /**
     * Un-executes the command.
     * @return null if successful, a string if it failed
     */
    @Override
    public String unExecute() {
        LBMS.getCurrentVisits().remove(this.visitorID);
        LBMS.getVisitors().get(this.visitorID).switchInLibrary(false);
        return null;
    }

    /**
     * Adds a current visit to the LBMS.
     * @param visitor: the visitor at the library
     * @return the new visit object
     */
    private Visit beginVisit(Visitor visitor) {
        Visit visit = new Visit(visitor);
        LBMS.getCurrentVisits().put(visitor.getVisitorID(), visit);
        return visit;
    }
}
