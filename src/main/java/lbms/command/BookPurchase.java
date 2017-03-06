package lbms.command;

import lbms.API;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by anthony on 3/5/17.
 */
public class BookPurchase implements Command {

    private int quantity;
    private List<Integer> ids; // Integer dependent on temporary id from search implementation

    public BookPurchase(String request) { // TODO throw invalid arg exception
        request = request.replaceAll(";$", "");
        List<String> arguments = Arrays.asList(request.split(","));
        quantity = Integer.parseInt(arguments.remove(0));
        ids = arguments.parallelStream().map(Integer::parseInt).collect(Collectors.toList());
    }

    @Override
    public String execute() {
        return API.processPurchaseOrder(quantity, ids);
    }

}
