package lbms.command;

import lbms.API;
import lbms.models.Book;
import lbms.search.Search;
import lbms.search.SearchByTitle;
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
    public LibrarySearch(String request) {
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
            // index++; uncomment this if additional parameters are used
        }
        else {
            sort_order = null;
        }
    }

    /**
     * Executes the library search command.
     * @return a response string or error message
     */
    @Override
    public String execute() {
        if(sort_order != null && !sort_order.equals("title") && !sort_order.equals("publish-date") &&
                !sort_order.equals("book-status")) {
            return "invalid-sort-order;";
        }
        Search s = new SearchByTitle(title);
        ArrayList<Book> antiMatches = new ArrayList<>();
        List<Book> matches = API.findBooks(s);
        if(authors.size() > 0) {
            for(Book b: matches) {
                for(String author: authors) {
                    if(!b.hasAuthorPartial(author)) {
                        antiMatches.add(b);
                    }
                }
            }
            for(Book b: antiMatches) {
                matches.remove(b);
            }
            antiMatches.clear();
            if(isbn != null) {
                for(Book b: matches) {
                    if(!isbn.equals(b.getIsbn())) {
                        antiMatches.add(b);
                    }
                }
                for(Book b: antiMatches) {
                    matches.remove(b);
                }
                antiMatches.clear();
                // TODO check publisher and sort order
            }
            else {
                // TODO return all matches
            }
        }
        else {
            // TODO return all matches
        }
        return null; // TODO remove this
    }
}
