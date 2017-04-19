package lbms.command;

import lbms.LBMS;
import lbms.controllers.commandproxy.ProxyCommandController;
import lbms.models.SystemDateTime;
import lbms.models.Visit;
import lbms.models.Visitor;
import lbms.search.UserSearch;

/**
 * EndVisit class for end visit command.
 * @author Team B
 */
public class EndVisit implements Undoable {

    private long clientID;
    private long visitorID;
    private Visit visit;

    /**
     * Constructor for an EndVisit command class.
     * @param request: request string holding clientID and visitorID
     */
    public EndVisit(String request) {
        String[] arguments = request.split(",");
        if (arguments.length == 1) {
            this.clientID = Long.parseLong(arguments[0]);
            this.visitorID = LBMS.getSessions().get(this.clientID).getV().getVisitorID();
        } else if (arguments.length == 2) {
            this.clientID = Long.parseLong(arguments[0]);
            this.visitorID = Long.parseLong(arguments[1]);
        }
        this.visit = null;
    }

    /**
     * Executes the EndVisit command.
     * @return the response string or error message
     */
    @Override
    public String execute() {
        if (UserSearch.BY_ID.findFirst(this.visitorID) == null) {
            LBMS.getSessions().get(clientID).popUndoable();
            return ",invalid-id;";
        }

        if (!ProxyCommandController.assistanceAuthorized(this.visitorID, this.clientID)) {
            LBMS.getSessions().get(clientID).popUndoable();
            return ",not-authorized;";
        }

        if (UserSearch.BY_ID.findFirst(this.visitorID) != null) {
            Visitor visitor = UserSearch.BY_ID.findFirst(this.visitorID);
            if (visitor != null ) {
                if (visitor.getInLibrary()) {
                    this.visit = LBMS.getCurrentVisits().remove(visitor.getVisitorID());
                    this.visit.depart();
                    LBMS.getTotalVisits().add(this.visit);
                    long s = this.visit.getDuration().getSeconds();
                    String duration = String.format("%02d:%02d:%02d", s / 3600, (s % 3600) / 60, (s % 60));
                    return "," + String.format("%010d", this.visitorID) + "," +
                            this.visit.getDepartureTime().format(SystemDateTime.TIME_FORMAT) + "," + duration + ";";
                }
                LBMS.getSessions().get(clientID).popUndoable();
                return ",invalid-id;";
            }
            LBMS.getSessions().get(clientID).popUndoable();
            return ",invalid-id;";
        }
        LBMS.getSessions().get(clientID).popUndoable();
        return ",invalid-id;";
    }

    /**
     * Un-executes the command.
     * @return null if successful, a string if it fails
     */
    @Override
    public String unExecute() {
        this.visit.unDepart();
        LBMS.getCurrentVisits().put(this.visitorID, this.visit);
        LBMS.getTotalVisits().remove(this.visit);
        return null;
    }
}
