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
    public static String processRequest(String requestString) {
        String[] request = requestString.split(",", 2);
        String response = request[0] + ",";
        // TODO missing semi-colon is incorrect request

        try {
            switch (request[0].replaceAll(";$", "")) {
                case "register":
                    command = new RegisterVisitor(request[1]);
                    response += command.execute();
                    break;
                case "arrive":
                    command = new BeginVisit(request[1]);
                    response += command.execute();
                    break;
                case"depart":
                    command = new EndVisit(request[1]);
                    response += command.execute();
                    break;
                case "info":
                    command = new LibrarySearch(request[1]);
                    response += command.execute();
                    break;
                case "borrow":
                    command = new Borrow(request[1]);
                    response += command.execute();
                    break;
                case "borrowed":
                    command = new FindBorrowed(request[1]);
                    response += command.execute();
                    break;
                case "return":
                    command = new Return(request[1]);
                    response += command.execute();
                    break;
                case "pay":
                    command = new PayFine(request[1]);
                    response += command.execute();
                    break;
                case "search":
                    command =  new StoreSearch(request[1]);
                    response += command.execute();
                    break;
                case "buy":
                    command = new BookPurchase(request[1]);
                    response += command.execute();
                    break;
                case "advance":
                    command = new AdvanceTime(request[1]);
                    response += command.execute();
                    break;
                case "datetime":
                    command = new GetDateTime();
                    response += command.execute();
                    break;
                case "report":
                    command = new StatisticsReport(request[1]);
                    response += command.execute();
                    break;
                case "reset":      // FOR TESTING
                    command = new ResetTime();
                    response += command.execute();
                    break;
                default:
                    System.out.println("UH OH");
                    break;
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            System.exit(1);
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
}
