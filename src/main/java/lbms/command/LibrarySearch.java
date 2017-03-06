package lbms.command;

import lbms.API;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by anthony on 3/5/17.
 */
public class LibrarySearch implements Command {

    String title;
    ArrayList<String> authors;
    Long isbn;
    String publisher;
    String sort_order;

    public LibrarySearch(String request) { // TODO throw invalid argument exception
        request = request.replaceAll(";$", "");
        List<String> arguments = Arrays.asList(request.split(","));

        title = arguments.remove(0);

        int index = 0;
        for( ; index < arguments.size(); index++ ) {
            if( !arguments.get(index).matches("[0-9]+") ) {
                authors.add(arguments.get(index));
            }
            else {
                isbn = Long.valueOf(arguments.get(index));
                break;
            }
        }

        if( index < arguments.size() ) {
            publisher = arguments.get(index);
            index++;
        }
        if( index < arguments.size() ) {
            sort_order = arguments.get(index);
            index++;
        }

    }

    @Override
    public String execute() {
        // TODO Charles
        // Note: id is actually the isbn
        return "NOT DONE";
    }

}
