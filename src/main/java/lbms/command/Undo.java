package lbms.command;

/**
 * Undo class for the undo command.
 * @author Team B
 */
public class Undo implements Command {

    /**
     * Constructor for an Undo class object.
     * @param request: the request string to be processed
     * @throws MissingParametersException: when the request format is invalid
     */
    public Undo(String request) throws MissingParametersException {
        // TODO
    }

    /**
     * Executes the command by interacting with the backend in the LBMS.
     * @return the response as a string to the system
     */
    @Override
    public String execute() {
        // TODO
        return null;
    }

    @Override
    public String parseResponse(String response) {
        // TODO
        return null;
    }
}
