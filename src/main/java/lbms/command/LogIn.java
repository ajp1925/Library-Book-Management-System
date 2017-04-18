package lbms.command;

import lbms.LBMS;
import lbms.models.Visitor;

/**
 * LogIn class for login command.
 * @author Team B
 */
public class LogIn implements Command {

    private Long clientID;
    private String username;
    private String password;

    /**
     * Constructor for a LogIn class object.
     * @param request: the request string to be processed
     * @throws MissingParametersException: when the request format is invalid
     */
    public LogIn(String request) throws MissingParametersException {
        String parts[] = request.split(",");
        if (parts.length == 1) {
            throw new MissingParametersException("missing-parameters,{all};");
        } else if (parts.length == 2) {
            throw new MissingParametersException("missing-parameters,{password};");
        }
        this.clientID = Long.parseLong(parts[0]);
        this.username = parts[1];
        this.password = parts[2];
    }

    /**
     * Executes the command using the information from the request.
     * @return a string of the response to the system
     */
    @Override
    public String execute() {
        for (Visitor v: LBMS.getVisitors().values()) {
            if (v.getUsername() != null && v.getUsername().equals(this.username) &&
                    v.getPassword().equals(this.password)) {
                LBMS.getSessions().get(this.clientID).setV(v);
                return ",success;";
            }
        }
        return ",bad-username-or-password;";
    }
}
