package lbms.command;

import lbms.LBMS;
import lbms.models.Visitor;

/**
 * LogIn class for login command.
 * @author Team B
 */
public class LogIn implements Command {

    private String username;
    private String password;

    /**
     * Constructor for a LogIn class object.
     * @param request: the request string to be processed
     * @throws MissingParametersException: when the request format is invalid
     */
    public LogIn(String request) throws MissingParametersException {
        String parts[] = request.split(",");
        if(parts.length < 2) {
            throw new MissingParametersException("missing-parameters,username,password");
        }
        username = parts[0];
        password = parts[1];
    }

    /**
     * Executes the command using the information from the request.
     * @return a string of the response to the system
     */
    @Override
    public String execute() {
        for(Visitor v : LBMS.getVisitors().values()) {
            if(v.getUsername() != null && v.getUsername().equals(username) && v.getPassword().equals(password)) {
                return "success;";
            }
        }
        return "bad-username-or-password;";
    }
}
