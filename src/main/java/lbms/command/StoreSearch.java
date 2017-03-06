package lbms.command;

import java.util.ArrayList;

/**
 * Created by anthony on 3/5/17.
 */
public class StoreSearch implements Command {

    private String title;
    private ArrayList<String> authors;
    private Long isbn;
    private String publisher;
    private String sortOrder;

    public StoreSearch(String request) {
        int i = 1;
        request = request.replaceAll(";$", "");
        String[] arguments = request.split(",");
        title = arguments[0];
        while (!arguments[i].matches("\\d+")) {
            authors.add(arguments[i]);
            i += 1;
        }
        isbn = Long.parseLong(arguments[i]);
        i += 1;
        publisher = arguments[i];
        i += 1;
        sortOrder = arguments[i];
    }

    @Override
    public String execute() {
        // TODO Edward
        if (sortOrder != "title" && sortOrder != "publish-date") {
            return "invalid-sort-order";
        }
        return "";
    }

}
