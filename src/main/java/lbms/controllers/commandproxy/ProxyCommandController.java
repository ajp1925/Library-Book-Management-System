package lbms.controllers.commandproxy;

import lbms.LBMS;
import lbms.models.Employee;
import lbms.models.SystemDateTime;
import lbms.models.Visitor;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Implementation of a protection proxy to control access
 * to RealCommandController based on access rights.
 * @author Team B
 */
public class ProxyCommandController implements CommandController {

    /**
     * Checks to ensure the command is being requested by the proper user
     * before sending the command to the RealCommandController.
     * @param requestString the input string to be processed
     * @return the response string
     */
    public String processRequest(String requestString) {
        if (requestString.charAt(requestString.length() - 1) != ';' && !requestString.equals("quit") &&
                !requestString.equals("exit")) {
            return "partial-request;";
        }
        String request[] = requestString.replace(";", "").split(",", 3);
        if (request[0].equals("connect")) {
            return new RealCommandController().processRequest(requestString);
        }

        long clientID;
        try {
            clientID = Long.parseLong(request[0]);
        } catch (NumberFormatException e) {
            if (!requestString.equals("quit") && !requestString.equals("exit")) {
                return "invalid-client-id;";
            }
            return "";
        }
        String command = request[1];

        if (unrestricted(command) || !unrestricted(command) && isEmployee(clientID)) {
            return new RealCommandController().processRequest(requestString);
        } else {
           return request[0] + "," +request[1] + "," + "not-authorized; ";
        }
    }

    /**
     * Most commands can only be executed by employees while four
     * can be accessed by any visitor and are therefore "unrestricted"
     * @param command the word from the request string which differentiates the command requested
     * @return true if any visitor can perform the command, false otherwise
     */
    private boolean unrestricted(String command) {
        ArrayList<String> visitorCommands = new ArrayList<>(Arrays.asList(
                        "arrive", "info", "borrow", "depart", "register",
                        "login", "logout", "undo", "redo", "disconnect", "create"
        ));
        return visitorCommands.contains(command);
    }

    /**
     * A particular client id always maps to a visitor but that visitor
     * may also be an employee. This function serves as an employee check.
     * @param clientID the id of the client
     * @return true if the client id represents and employee, false otherwise
     */
    private static boolean isEmployee(long clientID) {
        try {
            Visitor v = LBMS.getSessions().get(clientID).getV();
            for (Employee employee : LBMS.getEmployees().values()) {
                if (employee.getVisitor().getVisitorID() == v.getVisitorID()) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Checks if the library is currently open based on the system time
     * @return true if the library is open, false otherwise
     */
    public static boolean isOpen() {
        return SystemDateTime.getInstance(null).getTime().isAfter(LBMS.OPEN_TIME) &&
                SystemDateTime.getInstance(null).getTime().isBefore(LBMS.CLOSE_TIME);
    }

    /**
     * Employees are able to perform actions for visitors and therefore may
     * input a visitorID which does not match their clientID.
     * Visitors are unable to due this for other visitors.
     * @param visitorID id of visitor to perform action on
     * @param clientID id of client requesting action
     * @return true if clientID can perform action for visitorID, false otherwise
     */
    public static boolean assistanceAuthorized(long visitorID, long clientID) {
        return visitorID == LBMS.getSessions().get(clientID).getV().getVisitorID() ||
                isEmployee(clientID);
    }
}
