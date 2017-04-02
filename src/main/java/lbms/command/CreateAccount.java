package lbms.command;

/**
 * CreateAccount class for the create account command.
 * @author Team B
 */
public class CreateAccount implements Command {

    /**
     * Constructor for the CreateAccount command.
     * @param request: the string for input
     * @throws MissingParametersException: when the request format is invalid
     */
    public CreateAccount(String request) throws MissingParametersException {
        // TODO
    }

    /**
     * Processes the models of the LBMS based on the command.
     * @return the response string
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
