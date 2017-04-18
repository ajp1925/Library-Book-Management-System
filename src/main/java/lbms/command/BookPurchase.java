package lbms.command;

import lbms.LBMS;
import lbms.models.Book;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * BookPurchase class that implements the book purchase command.
 * @author Team B
 */
public class BookPurchase implements Undoable {

    private int quantity;
    private List<Integer> ids;
    private long clientID;

    /**
     * Constructor for a BookPurchase class.
     * @param request: the input string
     * @throws MissingParametersException: missing parameters
     */
    public BookPurchase(long clientID, String request) throws MissingParametersException {
        try {
            this.clientID = clientID;
            ArrayList<String> arguments = new ArrayList<>(Arrays.asList(request.split(",")));
            this.quantity = Integer.parseInt(arguments.remove(0));
            this.ids = arguments.parallelStream().map(Integer::parseInt).collect(Collectors.toList());
            if (this.ids.size() == 0) {
                throw new MissingParametersException("missing-parameters,quantity,id[,ids];");
            }
        } catch (Exception e) {
            throw new MissingParametersException("missing-parameters,quantity,id[,ids];");
        }
    }

    /**
     * Executes the book purchase command.
     * @return a success message for the commandq
     */
    @Override
    public String execute() {
        if (this.ids.size() == 0) {
            return ",missing-parameters,id;";
        }
        String s = processPurchaseOrder();
        if (s.equals(",failure;")) {
            return s;
        }
        s = s.replaceAll(",$","");
        return ",success," + s + ";";
    }

    /**
     * Un-executes the command.
     * @return null if successful, a string if it failed
     */
    @Override
    public String unExecute() {
        // TODO test this
        for (int id : this.ids) {
            Book b;
            b = LBMS.getSessions().get(this.clientID).getBookSearch().get(id - 1);
            for (int i = 0; i < this.quantity; i++) {
                b.undoPurchase();
            }
            if (LBMS.getBooks().get(b.getIsbn()).getNumberOfCopies() <= 0) {
                LBMS.getBooks().remove(b.getIsbn());
            }
        }
        return null;
    }

    /**
     * Buys *quantity* of each book listed in *ids*
     * @return a response string
     */
    private String processPurchaseOrder() {
        String booksBought = "";
        for (int id: this.ids) {
            Book b;

            try {
                b = LBMS.getSessions().get(this.clientID).getBookSearch().get(id - 1);
            } catch (IndexOutOfBoundsException e) {
                return ",failure;";
            }

            for (int i = 0; i < this.quantity; i++) {
                buyBook(b);
            }

            booksBought += ("\n" + b.toString() + "," + this.quantity) + ",";
        }
        return this.ids.size() + booksBought;
    }

    /**
     * Buys a book for the library
     * @param book: The book to buy
     */
    private void buyBook(Book book) {
        book.purchase();
        if (!LBMS.getBooks().values().contains(book)) {
            LBMS.getBooks().put(book.getIsbn(), book);
        }
    }
}
