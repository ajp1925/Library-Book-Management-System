package lbms.command;

/**
 * ClientConnect class for the client connect command.
 * @author Team B
 */
public class ClientConnect implements Command {

    /**
     * Constructor parses the request string and creates the necessary data in the class.
     * @throws MissingParametersException: when the request format is invalid
     */
    public ClientConnect() throws MissingParametersException {
        // TODO
    }

    /**
     * Executes the command by altering the models in the LBMS as necessary.
     * @return the response for the system
     */
    @Override
    public String execute() {
        // TODO
        return null;
    }
}
