package lbms.controllers.commandproxy;

import lbms.LBMS;
import lbms.command.*;
import lbms.models.SystemDateTime;
import lbms.models.Visitor;

import java.time.LocalDateTime;

/**
 * ICommandController class interacts with the command package to execute commands.
 * @author Team B
 */
public class CommandController implements ICommandController {

    private static Command command = null;

    /**
     * Takes in a request string and outputs a response string.
     * @param requestString: the input string to be processed
     * @return the response output string
     */
    public String processRequest(String requestString) {
        String response = "";
        if (requestString.endsWith(";")) {
            String request[] = requestString.replace(";", "").split(",", 3);
            try {
                command = createCommand(request);
                if (request[0].equals("connect")) {
                    response = request[0] + "," + command.execute() + ";";
                } else {
                    response = request[0] + "," + request[1] + command.execute();
                }
            } catch (MissingParametersException e) {
                response = request[0] + "," + request[1] + "," + e.getMessage();
            } catch (Exception e) {
                response = request[0] + "," + request[1] + ",missing-parameters,{all};";
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

    /**
     * Getter for the system clock.
     * @return a local date time of the system clock
     */
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
            if (LBMS.getSessions().get(clientID) == null) {
                throw new MissingParametersException("invalid-client-id;");
            }

            switch (request[1]) {
                case "disconnect":
                    return new Disconnect(Long.parseLong(request[0]));
                case "create":
                    return new CreateAccount(request[2]);
                case "login":
                    return new LogIn(clientID + "," + request[2]);
                case "logout":
                    return new LogOut(clientID);
                case "undo":
                    return new Undo(clientID);
                case "redo":
                    return new Redo(clientID);
                case "service":
                    return new SetBookService(clientID, request[2]);
                case "arrive":
                    if (ProxyCommandController.isOpen()) {
                        BeginVisit b;
                        if (request.length == 2) {
                            b = new BeginVisit(Long.toString(clientID));
                        }
                        else {
                            b = new BeginVisit(clientID + "," + request[2]);
                        }
                        /*
                        if (request.length > 2) {
                            b = new BeginVisit(Long.parseLong(request[2]));
                        } else {
                            Visitor v = LBMS.getSessions().get(clientID).getV();
                            if (v == null) {
                                throw new MissingParametersException("not-logged-in;");
                            }
                            b = new BeginVisit(v.getVisitorID());
                        }
                        */
                        LBMS.getSessions().get(clientID).addUndoable(b);
                        return b;
                    }
                    return new CloseLibrary();
                case "borrow":
                    if (ProxyCommandController.isOpen()) {
                        Borrow b = new Borrow(clientID + "," + request[2]);
                        LBMS.getSessions().get(clientID).addUndoable(b);
                        return b;
                    }
                    return new CloseLibrary();
                case "register":
                    return new RegisterVisitor(request[2]);
                case "depart":
                    EndVisit ev;
                    if (request.length > 2) {
                        ev = new EndVisit(Long.parseLong(request[2]));
                    } else {
                        Visitor v = LBMS.getSessions().get(clientID).getV();
                        if (v == null) {
                            throw new MissingParametersException(clientID + ",depart,not-logged-in");
                        }
                        ev = new EndVisit(v.getVisitorID());
                    }
                    LBMS.getSessions().get(clientID).addUndoable(ev);
                    return ev;
                case "info":
                    return new LibrarySearch(request[2]);
                case "borrowed":
                    return new FindBorrowed(request[2]);
                case "return":
                    Return r = new Return(request[2]);
                    LBMS.getSessions().get(clientID).addUndoable(r);
                    return r;
                case "pay":
                    PayFine pf = new PayFine(request[2]);
                    LBMS.getSessions().get(clientID).addUndoable(pf);
                    return pf;
                case "search":
                    return new StoreSearch(clientID, request[2]);
                case "buy":
                    BookPurchase bp = new BookPurchase(request[2]);
                    LBMS.getSessions().get(clientID).addUndoable(bp);
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
