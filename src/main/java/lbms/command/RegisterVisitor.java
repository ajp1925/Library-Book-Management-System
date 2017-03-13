package lbms.command;

import lbms.LBMS;
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
            visitor = new Visitor(arguments[0], arguments[1], arguments[2], Long.parseLong(arguments[3]));
        }
        catch(ArrayIndexOutOfBoundsException | NumberFormatException e) {
            if(arguments.length == 1) {
                throw new MissingParametersException("missing-parameters,last-name,address,phone-number");
            }
            else if(arguments.length == 2) {
                throw new MissingParametersException("missing-parameters,address,phone-number");
            }
            else if(arguments.length == 3) {
                throw new MissingParametersException("missing-parameters,phone-number");
            }
            else {
                throw new MissingParametersException("missing-parameters,first-name,last-name,address,phone-number");
            }
        }
    }

    /**
     * Executes the registration of a visitor.
     * @return the response string or error message
     */
    @Override
    public String execute() {
        if(registerVisitor(visitor)) {
            SystemDateTime s = SystemDateTime.getInstance();
            return String.format("%010d", visitor.getVisitorID()) + "," +
                    s.getDate().format(SystemDateTime.DATE_FORMAT) + ";";
        }
        return "duplicate;";
    }

    /**
     * Parses the response for standard output.
     * @param response: the response string from execute
     * @return the output to be printed
     */
    @Override
    public String parseResponse(String response) {
        String[] fields = response.split(",");
        if(fields[1].equals("duplicate;")) {
            return "This user already exists in the system.";
        }
        else {
            return String.format("\nNew visitor created on %s:\n\tName: %s\n\tAddress: %s\n\tVisitor ID: %d",
                    fields[2], visitor.getName(), visitor.getAddress(), visitor.getVisitorID());
        }
    }

    /**
     * Registers a visitor with the system, if they are not already registered
     * @param visitor: The visitor to register
     * @return true if successfully registered, false if duplicate
     */
    private static boolean registerVisitor(Visitor visitor) {
        if(UserSearch.BY_ID.findFirst(visitor.getVisitorID()) == null &&
                UserSearch.BY_NAME.findFirst(visitor.getName()) == null &&
                UserSearch.BY_ADDRESS.findFirst(visitor.getAddress()) == null &&
                UserSearch.BY_PHONE.findFirst(visitor.getPhoneNumber()) == null) {
            LBMS.getVisitors().put(visitor.getVisitorID(), visitor);
            return true;
        }
        return false;
    }
}
