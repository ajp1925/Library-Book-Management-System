package lbms.controllers;

import lbms.command.*;

import java.text.ParseException;

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
    public static String processRequest(boolean SYSTEM_STATUS, String requestString) {
        String response = "";

        if (requestString.endsWith(";")) {
            String request[] = requestString.replace(";", "").split(",", 3);
            try {
                command = createCommand(SYSTEM_STATUS, request);
                response = command.execute();
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

    /**
     * Creates a command based on the input request.
     * @param SYSTEM_STATUS: whether or not the system is operational
     * @param request: the input request to be processed
     * @return a Command object for the request
     */
    private static Command createCommand(boolean SYSTEM_STATUS, String[] request) throws Exception {
        if (request[0].equals("connect")) {
            return new ClientConnect();
        } else {
            long clientID = Long.parseLong(request[0]);
            // validate clientID

            switch (request[1]) {
                case "disconnect":
                    return new Disconnect(request[2]);
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
                    if (SYSTEM_STATUS) {
                        return new BeginVisit(request[2]);
                    }
                case "borrow":
                    if (SYSTEM_STATUS) {
                        return new Borrow(request[2]);
                    }
                    return new CloseLibrary();
                case "register":
                    return new RegisterVisitor(request[2]);
                case "depart":
                    return new EndVisit(request[2]);
                case "info":
                    return new LibrarySearch(request[2]);
                case "borrowed":
                    return new FindBorrowed(request[2]);
                case "return":
                    return new Return(request[2]);
                case "pay":
                    return new PayFine(request[2]);
                case "search":
                    return new StoreSearch(request[2]);
                case "buy":
                    return new BookPurchase(request[2]);
                case "advance":
                    return new AdvanceTime(request[2]);
                case "datetime":
                    return new GetDateTime();
                case "report":
                    if (request.length == 2) {
                        return new StatisticsReport("");
                    } else {
                        return new StatisticsReport(request[2]);
                    }
                case "reset":      // FOR TESTING
                    return new ResetTime();
                default:
                    return new Invalid();
            }
        }
    }
}
