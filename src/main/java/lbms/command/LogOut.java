package lbms.command;

import lbms.LBMS;
import lbms.models.Session;

/**
 * LogOut class for the log out command.
 * @author Team
 */
public class LogOut implements Command {

    private Long clientID;

    /**
     * Constructor for a log out object.
     * @param clientID: the ID of the client to be logged out
     */
    public LogOut(Long clientID) throws MissingParametersException {
        this.clientID = clientID;
    }

    /**
     * Executes the command by interacting with the backend in the LBMS.
     * @return a response as a string
     */
    public String execute() {
        Session session = LBMS.getSessions().get(this.clientID);
        if(session == null) {
            return ",invalid-client-id;";
        }
        session.setV(null);
        return ",success;";
    }
}
