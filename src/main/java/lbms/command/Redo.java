package lbms.command;

/**
 * Redo class for the redo command.
 * @author Team B
 */
public class Redo implements Command {

    /**
     * Constructor for a Redo class object.
     * @param request: the string to be processed
     * @throws MissingParametersException: when the request format is invalid
     */
    public Redo(String request) throws MissingParametersException {
        // TODO
    }

    /**
     * Processes the command by interacting with the backend in the LBMS.
     * @return a string of the response to the system
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
