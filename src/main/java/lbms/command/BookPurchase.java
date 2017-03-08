package lbms.command;

import lbms.API;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * BookPurchase class that implements the book purchase command.
 * @author Team B
 */
public class BookPurchase implements Command {

    private int quantity;
    private List<Long> ids;

    /**
     * Constructor for a BookPurchase class.
     * @param request: the input string
     */
    public BookPurchase(String request) {
        request = request.replaceAll(";$", "");
        ArrayList<String> arguments = new ArrayList<>(Arrays.asList(request.split(",")));
        quantity = Integer.parseInt(arguments.remove(0));
        ids = arguments.parallelStream().map(Long::parseLong).collect(Collectors.toList());
    }

    /**
     * Executes the book purchase command.
     * @return a success message for the command
     */
    @Override
    public String execute() {
        return "success," + API.processPurchaseOrder(quantity, ids) + ";";
    }

    @Override
    public String parseResponse(String response) {
        return null;    //TODO
    }
}
