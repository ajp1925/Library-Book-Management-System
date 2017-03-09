package lbms.command;

import lbms.LBMS;
import lbms.models.Book;
import lbms.search.BookSearch;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * BookPurchase class that implements the book purchase command.
 * @author Team B
 */
public class BookPurchase implements Command {

    private int quantity;
    private List<Long> ids;

    /**
     * Constructor for a BookPurchase class.
     * @param request: the input string
     */
    public BookPurchase(String request) {
        ArrayList<String> arguments = new ArrayList<>(Arrays.asList(request.split(",")));
        quantity = Integer.parseInt(arguments.remove(0));
        ids = arguments.parallelStream().map(Long::parseLong).collect(Collectors.toList());
    }

    /**
     * Executes the book purchase command.
     * @return a success message for the command
     */
    @Override
    public String execute() {
        return "success," + processPurchaseOrder(quantity, ids) + ";";
    }

    /**
     * Parses the response for standard output.
     * @param response: the response string from execute
     * @return the output to be printed
     */
    @Override
    public String parseResponse(String response) {
        response = response.replaceAll(";$", "");
        String[] fields = response.split(",");
        if(fields[1].equals("success")) {
            String output = "Book(s) purchased, ";
            List<Book> books;
            for(int i = 2; i < fields.length; i++) {
                try {
                    books = BookSearch.BY_ISBN.search(Long.parseLong(fields[i]));
                    output += books.get(0).getTitle() + " * " + fields[1] + "\n";
                }
                catch(NumberFormatException e) { }
            }
            return output;
        }
        return null;
    }

    /**
     * Buys *quantity* of each book listed in *ids*
     * @param quantity: the quantity of books to be purchased
     * @param ids: the ids of the books to be purchased
     * @return a response string
     */
    private String processPurchaseOrder(int quantity, List<Long> ids) {
        String booksBought = "";
        for(Long id: ids) {
            for(Book b: LBMS.getBooksToBuy()) {
                if(b.getTempID() == id) {
                    for(int i = quantity; i > 0; i--) {
                        buyBook(b);
                    }
                    booksBought += ("\n" + b.toString() + "," + Integer.toString(quantity));
                    break;
                }
            }
        }
        return (quantity * ids.size()) + booksBought;
    }

    /**
     * Buys a book for the library
     * @param book: The book to buy
     */
    private void buyBook(Book book) {
        LBMS.getBooks().put(book.getIsbn(), book);
    }
}
