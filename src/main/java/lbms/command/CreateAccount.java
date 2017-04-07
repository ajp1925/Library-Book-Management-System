package lbms.command;

import lbms.LBMS;
import lbms.models.Employee;
import lbms.models.Visitor;

/**
 * CreateAccount class for the create account command.
 * @author Team B
 */
public class CreateAccount implements Command {

    String username;
    String password;
    String role;
    Long visitorID;

    /**
     * Constructor for the CreateAccount command.
     * @param request: the string for input
     * @throws MissingParametersException: when the request format is invalid
     */
    public CreateAccount(String request) throws MissingParametersException {
        String[] arguments = request.split(",");
        if(arguments.length < 4) {
            throw new MissingParametersException("missing-parameters,username,password,role,visitorID");
        }
        else {
            this.username = arguments[0];
            this.password = arguments[1];
            this.role = arguments[2];
            this.visitorID = Long.parseLong(arguments[3]);
        }
    }

    /**
     * Processes the models of the LBMS based on the command.
     * @return the response string
     */
    @Override
    public String execute() {
        // perform error checks
        if(usernameExists()) {
            return "duplicate-username;";
        }

        Visitor v = LBMS.getVisitors().remove(visitorID);
        if(v == null) {
            return "invalid-visitor;";
        }
        if(accountExists(v)) {
            LBMS.getVisitors().put(v.getVisitorID(), v);
            return "duplicate-visitor;";
        }
        // add the visitor/employee to LBMS
        if(role.toLowerCase().equals("visitor") || role.toLowerCase().equals("employee")) {
            LBMS.getVisitors().put(visitorID,
                    new Visitor(v.getFirstName(), v.getLastName(), username, password, v.getAddress(), v.getPhoneNumber()));
            if(role.toLowerCase().equals("employee")) {
                LBMS.getEmployees().put(visitorID,
                        new Employee(v.getFirstName(), v.getLastName(), username, password, v.getAddress(), v.getPhoneNumber()));
            }
        }
        else {
            return "invalid-role;";
        }

        return "success;";
    }

    /**
     * Checks if the username already exists in the system
     * @return true if username exists, false otherwise
     */
    private boolean usernameExists() {
        for(Visitor v : LBMS.getVisitors().values()) {
            if(v.getUsername() != null && v.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the visitor already has an account
     * @param v visitor trying to make an account
     * @return true if account already exists, false otherwise
     */
    private boolean accountExists(Visitor v) {
        return v.getUsername() != null && v.getPassword() != null;
    }
}
