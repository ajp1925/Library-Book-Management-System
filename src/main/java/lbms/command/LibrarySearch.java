package lbms.command;

import lbms.LBMS;
import lbms.models.Book;
import lbms.models.ISBN;
import lbms.search.BookSearch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * LibrarySearch class for the library search command.
 * @author Team B TODO -> update for R2
 */
public class LibrarySearch implements Command {

    private String title, publisher = null, sort_order = null;
    private ArrayList<String> authors;
    private ISBN isbn;

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
        if (arguments.length == 1) {
            throw new MissingParametersException("missing-parameters,{authors}");
        }
        try {
            for (int index = 0; index < arguments.length; index++) {
                if (this.sort_order == null && (Arrays.asList(arguments).contains("title") ||
                    Arrays.asList(arguments).contains("publish-date") ||
                    Arrays.asList(arguments).contains("book-status"))) {
                    this.sort_order = arguments[arguments.length - 1];
                }
                if (arguments[index].startsWith("{")) {
                    this.authors = new ArrayList<>();
                    while (!arguments[index].endsWith("}")) {
                        this.authors.add(arguments[index++].replaceAll("[{}]", ""));
                    }
                    this.authors.add(arguments[index].replaceAll("[{}]", ""));
                } else if (!arguments[index].equals("*")) {
                    if (this.title == null && !arguments[0].equals("*")) {
                        this.title = (arguments[index]);
                    } else if (this.isbn == null && arguments[index].matches("^\\d{13}$")) {
                        this.isbn = new ISBN(arguments[index]);
                    } else if ((this.publisher == null && this.sort_order == null && index == (arguments.length) - 1) ||
                               (this.publisher == null && this.sort_order != null && index == (arguments.length) - 2)) {
                        this.publisher = arguments[index];
                    }
                }
            }
        } catch (Exception e) {
            throw new MissingParametersException("unknown-error");
        }
    }

    /**
     * Executes the library search command.
     * @return a response string or error message
     */
    @Override
    public String execute() {
        if (this.sort_order != null && !this.sort_order.equals("title") && !this.sort_order.equals("publish-date") &&
                !this.sort_order.equals("book-status")) {
            return "invalid-sort-order;";
        }
        List<Book> matches;
        List<Book> antiMatches = new ArrayList<>();
        if (this.title != null) {
            matches = BookSearch.BY_TITLE.findAll(this.title);
        } else if (this.authors != null) {
            matches = BookSearch.BY_AUTHOR.findAll(this.authors.get(0));
        } else if (this.isbn != null) {
            matches = BookSearch.BY_ISBN.findAll(isbn.toString());
        } else if (this.publisher != null) {
            matches = BookSearch.BY_PUBLISHER.findAll(this.publisher);
        } else {
            matches = new ArrayList<>();
        }

        for (Book b: matches) {
            if (this.title != null && !b.getTitle().contains(this.title)) {
                antiMatches.add(b);
            }
            if (!LBMS.getBooks().containsKey(b.getIsbn())) {
                antiMatches.add(b);
            }
            if (this.authors != null) {
                for (String author: this.authors) {
                    if (!b.hasAuthorPartial(author)) {
                        antiMatches.add(b);
                    }
                }
            }
            if (this.isbn != null && b.getIsbn() != this.isbn) {
                antiMatches.add(b);
            }
            if (this.publisher != null && !b.getPublisher().equals(this.publisher)) {
                antiMatches.add(b);
            }
        }
        matches.removeAll(antiMatches);

        if (this.sort_order != null) {
            switch (this.sort_order) {
                case "title":
                    matches.sort((Book b1, Book b2) -> b2.getTitle().compareTo(b1.getTitle()));
                    break;
                case "publish-date":
                    matches.sort((Book b1, Book b2) -> b2.getPublishDate().compareTo(b1.getPublishDate()));
                    break;
                case "book-status":
                    matches.sort((Book b1, Book b2) ->
                            ((Integer) b2.getCopiesAvailable()).compareTo(b1.getCopiesAvailable()));
                    break;
            }
        }
        LBMS.getLastBookSearch().clear();
        StringBuilder matchesString = new StringBuilder();
        for (Book b: matches) {
            LBMS.getLastBookSearch().add(b);
            matchesString.append("\n")
                    .append(b.getCopiesAvailable()).append(",")
                    .append(LBMS.getLastBookSearch().indexOf(b) + 1).append(",")
                    .append(b.toString()).append(",");
        }
        if (matches.size() > 0) {
            matchesString = new StringBuilder(matchesString.substring(0, matchesString.length() - 1));
        } else {
            return ",0;";
        }

        return "," + matches.size() + "," + matchesString + ";";
    }
}