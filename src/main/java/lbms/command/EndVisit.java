package lbms.command;

import lbms.LBMS;
import lbms.models.SystemDateTime;
import lbms.models.Visit;
import lbms.models.Visitor;
import lbms.search.UserSearch;

/**
 * EndVisit class for end visit command.
 * @author Team B TODO -> change for R2
 */
public class EndVisit implements Command, Undoable {

    private long visitorID;

    /**
     * Constructor for an EndVisit command class.
     * @param request: the request input string
     * @throws MissingParametersException: missing parameters
     */
    public EndVisit(String request) throws MissingParametersException {
        try {
            visitorID = Long.parseLong(request);
        }
        catch(NumberFormatException e) {
            throw new MissingParametersException("missing-parameters,visitor-ID");
        }
    }

    /**
     * Executes the EndVisit command.
     * @return the response string or error message
     */
    @Override
    public String execute() {
        if(UserSearch.BY_ID.findFirst(visitorID) != null) {
            Visitor visitor = UserSearch.BY_ID.findFirst(visitorID);
            if(visitor != null && visitor.getInLibrary()) {
                Visit visit = LBMS.getCurrentVisits().remove(visitor.getVisitorID());
                visit.depart();
                LBMS.getTotalVisits().add(visit);
                long s = visit.getDuration().getSeconds();
                String duration = String.format("%02d:%02d:%02d", s / 3600, (s % 3600) / 60, (s % 60));
                return visitorID + "," + visit.getDepartureTime().format(SystemDateTime.TIME_FORMAT) + "," +
                        duration + ";";
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
