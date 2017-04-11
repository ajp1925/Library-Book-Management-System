package lbms.command;

import lbms.LBMS;
import lbms.models.Employee;
import lbms.models.Visitor;

/**
 * CreateAccount class for the create account command.
 * @author Team B
 */
public class CreateAccount implements Command {

    private String username;
    private String password;
    private String role;
    private Long visitorID;

    /**
     * Constructor for the CreateAccount command.
     * @param request: the string for input
     * @throws MissingParametersException: when the request format is invalid
     */
    public CreateAccount(String request) throws MissingParametersException {
        String[] arguments = request.split(",");
        if (arguments.length < 4) {
            throw new MissingParametersException(",missing-parameters,username,password,role,visitorID");
        } else {
            this.username = arguments[0];
            this.password = arguments[1];
            this.role = arguments[2];
            try {
                this.visitorID = Long.parseLong(arguments[3]);
            } catch (NumberFormatException e) {
                throw new MissingParametersException(",invalid-visitor;");
            }
        }
    }

    /**
     * Processes the models of the LBMS based on the command.
     * @return the response string
     */
    @Override
    public String execute() {
        // perform error checks
        if (usernameExists()) {
            return ",duplicate-username;";
        }

        Visitor v = LBMS.getVisitors().get(this.visitorID);
        if (v == null) {
            return ",invalid-visitor;";
        }
        if (accountExists(v)) {
            LBMS.getVisitors().put(v.getVisitorID(), v);
            return ",duplicate-visitor;";
        }
        // add the visitor/employee to LBMS
        if (this.role.toLowerCase().equals("visitor") || this.role.toLowerCase().equals("employee")) {
            LBMS.getVisitors().put(this.visitorID, v);
            if (this.role.toLowerCase().equals("employee")) {
                LBMS.getEmployees().put(this.visitorID, new Employee(v));
            }
        } else {
            return ",invalid-role;";
        }
        v.setCredentials(this.username, this.password);
        return ",success;";
    }

    /**
     * Checks if the username already exists in the system
     * @return true if username exists, false otherwise
     */
    private boolean usernameExists() {
        for (Visitor v : LBMS.getVisitors().values()) {
            if (v.getUsername() != null && v.getUsername().equals(this.username)) {
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
