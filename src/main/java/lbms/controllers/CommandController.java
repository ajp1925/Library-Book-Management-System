package lbms.controllers;

import lbms.command.*;

/**
 * CommandController class interacts with the command package to execute commands.
 */
public class CommandController {

    public static String processRequest(String requestString) {
        String[] request = requestString.split(",", 2);
        String response = request[0] + ",";

        try {
            switch (request[0]) {
                case "register":
                    response += new RegisterVisitor(request[1]).execute();
                    break;
                case "arrive":
                    response += new BeginVisit(request[1]).execute();
                    break;
                case "depart":
                    response += new EndVisit(request[1]).execute();
                    System.out.println(response);
                    break;
                case "info":
                    response += new LibrarySearch(request[1]).execute();
                    break;
                case "borrow":
                    response += new Borrow(request[1]).execute();
                    break;
                case "borrowed":
                    response += new FindBorrowed(request[1]).execute();
                    break;
                case "return":
                    response += new Return(request[1]).execute();
                    break;
                case "pay":
                    response += new PayFine(request[1]).execute();
                    break;
                case "search":
                    response += new StoreSearch(request[1]).execute();
                    break;
                case "buy":
                    response += new BookPurchase(request[1]).execute();
                    break;
                case "advance":
                    response += new AdvanceTime(request[1]).execute();
                    break;
                case "datetime":
                    response += new GetDateTime().execute();
                    break;
                case "report":
                    response += new StatisticsReport(request[1]).execute();
                    break;
                default:
                    System.out.println("UH OH");
                    break;
            }
        } catch (Exception e) {

        }

        return response;
    }
}
