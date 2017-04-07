package lbms.controllers;

import lbms.LBMS;
import lbms.command.*;
import lbms.models.SystemDateTime;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * CommandController class interacts with the command package to execute commands.
 * @author Team B
 */
public class CommandController {

    private static Command command = null;

    /**
     * Takes in a request string and outputs a response string.
     * @param requestString: the input string to be processed
     * @return the response output string
     */
    public static String processRequest(String requestString) {
        String response = "";

        if (requestString.endsWith(";")) {
            String request[] = requestString.replace(";", "").split(",", 3);
            try {
                command = createCommand(request);
                response = request[0] + "," + command.execute();
            } catch (MissingParametersException e) {
                response = e.getMessage() + ";";
            } catch (Exception e) {
                e.printStackTrace();
                response = request[0] + request[1] + "missing-parameters,{all};";
            }
        } else if (!requestString.equals("exit")) {
            response = "partial-request;";
        }

        return response;
    }

    /**
     * Getter for the command.
     * @return the command
     */
    public static Command getCommand() {
        return command;
    }

    public static boolean isOpen() {
        return SystemDateTime.getInstance(null).getTime().isAfter(LBMS.OPEN_TIME) &&
                SystemDateTime.getInstance(null).getTime().isBefore(LBMS.CLOSE_TIME);
    }

    public static LocalDateTime getSystemDateTime() {
        return SystemDateTime.getInstance(null).getDateTime();
    }

    /**
     * Creates a command based on the input request.
     * @param request: the input request to be processed
     * @return a Command object for the request
     */
    private static Command createCommand(String[] request) throws Exception {
        if (request[0].equals("connect")) {
            return new ClientConnect();
        } else {
            long clientID = Long.parseLong(request[0]);
            if (LBMS.getSessionProxies().get(clientID) == null) {
                throw new MissingParametersException("invalid-client-id;");
            }

            switch (request[1]) {
                case "disconnect":
                    return new Disconnect(Long.parseLong(request[0]));
                case "create":
                    return new CreateAccount(request[2]);
                case "login":
                    return new LogIn(request[2]);
                case "logout":
                    return new LogOut(request[2]);
                case "undo":
                    return new Undo(request[2]);
                case "redo":
                    return new Redo(request[2]);
                case "service":
                    return new SetBookService(request[2]);
                case "arrive":
                    if (isOpen()) {
                        BeginVisit b = new BeginVisit(request[2]);
                        LBMS.getSessionProxies().get(clientID).addUndoable(b);
                        return b;
                    }
                    return new CloseLibrary();
                case "borrow":
                    if (isOpen()) {
                        Borrow b = new Borrow(request[2]);
                        LBMS.getSessionProxies().get(clientID).addUndoable(b);
                        return b;
                    }
                    return new CloseLibrary();
                case "register":
                    return new RegisterVisitor(request[2]);
                case "depart":
                    EndVisit ev = new EndVisit(request[2]);
                    LBMS.getSessionProxies().get(clientID).addUndoable(ev);
                    return ev;
                case "info":
                    return new LibrarySearch(request[2]);
                case "borrowed":
                    return new FindBorrowed(request[2]);
                case "return":
                    Return r = new Return(request[2]);
                    LBMS.getSessionProxies().get(clientID).addUndoable(r);
                    return r;
                case "pay":
                    PayFine pf = new PayFine(request[2]);
                    LBMS.getSessionProxies().get(clientID).addUndoable(pf);
                    return pf;
                case "search":
                    return new StoreSearch(request[2]);
                case "buy":
                    BookPurchase bp = new BookPurchase(request[2]);
                    LBMS.getSessionProxies().get(clientID).addUndoable(bp);
                    return bp;
                case "advance":
                    return new AdvanceTime(request[2]);
                case "datetime":
                    return new GetDateTime();
                case "report":
                    if (request.length == 2) {
                        return new StatisticsReport("");
                    }
                    return new StatisticsReport(request[2]);
                case "reset":      // FOR TESTING
                    return new ResetTime();
                default:
                    return new Invalid();
            }
        }
    }
}
