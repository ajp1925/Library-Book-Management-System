package lbms.command;

import lbms.LBMS;
import lbms.models.Book;
import lbms.models.ISBN;
import lbms.search.BookSearch;
import lbms.search.GoogleAPISearch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static lbms.LBMS.SearchService.GOOGLE;
import static lbms.LBMS.SearchService.LOCAL;

/**
 * StoreSearch class that implements the book store search command.
 * @author Team B
 */
public class StoreSearch implements Command {

    private long clientID;
    private String title;
    private ArrayList<String> authors;
    private ISBN isbn;
    private String publisher = null;
    private String sortOrder = null;

    /**
     * Constructor for a StoreSearch object.
     * @param clientID: the clientID
     * @param request: the request string to be parsed
     * @throws MissingParametersException: when the request format is invalid
     */
    public StoreSearch(long clientID, String request) throws MissingParametersException {
        this.clientID = clientID;
        String[] arguments = request.split(",");
        if (arguments.length == 1 && arguments[0].equals("")) {
            throw new MissingParametersException("missing-parameters,title;");
        }
        try {
            for (int index = 0; index < arguments.length; index++) {
                if (this.sortOrder == null && (Arrays.asList(arguments).contains("title") ||
                        Arrays.asList(arguments).contains("publish-date"))) {
                    this.sortOrder = arguments[arguments.length - 1];
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
                    } else if ((this.publisher == null && this.sortOrder == null && index == (arguments.length) - 1) ||
                            (this.publisher == null && this.sortOrder != null && index == (arguments.length) - 2)) {
                        this.publisher = arguments[index];
                    }
                }
            }
        } catch (Exception e) {
            throw new MissingParametersException("unknown-error");
        }
    }

    /**
     * Executes the command for book store search.
     * @return a response or error string
     */
    @Override
    public String execute() {
        if (this.sortOrder != null && !this.sortOrder.equals("title") && !this.sortOrder.equals("publish-date")) {
            return "invalid-sort-order";
        }

        List<Book> books;
        if (LBMS.getSessions().get(this.clientID).getSearch() == LOCAL) {
            if (this.title != null) {
                books = BookSearch.BY_TITLE.toBuy().findAll(this.title);
            } else if (this.authors != null) {
                books = BookSearch.BY_AUTHOR.toBuy().findAll(this.authors.get(0));
            } else if (this.isbn != null) {
                books = BookSearch.BY_ISBN.toBuy().findAll(isbn.toString());
            } else if (this.publisher != null) {
                books = BookSearch.BY_PUBLISHER.toBuy().findAll(this.publisher);
            } else {
                books = new ArrayList<>();
            }
        } else if (LBMS.getSessions().get(clientID).getSearch() == GOOGLE) {
            if (this.title != null) {
                books = GoogleAPISearch.searchByTitle(this.title);
            } else if (this.authors != null) {
                books = GoogleAPISearch.searchByAuthor(this.authors);
            } else if (this.isbn != null) {
                books = GoogleAPISearch.searchByISBN(this.isbn.toString());
            } else if (this.publisher != null) {
                books = GoogleAPISearch.searchByPublisher(this.publisher);
            } else {
                books = new ArrayList<>();
            }
        } else {
            books = new ArrayList<>();
        }

        List<Book> remove = new ArrayList<>();
        if (this.authors != null) {
            for (Book b: books) {
                for (String author: this.authors) {
                    if (!b.hasAuthorPartial(author)) {
                        remove.add(b);
                    }
                }
            }
        }
        if (this.isbn != null) {
            for (Book b: books) {
                if (b.getIsbn() != this.isbn) {
                    remove.add(b);
                }
            }
        }
        if (this.publisher != null) {
            for (Book b: books) {
                if (!b.getPublisher().contains(this.publisher)) {
                    remove.add(b);
                }
            }
        }
        books.removeAll(remove);

        if (this.sortOrder != null && this.sortOrder.equals("title")) {
            books.sort((Book b1, Book b2) -> b2.getTitle().compareTo(b1.getTitle()));
        } else if (this.sortOrder != null && this.sortOrder.equals("publish-date")) {
            books.sort((Book b1, Book b2) -> b2.getPublishDate().compareTo(b1.getPublishDate()));
        }

        if (books.size() == 0) {
            return ",0;";
        } else {
            int id = 1;
            StringBuilder response = new StringBuilder(Integer.toString(books.size()) + ",\n");
            LBMS.getLastBookSearch().clear();
            for (Book book: books) {
                LBMS.getLastBookSearch().add(book);
                response.append(id).append(",")
                        .append(book.getIsbn()).append(",")
                        .append(book.getTitle()).append(",{");
                for (String author: book.getAuthors()) {
                    response.append(author).append(",");
                }
                response = new StringBuilder(response.toString().replaceAll(",$", "},"));
                response.append(book.dateFormat()).append(",");
                response.append(book.getPageCount()).append(",\n");
                id += 1;
            }
            response = new StringBuilder(response.substring(0, response.length() - 2));
            response.append(";");
            return "," + response.toString();
        }
    }
}
