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
    private List<Integer> ids;

    /**
     * Constructor for a BookPurchase class.
     * @param request: the input string
     * @throws MissingParametersException: missing parameters
     */
    public BookPurchase(String request) throws MissingParametersException {
        try {
            ArrayList<String> arguments = new ArrayList<>(Arrays.asList(request.split(",")));
            quantity = Integer.parseInt(arguments.remove(0));
            ids = arguments.parallelStream().map(Integer::parseInt).collect(Collectors.toList());
        }
        catch(Exception e) {
            throw new MissingParametersException("missing-parameters,quantity,id[,ids]");
        }
    }

    /**
     * Executes the book purchase command.
     * @return a success message for the commandq
     */
    @Override
    public String execute() {
        if(ids.size() == 0) {
            return "missing-parameters,id;";
        }
        String s = processPurchaseOrder();
        if(s.equals("failure;")) {
            return s;
        }
        s = s.replaceAll(",$","");
        return "success," + s + ";";
    }

    /**
     * Parses the response for standard output.
     * @param response: the response string from execute
     * @return the output to be printed
     */
    @Override
    public String parseResponse(String response) {
        try {
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
                    catch(NumberFormatException e) {}
                }
                return output;
            }
            return null;
        }
        catch(Exception e) {
            return "failure;";
        }
    }

    /**
     * Buys *quantity* of each book listed in *ids*
     * @return a response string
     */
    private String processPurchaseOrder() {
        String booksBought = "";
        for(int id: ids) {
            Book b;
            try {
                b = LBMS.getLastBookSearch().get(id - 1);
            }
            catch(IndexOutOfBoundsException e) {
                return "failure;";
            }
            for(int i = 0; i < quantity; i++) {
                buyBook(b);
            }
            booksBought += ("\n" + b.toString() + "," + b.getNumberOfCopies()) + ",";
        }
        return ids.size() + booksBought;
    }

    /**
     * Buys a book for the library
     * @param book: The book to buy
     */
    private void buyBook(Book book) {
        book.purchase();
        if(!LBMS.getBooks().values().contains(book)) {
            LBMS.getBooks().put(book.getIsbn(), book);
        }
    }
}
