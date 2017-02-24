package lbms;

/**
 * Main class to run the Library Book Management System.
 * @author Team B
 */
public class LBMS {

    /**
     * Main method.
     * @param args: the program arguments
     */
    public static void main(String[] args) {
        System.out.println("Hello World!");
    }


    /**
     * Executes a checkout of a book by a visitor
     *
     * @param visitor the visitor checking out the book
     * @param book the book being checked out
     * @return a Transaction if a successful Transaction is made, else return null
     */
    public Transaction checkOut(Visitor visitor, Book book) {
        if (book.canCheckOut() && visitor.canCheckOut()) {
            Transaction transaction = new Transaction(book.getIsbn(), visitor.getVisitorID(), null, null);       // PLACEHOLDER dates
            book.checkOut();
            visitor.checkOut(transaction);
            return transaction;
        }

        return null;
    }
}
