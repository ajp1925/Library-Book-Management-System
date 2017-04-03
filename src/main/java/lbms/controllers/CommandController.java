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
                command = createCommand(SYSTEM_STATUS, request, requestString);
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
    private static Command createCommand(boolean SYSTEM_STATUS, String[] request, String requestString) throws Exception {
        // TODO update this ASAP
        if (request[0].equals("connect")) {
            return new ClientConnect(requestString);
        } else {
            try {
                Long.parseLong(request[0]);
                switch (request[1]) {
                    case "disconnect":
                        return new Disconnect(requestString);
                    case "create":
                        return new CreateAccount(requestString);
                    case "login":
                        return new LogIn(requestString);
                    case "logout":
                        return new LogOut(requestString);
                    case "undo":
                        return new Undo(requestString);
                    case "redo":
                        return new Redo(requestString);
                    case "service":
                        return new SetBookService(requestString);
                    default:
                        return new Invalid();
                }
            } catch (NumberFormatException e) {
                switch (request[0]) {
                    case "arrive":
                        if (SYSTEM_STATUS) {
                            return new BeginVisit(requestString);
                        }
                    case "borrow":
                        if (SYSTEM_STATUS) {
                            return new Borrow(requestString);
                        }
                        return new CloseLibrary();
                    case "register":
                        return new RegisterVisitor(requestString);
                    case "depart":
                        return new EndVisit(requestString);
                    case "info":
                        return new LibrarySearch(requestString);
                    case "borrowed":
                        return new FindBorrowed(requestString);
                    case "return":
                        return new Return(requestString);
                    case "pay":
                        return new PayFine(request[1]);
                    case "search":
                        return new StoreSearch(requestString);
                    case "buy":
                        return new BookPurchase(requestString);
                    case "advance":
                        return new AdvanceTime(requestString);
                    case "datetime":
                        return new GetDateTime();
                    case "report":
                        if (request.length == 1) {
                            return new StatisticsReport("");
                        } else {
                            return new StatisticsReport(requestString);
                        }
                    case "reset":      // FOR TESTING
                        return new ResetTime();
                    default:
                        return new Invalid();
                }
            }
        }
    }
}
