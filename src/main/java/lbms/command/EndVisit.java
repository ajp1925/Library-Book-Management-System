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
public class EndVisit implements Command, Undoable {

    private long visitorID;
    private Visit visit;

    /**
     * Constructor for an EndVisit command class.
     * @param visitorID: the visitorID of the visitor leaving the library
     * @throws MissingParametersException: missing parameters
     */
    public EndVisit(long visitorID) throws MissingParametersException {
        this.visitorID = visitorID;
        this.visit = null;
    }

    /**
     * Executes the EndVisit command.
     * @return the response string or error message
     */
    @Override
    public String execute() {
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
                return ",invalid-id;";
            }
            return ",invalid-id;";
        }
        return ",invalid-id;";
    }

    /**
     * Un-executes the command.
     * @return null if successful, a string if it fails
     */
    @Override
    public String unExecute() {
        this.visit.undepart();
        LBMS.getCurrentVisits().put(this.visitorID, this.visit);
        LBMS.getTotalVisits().remove(this.visit);
        return null;
    }
}
