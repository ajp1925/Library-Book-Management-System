package lbms.command;

import lbms.API;
import lbms.models.Book;
import lbms.search.SearchByTitle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by anthony on 3/5/17.
 */
public class StoreSearch implements Command {

    private String title;
    private ArrayList<String> authors;
    private Long isbn;
    private String publisher;
    private String sortOrder;

    public StoreSearch(String request) {
        int i = 1;
        request = request.replaceAll(";$", "");
        String[] arguments = request.split(",");
        title = arguments[0];
        while (!arguments[i].matches("\\d+")) {
            authors.add(arguments[i]);
            i += 1;
        }
        isbn = Long.parseLong(arguments[i]);
        i += 1;
        publisher = arguments[i];
        i += 1;
        sortOrder = arguments[i];
    }

    @Override
    public String execute() {
        // TODO Edward
        if (sortOrder != "title" && sortOrder != "publish-date") {
            return "invalid-sort-order";
        }
        int booksFound = 0;
        SearchByTitle search = new SearchByTitle(title);
        List<Book> books = API.findBooks(search);

        if (books.size() == 0) {
            return Integer.toString(booksFound) + ";";
        }
        else {
            int id = 1;
            String response = Integer.toString(books.size()) + "\n";
            for (Book book : books) {
                response = response + Integer.toString(id) + "," + book.getIsbn() + "," + book.getTitle()
                + ",{";
                for (String author : book.getAuthors()) {
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
