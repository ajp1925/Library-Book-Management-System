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
    // TODO check functionality with *

    private String title, publisher = null, sort_order = null;
    private ArrayList<String> authors;
    private Long isbn = null;
    private static final Set<String> SORTS = new HashSet<>(Arrays.asList(
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
            arguments = new ArrayList<>(Arrays.asList(request.split("\\{")));
            if(arguments.size() <= 1) {
                System.out.println("2");
                throw new MissingParametersException("missing-parameters,{authors}");
            }
            String[] authors = arguments.get(1).split("}");
            if(authors.length < 1) {
                System.out.println("1");
                throw new MissingParametersException("missing-parameters,{authors}");
            }
            authors = authors[0].split(",");
            for(index = 0; index < authors.length; index++) {
                if(authors[index].equals("*")) {
                    this.authors = null;
                    break;
                }
                this.authors.add(authors[index]);
                index++;
            }
            arguments = new ArrayList<>(Arrays.asList(request.split("}")));
            if(arguments.size() > 1) {
                arguments = new ArrayList<>(Arrays.asList(arguments.get(1).split(",")));
                if(arguments.get(0).matches("\\d*")) {
                    isbn = Long.parseLong(arguments.remove(0));
                    publisher = arguments.get(0);
                }
                else {
                    if(arguments.get(0).matches("\\*")) {
                        isbn = null;
                        publisher = arguments.get(0);
                    }
                    else {
                        throw new MissingParametersException("missing-parameters,isbn");
                    }
                }
            }
        }
        catch(ArrayIndexOutOfBoundsException e) {
            System.out.println("3");
            throw new MissingParametersException("missing-parameters,title,{authors}");
        }
        catch(MissingParametersException e) {
            throw new MissingParametersException(e.getMessage());
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