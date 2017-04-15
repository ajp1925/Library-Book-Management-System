package lbms.command;

import lbms.LBMS;
import lbms.controllers.commandproxy.ProxyCommandController;
import lbms.models.Book;
import lbms.models.SystemDateTime;
import lbms.models.Transaction;
import lbms.models.Visitor;
import lbms.search.UserSearch;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Borrow class that implements the borrow command.
 * @author Team B TODO -> change for R2
 */
public class Borrow implements Undoable {

    private long clientID;
    private long visitorID;
    private ArrayList<Integer> ids = new ArrayList<>();

    /**
     * Constructor for a Borrow class.
     * @param request: the request input string
     * @throws MissingParametersException: missing parameters
     */
    public Borrow(String request) throws MissingParametersException {
        String[] allArguments = request.split(",");
        if (allArguments.length < 2) {
            throw new MissingParametersException(",missing-parameters,{ids};");
        }

        this.clientID = Long.parseLong(allArguments[0]);
        String[] arguments = Arrays.copyOfRange(allArguments, 1, allArguments.length);

        for (String arg: arguments) {
            if (arg.equals(arguments[arguments.length - 1]) && !arg.endsWith("}")) {
                throw new MissingParametersException(",missing-parameters,{ids};");
            } else if (arg.endsWith("}")) {
                break;
            }
        }

        if (arguments[arguments.length-1].startsWith("{") && arguments[arguments.length-1].endsWith("}")) {
            this.ids.add(Integer.parseInt(arguments[arguments.length-1].replaceAll("[{}]","")));
        } else if (arguments[arguments.length-1].endsWith("}")) {
            for (String arg: arguments) {
                if (arg.startsWith("{") || arg.endsWith("}")) {
                    this.ids.add(Integer.parseInt(arg.replaceAll("[{}]", "")));
                } else {
                    this.ids.add(Integer.parseInt(arg));
                }
            }
        } else {
            for (int i = 0; i < arguments.length - 1; i++) {
                if (arguments[i].startsWith("{") || arguments[i].endsWith("}")) {
                    this.ids.add(Integer.parseInt(arguments[i].replaceAll("[{}]", "")));
                    if (arguments[i].endsWith("}")) {
                        break;
                    }
                } else {
                    this.ids.add(Integer.parseInt(arguments[i]));
                }
            }
        }

        if (arguments[arguments.length - 1].endsWith("}")) {
            this.visitorID = LBMS.getSessions().get(this.clientID).getV().getVisitorID();
        } else {
            this.visitorID = Long.parseLong(arguments[arguments.length - 1]);
        }
        /*
        int index = 0;
        if (arguments[index].startsWith("{")) {
            while (!arguments[index].endsWith("}")) {
                this.ids.add(Integer.parseInt(arguments[index++].replaceAll("[{}]", "")));
            }
            this.ids.add(Integer.parseInt(arguments[index].replaceAll("[{}]", "")));
        } else {
            throw new MissingParametersException("missing-parameters,{ids};");
        }

        if (index < arguments.length - 1) {
            this.visitorID = Long.parseLong(arguments[index+1]);
        } else {
            this.visitorID = LBMS.getSessions().get(this.clientID).getV().getVisitorID();
        }
        */
    }

    /**
     * Executes the borrow command.
     * @return the response or error message
     *
     * TODO make sure only x people can borrow x copies of books
     */
    @Override
    public String execute() {
        if (!ProxyCommandController.assistanceAuthorized(this.visitorID, this.clientID)) {
            return ",not-authorized;";
        }

        if (UserSearch.BY_ID.findFirst(this.visitorID) == null) {
            return ",invalid-visitor-id;";
        } else if (UserSearch.BY_ID.findFirst(this.visitorID).getFines() > 0) {
            return ",outstanding-fine," + new DecimalFormat("#.00").format(UserSearch.BY_ID
                    .findFirst(this.visitorID).getFines()) + ";";
        }
        StringBuilder invalidIDs = new StringBuilder();
        String temp = "";
        for (Integer i: this.ids) {
            if (!UserSearch.BY_ID.findFirst(this.visitorID).canCheckOut()) {
                return ",book-limit-exceeded;";
            }
            if (i <= LBMS.getLastBookSearch().size() && LBMS.getLastBookSearch().get(i - 1).getCopiesAvailable() < 1) {
                return ",no-more-copies;";
            }
            temp = checkOutBook(i, this.visitorID);
            try {
                if (temp.contains("id-error")) {
                    String[] error = temp.split(",");
                    String string = error[1] + ",";
                    invalidIDs.append(string);
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
        if (invalidIDs.length() > 0) {
            String output = "invalid-book-id,";
            invalidIDs.deleteCharAt(invalidIDs.length()-1);
            output += "{" + invalidIDs + "}";
            //output = output.substring(0,output.length() - 1);
            output += ";";
            return "," + output;
        } else {
            return "," + temp + ";";
        }
    }

    /**
     * Un-executes the command.
     * @return null if successful, a string if it fails
     */
    @Override
    public String unExecute() {
        // TODO
        for (int id : this.ids) {
            Book b = LBMS.getLastBookSearch().get(id - 1);
            Transaction t = new Transaction(b.getIsbn(), this.visitorID);
            Visitor visitor = UserSearch.BY_ID.findFirst(this.visitorID);
            visitor.undoCheckOut(t);
            b.undoCheckOut();
            List<Transaction> transactions = LBMS.getTransactions();
            transactions.remove(t);
        }
        return null;
    }

    /**
     * Checks out a book for a visitor.
     * @param id: the temp id of the book
     * @param visitorID: the ID of the visitor checking out the book
     * @return a string of the response message
     */
    private String checkOutBook(int id, long visitorID) {
        Book b;
        Visitor v;
        Transaction t;
        try {
            b = LBMS.getLastBookSearch().get(id - 1);
            t = new Transaction(b.getIsbn(), visitorID);
            v = UserSearch.BY_ID.findFirst(visitorID);
        } catch (Exception e) {
            return "id-error," + id;
        }

        if (v.canCheckOut()) {
            v.checkOut(t);
            b.checkOut();
            List<Transaction> transactions = LBMS.getTransactions();
            transactions.add(t);
            return t.getDueDate().format(SystemDateTime.DATE_FORMAT);
        }
        return "unknown-error";
    }
}
