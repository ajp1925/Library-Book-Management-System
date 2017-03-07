package lbms.command;

import lbms.API;
import lbms.models.Book;
import lbms.search.SearchByTitle;
import java.util.ArrayList;
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
        request = request.replaceAll(";$", "");
        String[] arguments = request.split(",");
        title = arguments[0];
        while(!arguments[i].matches("\\d+")) {
            authors.add(arguments[i]);
            i += 1;
        }
        isbn = Long.parseLong(arguments[i]);
        i += 1;
        publisher = arguments[i];
        i += 1;
        sortOrder = arguments[i];
    }

    /**
     * Executes the command for book store search.
     * @return a response or error string
     */
    @Override
    public String execute() {
        if(sortOrder.equals("title") && sortOrder.equals("publish-date")) {
            return "invalid-sort-order";
        }
        int booksFound = 0;
        SearchByTitle search = new SearchByTitle(title);
        List<Book> books = API.findBooks(search);

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
}
