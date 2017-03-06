package lbms.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * LibrarySearch class for the library search command.
 * @author Team B
 */
public class LibrarySearch implements Command {

    private String title, publisher, sort_order;
    private ArrayList<String> authors;
    private Long isbn;

    /**
     * Constructor for a LibrarySearch command object.
     * @param request: the request string for a library search
     */
    public LibrarySearch(String request) { // TODO throw invalid argument exception
        request = request.replaceAll(";$", "");
        List<String> arguments = Arrays.asList(request.split(","));
        title = arguments.remove(0);
        int index;

        for(index = 0; index < arguments.size(); index++ ) {
            if(!arguments.get(index).matches("[0-9]+")) {
                authors.add(arguments.get(index));
            }
            else {
                isbn = Long.valueOf(arguments.get(index));
                break;
            }
        }

        if(index < arguments.size()) {
            publisher = arguments.get(index);
            index++;
        }
        if(index < arguments.size()) {
            sort_order = arguments.get(index);
            index++;
        }
    }

    /**
     * Executes the library search command.
     * @return a response string or error message
     */
    @Override
    public String execute() {
        // TODO Charles
        // Note: id is actually the isbn
        return "NOT DONE";
    }

}
