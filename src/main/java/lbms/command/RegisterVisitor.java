package lbms.command;

import lbms.API;
import lbms.models.SystemDateTime;
import lbms.models.Visitor;

/**
 * RegisterVisitor class that calls the API to register a visitor in the system.
 * @author Team B
 */
public class RegisterVisitor implements Command {

    private Visitor visitor;

    /**
     * Constructor for the RegisterVisitor command.
     * @param request: the request string to be processed
     */
    public RegisterVisitor(String request) {
        request = request.replaceAll(";$", "");
        String[] arguments = request.split(",");
        visitor = new Visitor(arguments[0], arguments[1], arguments[2], Integer.parseInt(arguments[3]));
    }

    /**
     * Executes the registration of a visitor.
     * @return the response string or error message
     */
    @Override
    public String execute() {
        if(API.registerVisitor(visitor)) {
            SystemDateTime s = SystemDateTime.getInstance();
            return visitor.getVisitorID() + "," + s.getDate().format(SystemDateTime.DATE_FORMAT);
        }
        return "duplicate;";
    }
}
