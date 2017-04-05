package lbms.command;

import lbms.LBMS;
import lbms.models.sessions.SessionProxy;

/**
 * ClientConnect class for the client connect command.
 * @author Team B
 */
public class ClientConnect implements Command {

    private SessionProxy sp;

    /**
     * Constructor parses the request string and creates the necessary data in the class.
     * @throws MissingParametersException: when the request format is invalid
     */
    public ClientConnect() throws MissingParametersException {
        this.sp = new SessionProxy();
    }

    /**
     * Executes the command by altering the models in the LBMS as necessary.
     * @return the response for the system
     */
    @Override
    public String execute() {
        LBMS.getSessionProxies().put(this.sp.getClientID(), this.sp);
        return sp.getClientID() + ";";
    }
}
