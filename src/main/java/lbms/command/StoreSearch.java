package lbms.command;

import lbms.LBMS;
import lbms.models.Book;
import lbms.search.BookStoreSearch;
import java.util.ArrayList;
import java.util.Collections;
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
    public StoreSearch(String request) {
        int i = 1;
        authors = new ArrayList<>();
        String[] arguments = request.split(",");
        title = arguments[0];
        while(arguments.length > i && !arguments[i].matches("\\d+")) {
            authors.add(arguments[i]);
            i += 1;
        }
        if(arguments.length > i) {
            isbn = Long.parseLong(arguments[i]);
            i += 1;
        }
        if(arguments.length > i) {
            publisher = arguments[i];
            i += 1;
        }
        if(arguments.length > i) {
            sortOrder = arguments[i];
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
        List<Book> books = BookStoreSearch.BY_TITLE.search(title);
        List<Book> remove = new ArrayList<>();
        if(authors.size() > 0) {
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
        for(Book b: remove) {
            books.remove(b);
        }

        if(sortOrder != null && sortOrder.equals("title")) {
            Collections.sort(books, (Book b1, Book b2) -> b2.getTitle().compareTo(b1.getTitle()));
        }
        else if(sortOrder != null && sortOrder.equals("publish-date")) {
            Collections.sort(books, (Book b1, Book b2) -> b2.getPublishDate().compareTo(b1.getPublishDate()));
        }
        if(books.size() == 0) {
            return "0;";
        }
        else {
            int id = 1;
            String response = Integer.toString(books.size()) + "\n";
            LBMS.getLastBookSearch().clear();
            for(Book book : books) {
                LBMS.getLastBookSearch().add(book);
                response = response + id + "," + book.getIsbn() + "," + book.getTitle()
                + ",{";
                for(String author : book.getAuthors()) {
                    response = response + author + ",";
                }
                response = response.replaceAll(",$", "},");
                response = response + book.dateFormat() + "\n";
                id += 1;
            }
            response += ";";
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
        if(fields[1].equals("n;")) {
            return "No books were found.";
        }
        else {
            return fields[2];
        }
    }
}
