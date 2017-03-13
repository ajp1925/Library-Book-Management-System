package lbms.controllers;

import lbms.command.*;

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
        String response;

        if(requestString.endsWith(";")) {
            String[] request = requestString.replace(";", "").split(",", 2);
            response = request[0] + ",";
            try {
                command = createCommand(SYSTEM_STATUS, request);
                response += command.execute();
            }
            catch(MissingParametersException e) {
                response += e.getMessage() + ";";
            }
            catch(Exception e) {
                response += "missing-parameters,{all};";
            }
        }
        else {
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
        switch (request[0]) {
            case "arrive":
                if(SYSTEM_STATUS) {
                    return new BeginVisit(request[1]);
                }
            case "borrow":
                if(SYSTEM_STATUS) {
                    return new Borrow(request[1]);
                }
                return new CloseLibrary();
            case "register":
                return new RegisterVisitor(request[1]);
            case "depart":
                return new EndVisit(request[1]);
            case "info":
                return new LibrarySearch(request[1]);
            case "borrowed":
                return new FindBorrowed(request[1]);
            case "return":
                return new Return(request[1]);
            case "pay":
                return new PayFine(request[1]);
            case "search":
                return new StoreSearch(request[1]);
            case "buy":
                return new BookPurchase(request[1]);
            case "advance":
                return new AdvanceTime(request[1]);
            case "datetime":
                return new GetDateTime();
            case "report":
                if(request.length == 1){
                    return new StatisticsReport("");
                }
                else {
                    return new StatisticsReport(request[1]);
                }
            case "reset":      // FOR TESTING
                return new ResetTime();
            default:
                return new Invalid();
        }
    }
}
