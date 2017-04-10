package lbms.command;

import lbms.LBMS;
import lbms.models.Session;

/**
 * LogOut class for the log out command.
 * @author Team  TODO -> test this class
 */
public class LogOut implements Command {

    private Long clientID;

    /**
     * Constructor for a log out object.
     * @param request: the string to be processed
     */
    public LogOut(String request) throws MissingParametersException {
        try {
            this.clientID = Long.parseLong(request.split(",")[0]);
        } catch (NumberFormatException e) {
            throw new MissingParametersException("Invalid clientID passed to LogOut");
        }

    }

    /**
     * Executes the command by interacting with the backend in the LBMS.
     * @return a response as a string
     */
    public String execute() {
        Session session = LBMS.getSessions().get(this.clientID);
        if (session == null) {
            return "invalid-client-id;";
        }
        session.setV(null);
        return "success;";
    }
}
