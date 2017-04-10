package lbms.command;

import lbms.LBMS;
import lbms.controllers.commandproxy.ProxyCommandController;
import lbms.models.SystemDateTime;
import lbms.models.Visit;
import lbms.models.Visitor;
import lbms.search.UserSearch;

/**
 * EndVisit class for end visit command.
 * @author Team B TODO -> change for R2
 */
public class EndVisit implements Command, Undoable {

    private long clientID;
    private long visitorID;

    /**
     * Constructor for an EndVisit command class.
     * @param request: the request input string
     * @throws MissingParametersException: missing parameters
     */
    public EndVisit(String request) throws MissingParametersException {
        if (request.equals("")) {
            throw new MissingParametersException("missing-parameters,clientID");
        }

        String[] arguments = request.split(",");
        this.clientID = Long.parseLong(arguments[0]);
        if (arguments.length == 2) {
            this.visitorID = Long.parseLong(arguments[1]);
        } else {
            if ( LBMS.getSessions().get(this.clientID) != null ) {
                this.visitorID = LBMS.getSessions().get(this.clientID).getV().getVisitorID();
            }
        }
    }

    /**
     * Executes the EndVisit command.
     * @return the response string or error message
     */
    @Override
    public String execute() {
        if (LBMS.getSessions().get(this.clientID) == null) {
            return "invalid-client-id;";
        }

        if (UserSearch.BY_ID.findFirst(this.visitorID) != null) {
            Visitor visitor = UserSearch.BY_ID.findFirst(this.visitorID);
            if (visitor != null ) {
                if (ProxyCommandController.assistanceAuthorized(this.visitorID, this.clientID)) {
                    if (visitor.getInLibrary()) {
                        Visit visit = LBMS.getCurrentVisits().remove(visitor.getVisitorID());
                        visit.depart();
                        LBMS.getTotalVisits().add(visit);
                        long s = visit.getDuration().getSeconds();
                        String duration = String.format("%02d:%02d:%02d", s / 3600, (s % 3600) / 60, (s % 60));
                        return this.visitorID + "," + visit.getDepartureTime().format(SystemDateTime.TIME_FORMAT) +
                                "," + duration + ";";
                    }
                    return "invalid-id;";
                }
                return "not-authorized;";
            }
            return "invalid-id;";
        }
        return "invalid-id;";
    }

    @Override
    public void unExecute() {
        // TODO
    }
}
