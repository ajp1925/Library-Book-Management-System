package lbms.command;

import lbms.LBMS;
import lbms.models.Book;
import lbms.search.BookSearch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * StoreSearch class that implements the book store search command.
 * @author Team B
 */
public class StoreSearch implements Command {

    private String title;
    private ArrayList<String> authors;
    private Long isbn = null;
    private String publisher = null;
    private String sortOrder = null;

    /**
     * Constructor for a StoreSearch object.
     * @param request: the request string to be read
     */
    public StoreSearch(String request) throws MissingParametersException {
        String[] arguments = request.split(",");
        if(arguments.length <= 0) {
            throw new MissingParametersException("missing-parameters,title");
        }
        try {
            for(int index = 0; index < arguments.length; index++) {
                if(sortOrder == null && (Arrays.asList(arguments).contains("title") ||
                        Arrays.asList(arguments).contains("publish-date"))) {
                    sortOrder = arguments[arguments.length - 1];
                }

                if(arguments[index].startsWith("{")) {
                    authors = new ArrayList<>();
                    while (!arguments[index].endsWith("}")) {
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
                    else if((publisher == null && sortOrder == null && index == (arguments.length) - 1) ||
                            (publisher == null && sortOrder != null && index == (arguments.length) - 2)) {
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
     * Executes the command for book store search.
     * @return a response or error string
     */
    @Override
    public String execute() {
        if(sortOrder != null && !sortOrder.equals("title") && !sortOrder.equals("publish-date")) {
            return "invalid-sort-order";
        }
        List<Book> books = BookSearch.BY_TITLE.toBuy().findAll(title);
        List<Book> remove = new ArrayList<>();
        if(authors != null) {
            for(Book b: books) {
                for(String author: authors) {
                    if(!b.hasAuthorPartial(author)) {
                        remove.add(b);
                    }
                }
            }
        }
        if(isbn != null) {
            for(Book b: books) {
                if(b.getIsbn() != isbn) {
                    remove.add(b);
                }
            }
        }
        if(publisher != null) {
            for(Book b: books) {
                if(!b.getPublisher().contains(publisher)) {
                    remove.add(b);
                }
            }
        }
        books.removeAll(remove);

        if(sortOrder != null && sortOrder.equals("title")) {
            books.sort((Book b1, Book b2) -> b2.getTitle().compareTo(b1.getTitle()));
        }
        else if(sortOrder != null && sortOrder.equals("publish-date")) {
            books.sort((Book b1, Book b2) -> b2.getPublishDate().compareTo(b1.getPublishDate()));
        }
        if(books.size() == 0) {
            return "0;";
        }
        else {
            int id = 1;
            StringBuilder response = new StringBuilder(Integer.toString(books.size()) + "\n");
            LBMS.getLastBookSearch().clear();
            for(Book book : books) {
                LBMS.getLastBookSearch().add(book);
                response.append(id).append(",")
                        .append(book.getIsbn()).append(",")
                        .append(book.getTitle()).append(",{");
                for(String author : book.getAuthors()) {
                    response.append(author).append(",");
                }
                response = new StringBuilder(response.toString().replaceAll(",$", "},"));
                response.append(book.dateFormat()).append(",\n");
                id += 1;
            }
            response = new StringBuilder(response.substring(0, response.length() - 2));
            response.append(";");
            return response.toString();
        }
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
