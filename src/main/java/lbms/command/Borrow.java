package lbms.command;

import lbms.API;
import java.util.ArrayList;

/**
 * Borrow class that implements the borrow command.
 * @author Team B
 */
public class Borrow implements Command {

    private long visitorID;
    private ArrayList<Long> id;

    /**
     * Constructor for a Borrow class.
     * @param request: the request input string
     */
    public Borrow(String request) {
        request = request.replaceAll(";", "");
        String[] arguments = request.split(",");
        visitorID = Long.parseLong(arguments[0]);
        id = new ArrayList<>();
        for(int i = 1; i < arguments.length; i++) {
            id.add(Long.parseLong(arguments[i]));
        }
    }

    /**
     * Executes the borrow command.
     * @return the response or error message
     */
    @Override
    public String execute() {
        if(!API.visitorIsRegisteredID(visitorID)) {
            return " invalid-visitor-id;";
        }
        else if(API.visitorFines(visitorID) > 0) {
            return "outstanding-fine," + API.df.format(API.getVisitorByID(visitorID).getFines());
        }
        String invalidIDs = "{";
        String temp = "";
        for(Long l: id) {
            if(!API.getVisitorByID(visitorID).canCheckOut()) {
                return "book-limit-exceeded;";
            }
            temp = API.checkOutBook(l, visitorID);
            try {
                if(temp.contains("id-error")) {
                    String[] error = temp.split(",");
                    invalidIDs += error[1];
                }
            }
            catch(NullPointerException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
        if(invalidIDs.length() > 1) {
            String output = "invalid-book-id,";
            output += invalidIDs;
            output = output.substring(0,output.length() - 1);
            output += "};";
            return output;
        }
        else {
            return temp + ";";
        }
    }
}
