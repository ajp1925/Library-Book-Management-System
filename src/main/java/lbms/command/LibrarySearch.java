package lbms.command;

import lbms.LBMS;
import lbms.models.Book;
import lbms.search.BookSearch;

import java.util.*;

/**
 * LibrarySearch class for the library search command.
 * @author Team B
 */
public class LibrarySearch implements Command {

    private String title, publisher = null, sort_order = null;
    private ArrayList<String> authors;
    private Long isbn = null;

    /**
     * Constructor for a LibrarySearch command object.
     * @param request: the request string for a library search
     * @throws MissingParametersException: missing parameters
     */
    public LibrarySearch(String request) throws MissingParametersException {
        String[] arguments = request.split(",");
        if (arguments.length == 0 || arguments.length == 1 && arguments[0].equals("")) {
            throw new MissingParametersException("missing-parameters,title,{authors}");
        }
        if(arguments.length == 1) {
            throw new MissingParametersException("missing-parameters,{authors}");
        }
        try {
            for(int index = 0; index < arguments.length; index++) {
                if(sort_order == null && (Arrays.asList(arguments).contains("title") ||
                    Arrays.asList(arguments).contains("publish-date") ||
                    Arrays.asList(arguments).contains("book-status"))) {
                    sort_order = arguments[arguments.length - 1];
                }
                if(arguments[index].startsWith("{")) {
                    authors = new ArrayList<>();
                    while(!arguments[index].endsWith("}")) {
                        authors.add(arguments[index++].replaceAll("[{}]", ""));
                    }
                    authors.add(arguments[index].replaceAll("[{}]", ""));
                }
                else if(!arguments[index].equals("*")) {
                    if(title == null && !arguments[0].equals("*")) {
                        title = (arguments[index]);
                    }
                    else if(isbn == null && arguments[index].matches("^\\d{13}$")) {
                        isbn = Long.parseLong(arguments[index]);
                    }
                    else if((publisher == null && sort_order == null && index == (arguments.length) - 1) ||
                               (publisher == null && sort_order != null && index == (arguments.length) - 2)) {
                        publisher = arguments[index];
                    }
                }
            }
        }
        catch(Exception e) {
            throw new MissingParametersException("unknown-error");
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
        List<Book> matches;
        List<Book> antiMatches = new ArrayList<>();
        if(title != null) {
            matches = BookSearch.BY_TITLE.search(title);
        }
        else if(authors != null) {
            matches = BookSearch.BY_AUTHOR.search(authors.get(0));
        }
        else if(isbn != null) {
            matches = BookSearch.BY_ISBN.search(isbn);
        }
        else if(publisher != null) {
            matches = BookSearch.BY_PUBLISHER.search(publisher);
        }
        else {
            matches = new ArrayList<>();
        }
        for(Book b: matches) {
            if(title != null && !b.getTitle().contains(title)) {
                antiMatches.add(b);
            }
            if(authors != null) {
                for(String author: authors) {
                    if(!b.hasAuthorPartial(author)) {
                        antiMatches.add(b);
                    }
                }
            }
            if(isbn != null && b.getIsbn() != isbn) {
                antiMatches.add(b);
            }
            if(publisher != null && !b.getPublisher().equals(publisher)) {
                antiMatches.add(b);
            }
        }
        for(Book b: antiMatches) {
            matches.remove(b);
        }

        if(sort_order != null) {
            switch(sort_order) {
                case "title":
                    Collections.sort(matches, (Book b1, Book b2) -> b2.getTitle().compareTo(b1.getTitle()));
                    break;
                case "publish-date":
                    Collections.sort(matches, (Book b1, Book b2) -> b2.getPublishDate().compareTo(b1.getPublishDate()));
                    break;
                case "book-status":
                    Collections.sort(matches, (Book b1, Book b2) ->
                            ((Integer)b2.getCopiesAvailable()).compareTo(b1.getCopiesAvailable()));
                    break;
            }
        }
        LBMS.getLastBookSearch().clear();
        String matchesString = "";
        for(Book b: matches) {
            LBMS.getLastBookSearch().add(b);
            matchesString += "\n" + b.getCopiesAvailable() + "," + (LBMS.getLastBookSearch().indexOf(b) + 1) + "," +
                    b.toString() + ",";
        }
        if(matches.size() > 0) {
            matchesString = matchesString.substring(0, matchesString.length() - 1);
        }
        else {
            return "0;";
        }

        return matches.size() + "," + matchesString + ";";
    }

    /**
     * Parses the response for standard output.
     * @param response: the response string from execute
     * @return the output to be printed
     */
    @Override
    public String parseResponse(String response) {
        String[] fields = response.replace(";", "").split("\n", 2);

        if (fields.length == 1) {
            if (fields[0].endsWith("0")) {
                return "No books match query.";
            } else {
                return response;
            }
        }
        else {
            return fields[1];
        }
    }
}