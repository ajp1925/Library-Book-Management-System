package lbms.command;

/**
 * ClientConnect class for the client connect command.
 * @author Team B
 */
public class ClientConnect implements Command {

    /**
     * Constructor parses the request string and creates the necessary data in the class.
     * @param request: the string that will be parsed for input
     * @throws MissingParametersException: when the request format is invalid
     */
    public ClientConnect(String request) throws MissingParametersException {
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

    @Override
    public String parseResponse(String response) {
        // TODO move? remove? who needs this method anyways?
        return null;
    }
}
