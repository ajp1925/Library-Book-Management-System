package lbms.command;

import lbms.LBMS;
import lbms.models.SystemDateTime;
import lbms.models.Visit;
import lbms.models.Visitor;
import lbms.search.UserSearch;

/**
 * StartVisit class for the start visit command.
 * @author Team B TODO -> change for R2
 */
public class BeginVisit implements Command, Undoable {

    private long visitorID;

    /**
     * Constructor for BeginVisit command.
     * @param request: the string that holds all the input information
     * @throws MissingParametersException: missing parameters
     */
    public BeginVisit(String request) throws MissingParametersException {
        String[] arguments = request.split(",");
        try {
            this.visitorID = Long.parseLong(arguments[0]);
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            throw new MissingParametersException("missing-parameters,visitor-ID");
        }
    }

    /**
     * Executes the BeginVisit command.
     * @return response or error message
     */
    @Override
    public String execute() {
        if (UserSearch.BY_ID.findFirst(this.visitorID) == null) {
            return "invalid-id;";
        }

        Visitor visitor = UserSearch.BY_ID.findFirst(this.visitorID);
        if (UserSearch.BY_ID.findFirst(this.visitorID).getInLibrary()) {
            return "duplicate;";
        }

        Visit v = beginVisit(visitor);
        return String.format("%010d", this.visitorID) + "," + v.getDate().format(SystemDateTime.DATE_FORMAT)+ "," +
                v.getArrivalTime().format(SystemDateTime.TIME_FORMAT) + ";";
    }

    /**
     * Comment
     */
    @Override
    public void unExecute() {
        // TODO
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
