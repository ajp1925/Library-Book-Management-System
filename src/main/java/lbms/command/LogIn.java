package lbms.command;

/**
 * LogIn class for login command.
 * @author Team B
 */
public class LogIn implements Command {

    private long clientID;
    private String username;
    private String password;

    /**
     * Constructor for a LogIn class object.
     * @param request: the request string to be processed
     * @throws MissingParametersException: when the request format is invalid
     */
    public LogIn(String request) throws MissingParametersException {
        String parts[] = request.split(",");
        clientID = Long.parseLong(parts[0]);
        username = parts[2];
        password = parts[3];
    }

    /**
     * Executes the command using the information from the request.
     * @return a string of the response to the system
     */
    @Override
    public String execute() {
        // TODO
        return null;
    }
}
