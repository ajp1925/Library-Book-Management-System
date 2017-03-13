package lbms.command;

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
    private static final Set<String> SORTS = new HashSet<String>(Arrays.asList(
            new String[] {"title", "publish-date", "book-status"}
    ));

    /**
     * Constructor for a LibrarySearch command object.
     * @param request: the request string for a library search
     * @throws MissingParametersException: missing parameters
     */
    public LibrarySearch(String request) throws MissingParametersException {
        ArrayList<String> arguments = new ArrayList<>(Arrays.asList(request.split(",")));
        try {
            title = arguments.remove(0);
            if(title.equals("*")) {
                title = null;
            }
            Integer i = null;
            for(String s: arguments) {
                if(SORTS.contains(s)) {
                    i = arguments.indexOf(s);
                    sort_order = s;
                }
            }
            if(i != null) {
                arguments.remove((int)i);
            }
            int index;
            authors = new ArrayList<>();
            for(index = 0; index < arguments.size(); index++) {
                if(!arguments.get(index).matches("[0-9]+") && !arguments.get(index).equals("*")) {
                    authors.add(arguments.get(index));
                }
                else {
                    index++;
                    break;
                }
            }
            if(index < arguments.size()) {
                if(!arguments.get(index).equals("*")) {
                    isbn = Long.valueOf(arguments.get(index));
                }
                index++;
            }
            if(index < arguments.size()) {
                if(!arguments.get(index).equals("*")) {
                    publisher = arguments.get(index);
                }
            }
            if(arguments.size() > 0 && SORTS.contains(arguments.get(arguments.size() - 1))) {
                sort_order = arguments.get(arguments.size() - 1);
            }
        }
        catch(ArrayIndexOutOfBoundsException e) {
            throw new MissingParametersException("missing-parameters,title");
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
        else if(authors.size() > 0) {
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
            if(authors.size() > 0) {
                for (String author : authors) {
                    if (!b.hasAuthorPartial(author)) {
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

        String matchesString = "";
        for(Book b: matches) {
            matchesString += "\n" + b.getCopiesAvailable() + "," + b.toString() + ",";
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
        String[] fields = response.split(",");
        if(fields.length > 2) {
            return Arrays.toString(Arrays.copyOfRange(fields, 2, fields.length - 1));
        }
        else {
            return "No books match query.";
        }
    }
}