package lbms.command;

import lbms.LBMS;
import lbms.models.PhoneNumber;
import lbms.models.SystemDateTime;
import lbms.models.Visitor;
import lbms.search.UserSearch;

/**
 * RegisterVisitor class that calls the API to register a visitor in the system.
 * @author Team B
 */
public class RegisterVisitor implements Command {

    private Visitor visitor;

    /**
     * Constructor for the RegisterVisitor command.
     * @param request: the request string to be processed
     * @throws MissingParametersException: missing parameters
     */
    public RegisterVisitor(String request) throws MissingParametersException {
        String[] arguments = request.split(",");
        try {
            this.visitor = new Visitor(arguments[0], arguments[1], null, null, arguments[2],
                    new PhoneNumber(arguments[3]));
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            throw new MissingParametersException("missing-parameters,first-name,last-name,address,phone-number;");
        }
    }

    /**
     * Executes the registration of a visitor.
     * @return the response string or error message
     */
    @Override
    public String execute() {
        if (registerVisitor(this.visitor)) {
            SystemDateTime s = SystemDateTime.getInstance(null);
            return "," + String.format("%010d", this.visitor.getVisitorID()) + "," +
                    s.getDate().format(SystemDateTime.DATE_FORMAT) + ";";
        }
        return ",duplicate;";
    }

    /**
     * Registers a visitor with the system, if they are not already registered
     * @param visitor: The visitor to register
     * @return true if successfully registered, false if duplicate
     */
    private static boolean registerVisitor(Visitor visitor) {
        if (UserSearch.BY_ID.findFirst(visitor.getVisitorID()) == null) {
            if (UserSearch.BY_NAME.findFirst(visitor.getName()) == null) {
                LBMS.getVisitors().put(visitor.getVisitorID(), visitor);
                return true;
            } else {
                Visitor v = UserSearch.BY_NAME.findFirst(visitor.getName());
                if (v.getPhoneNumber().toString().equals(visitor.getPhoneNumber().toString())) { // uses toString (no .equals)
                    if (v.getAddress().equals(visitor.getAddress())) {
                        return false;
                    } else {
                        LBMS.getVisitors().put(visitor.getVisitorID(), visitor);
                        return true;
                    }
                }
                LBMS.getVisitors().put(visitor.getVisitorID(), visitor);
                return true;
            }
        }
        return false;
    }
}
