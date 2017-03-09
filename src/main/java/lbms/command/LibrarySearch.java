package lbms.command;

import lbms.models.Book;
import lbms.search.BookSearch;

import java.lang.reflect.Array;
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
        ArrayList<String> arguments = new ArrayList<>(Arrays.asList(request.split(",")));
        title = arguments.remove(0);
        int index;
        authors = new ArrayList<>();
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
        ArrayList<Book> antiMatches = new ArrayList<>();
        List<Book> matches = BookSearch.BY_TITLE.search(title);
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
                if(publisher != null) {
                    for(Book b: matches) {
                        if(!b.getPublisher().equals(publisher)) {
                            antiMatches.add(b);
                        }
                    }
                    for(Book b: antiMatches) {
                        matches.remove(b);
                    }
                    antiMatches.clear();
                }
            }
        }

        // create the return string
        String matchesString = "";
        for(Book b: matches) {
            matchesString += ('\n' + b.getCopiesAvailable() + "," + b.toString());
        }

        return matches.size() + matchesString;
    }

    /**
     * Parses the response for standard output.
     * @param response: the response string from execute
     * @return the output to be printed
     */
    @Override
    public String parseResponse(String response) {
        return null;    //TODO
    }
}
