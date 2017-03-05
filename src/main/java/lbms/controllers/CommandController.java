package lbms.controllers;

import lbms.command.*;

/**
 * Created by Chris on 3/5/17.
 */
public class CommandController {

    public static String processRequest(String requestString) {

        String[] request = requestString.split(",", 1);
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
                    response += new GetDateTime(request[1]).execute();
                    break;
                case "report":
                    response += new StatisticsReport(request[1]).execute();
                    break;
                default:
                    break;
            }
        } catch (Exception e) {

        }

        return response;
    }
}
