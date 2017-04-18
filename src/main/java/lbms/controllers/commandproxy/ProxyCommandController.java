package lbms.controllers.commandproxy;

import lbms.LBMS;
import lbms.command.Invalid;
import lbms.models.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Implementation of a protection proxy to control access
 * to CommandController based on access rights.
 * @author Team B
 */
public class ProxyCommandController implements ICommandController {

    private static LibraryState libraryStatus = null;

    /**
     * Checks to ensure the command is being requested by the proper user
     * before sending the command to the CommandController.
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
            return new CommandController().processRequest(requestString);
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

        // allows users who are not logged in to only perform specific actions
        if (loginRequired(command) && !isLoggedIn(clientID)) {
            return "not-authorized;";
        }

        if (!isCommand(command)) {
            return new Invalid().execute();
        }

        if (unrestricted(command) || (isLoggedIn(clientID) && isEmployee(clientID))) {
            return new CommandController().processRequest(requestString);
        } else {
            return request[0] + "," + request[1] + "," + "not-authorized;";
        }
    }

    /**
     * Most commands can only be executed by employees while some commands
     * can be accessed by any visitor and are therefore "unrestricted"
     * @param command the word from the request string which differentiates the command requested
     * @return true if any visitor can perform the command, false otherwise
     */
    private boolean unrestricted(String command) {
        ArrayList<String> visitorCommands = new ArrayList<>(Arrays.asList(
                "arrive", "info", "borrow", "depart", "login", "logout", "undo", "redo", "disconnect",
                "search"
        ));
        return visitorCommands.contains(command);
    }

    /**
     * Used to determine if a string is a valid command or not.
     * @param command: the string being checked
     * @return true if it is a command, false if not
     */
    private boolean isCommand(String command) {
        ArrayList<String> commands = new ArrayList<>(Arrays.asList(
                "register", "arrive", "depart", "info", "borrow", "borrowed", "return", "pay", "search", "buy",
                "advance", "datetime", "report", "connect", "disconnect", "create", "login", "logout", "undo", "redo",
                "service"
        ));
        return commands.contains(command);
    }

    /**
     * Used to determine if a command can be executed without being logged in.
     * @param command the command to check
     * @return true if login is required to execute the command, false otherwise
     */
    private boolean loginRequired(String command) {
        ArrayList<String> allowedCommands = new ArrayList<>(Arrays.asList(
                "connect", "disconnect", "login", "logout"
        ));
        return !allowedCommands.contains(command);
    }

    /**
     * A particular client id always maps to a visitor but that visitor
     * may also be an employee. This function serves as an employee check.
     * @param clientID the id of the client
     * @return true if the client id represents and employee, false otherwise
     */
    public static boolean isEmployee(long clientID) {
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
     * Determines if someone is logged into a session/client.
     * @param clientID: the ID of the client
     * @return true if someone is logged in, false if not
     */
    private static boolean isLoggedIn(long clientID) {
        try {
            Session s = LBMS.getSessions().get(clientID);
            Visitor v = s.getV();
            return v != null;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Determines if the visitor is in the library.
     * @param clientID: the id of the client where they are logged
     * @return true if they are in the library, false if not
     */
    public static boolean inLibrary(long clientID) {
        try {
            HashMap<Long, Visit> visits = LBMS.getCurrentVisits();
            Session s = LBMS.getSessions().get(clientID);
            for (Visit v: visits.values()) {
                if (v.getVisitor().getVisitorID() == s.getV().getVisitorID()) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Updates the status of the library.
     */
    private static void updateStatus() {
        LocalTime time = SystemDateTime.getInstance(null).getTime();
        if (time.isAfter(LBMS.OPEN_TIME) && time.isBefore(LBMS.CLOSE_TIME)) {
            libraryStatus  = new OpenState();
        } else {
            libraryStatus = new ClosedState();
        }
    }

    /**
     * Checks if the library is currently open based on the system time
     * @return true if the library is open, false otherwise
     */
    public static boolean isOpen() {
        updateStatus();
        return libraryStatus.isOpen();
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

    /**
     * Getter for the visitor ID when the client ID is known.
     * @param client: the client ID of the session
     * @return the visitor ID of who is logged in
     */
    public static Long getVisitorID(Long client) {
        return LBMS.getSessions().get(client).getV().getVisitorID();
    }

    /**
     * Getter for the store that is currently being searched.
     * @return the search service
     */
    public static String getStore(long clientID) {
        if(LBMS.getSessions().get(clientID).getSearch() == LBMS.SearchService.GOOGLE) {
            return "google";
        } else {
            return "local";
        }
    }
}
