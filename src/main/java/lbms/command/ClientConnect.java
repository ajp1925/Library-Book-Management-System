package lbms.command;

import lbms.LBMS;
import lbms.models.Session;

/**
 * ClientConnect class for the client connect command.
 * @author Team B
 */
public class ClientConnect implements Command {

    private Session s;

    /**
     * Constructor parses the request string and creates the necessary data in the class.
     * @throws MissingParametersException: when the request format is invalid
     */
    public ClientConnect() throws MissingParametersException {
        this.s = new Session();
    }

    /**
     * Executes the command by altering the models in the LBMS as necessary.
     * @return the response for the system
     */
    @Override
    public String execute() {
        LBMS.getSessions().put(this.s.getClientID(), this.s);
        return s.getClientID() + ";";
    }
}
