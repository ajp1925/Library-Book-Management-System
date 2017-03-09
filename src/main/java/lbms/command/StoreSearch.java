package lbms.command;

import lbms.models.Book;
import lbms.search.BookSearch;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * StoreSearch class that implements the book store search command.
 * @author Team B
 */
public class StoreSearch implements Command {

    private String title;
    private ArrayList<String> authors;
    private Long isbn;
    private String publisher;
    private String sortOrder;

    /**
     * Constructor for a StoreSearch object.
     * @param request: the request string to be read
     */
    public StoreSearch(String request) {
        int i = 1;
        authors = new ArrayList<>();
        String[] arguments = request.split(",");
        title = arguments[0];
        if (arguments.length > i) {
            while (!arguments[i].matches("\\d+")) {
                authors.add(arguments[i]);
                i += 1;
            }
        }
        if (arguments.length > i) {
            isbn = Long.parseLong(arguments[i]);
            i += 1;
        }
        if (arguments.length > i) {
            publisher = arguments[i];
            i += 1;
        }
        if (arguments.length > i) {
            sortOrder = arguments[i];
        }
    }

    /**
     * Executes the command for book store search.
     * @return a response or error string
     */
    @Override
    public String execute() {
        int booksFound = 0;
        List<Book> books = BookSearch.BY_TITLE.search(title);
        if (sortOrder != null) {
            if (!sortOrder.equals("title") && !sortOrder.equals("publish-date")) {
                return "invalid-sort-order";
            }
            if(sortOrder.equals("title")) {
                Collections.sort(books);
            }
            else if(sortOrder.equals("publish-date")) {
                Collections.sort(books, new Comparator<Book>() {
                    @Override
                    public int compare(Book book1, Book book2) {
                        return book2.getPublishDate().compareTo(book1.getPublishDate());
                    }
                });
            }
        }
        if(books.size() == 0) {
            return Integer.toString(booksFound) + ";";
        }
        else {
            int id = 1;
            String response = Integer.toString(books.size()) + "\n";
            for(Book book : books) {
                response = response + Integer.toString(id) + "," + book.getIsbn() + "," + book.getTitle()
                + ",{";
                for(String author : book.getAuthors()) {
                    response = response + author + ",";
                }
                response = response.replaceAll(",$", "},");
                response = response + book.getPublishDate() + "\n";
                response = response.replaceAll("\\n$", ";");
                id += 1;
            }
            return response;
        }
    }

    /**
     * Parses the response for standard output.
     * @param response: the response string from execute
     * @return the output to be printed
     */
    @Override
    public String parseResponse(String response) {
        String[] fields = response.split(",");
        if (fields[1].equals("0;")) {
            return "No books were found.";
        }
        else {
            String string = "";
            for (int i = 2; i < fields.length; i++) {
                string = string + fields[i];
            }
            return string;
        }
    }
}
