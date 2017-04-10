package lbms.command;

import lbms.LBMS;

/**
 * Disconnect class for the disconnect command.
 * @author Team B
 */
public class Disconnect implements Command {

    private long clientID;

    /**
     * Constructor for the Disconnect class.
     * @param clientID: the client to disconnect
     * @throws MissingParametersException: when the request format is invalid
     */
    public Disconnect(long clientID) throws MissingParametersException {
        this.clientID = clientID;
    }

    /**
     * Processes the command by interacting with the LBMS models.
     * @return the response string
     */
    @Override
    public String execute() {
        if (LBMS.getSessions().remove(this.clientID) == null) {
            return("invalid-client-id;");
        }
        return ";";
    }
}
