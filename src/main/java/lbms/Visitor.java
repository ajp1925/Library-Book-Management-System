package lbms;

import java.util.HashMap;

/**
 * Class for a Visitor object, used in the library book management system.
 */
public class Visitor implements java.io.Serializable {

    private String firstName, lastName;
    private String address;     // PLACEHOLDER type address QUESTION: can we use external address and phone number class
    private int phoneNumber;    // PLACEHOLDER type phonenumber
    private int visitorID;
    private HashMap<Integer, Transaction> checkedOutBooks;
    private final int MAX_BOOKS = 5;

    /**
     * Constructor for a Visitor object.
     * @param firstName: the first name of the visitor
     * @param lastName: the last name of the visitor
     * @param address: the address of the visitor
     * @param phoneNumber: the visitor's phone number
     * @param visitorID: an ID generated for the visitor
     */
    public Visitor(String firstName, String lastName, String address, int phoneNumber, int visitorID) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.visitorID = visitorID;
        this.checkedOutBooks = new HashMap<Integer, Transaction>(5);
    }

    /**
     * Getter for the visitors first name.
     * @return the visitors first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Getter for the visitors last name.
     * @return the visitors last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Getter for the visitors address.
     * @return the visitors address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Getter for the visitors phone number.
     * @return the visitors phone number
     */
    public int getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Getter for the visitors ID.
     * @return the visitors ID
     */
    public int getVisitorID() {
        return visitorID;
    }

    /**
     * Getter for the number of books the visitor has checked out.
     * @return the number of checked out books
     */
    public int getNumCheckedOut() {
        return checkedOutBooks.size();
    }

    /**
     * Determines if a visitor can check out a book.
     * @return true if the number of checked out books is less than the max
     */
    public boolean canCheckOut() {
        return checkedOutBooks.size() < MAX_BOOKS;
    }

    /**
     * Checks out a book for a visitor.
     * @param transaction: the transaction for the checked out book
     */
    public void checkOut(Transaction transaction) {
        if (canCheckOut()) {
            checkedOutBooks.put(transaction.getIsbn(), transaction);
        }
    }

    /**
     * Returns a book for a visitor.
     * @param transaction: the transaction created when the book was checked out
     */
    public void returnBook(Transaction transaction) {
        checkedOutBooks.remove(transaction.getIsbn());
    }
}
