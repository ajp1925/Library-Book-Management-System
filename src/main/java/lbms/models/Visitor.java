package lbms.models;

import java.util.HashMap;
import java.io.Serializable;

/**
 * Class for a Visitor object, used in the library book management system.
 */
public class Visitor implements Serializable {

    private String firstName, lastName;
    private String address;     // PLACEHOLDER type address QUESTION: can we use external address and phone number class
    private int phoneNumber;    // PLACEHOLDER type phonenumber
    private long visitorID;
    private HashMap<Long, Transaction> checkedOutBooks;
    private final int MAX_BOOKS = 5;
    private boolean inLibrary;
    private static long systemIDs = 0;
    private double fines;

    /**
     * Constructor for a Visitor object.
     * @param firstName: the first name of the visitor
     * @param lastName: the last name of the visitor
     * @param address: the address of the visitor
     * @param phoneNumber: the visitor's phone number
     */
    public Visitor(String firstName, String lastName, String address, int phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.visitorID = ++systemIDs;
        this.checkedOutBooks = new HashMap<Long, Transaction>(MAX_BOOKS);
        this.inLibrary = false;
        fines = 0;
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
     * Getter for the visitors name.
     * @return the first and last name combined
     */
    public String getName() {
        return firstName + " " + lastName;
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
    public long getVisitorID() {
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
     * Getter for the checked out books
     * @return the checked out books
     */
    public HashMap<Long, Transaction> getCheckedOutBooks() {
        return checkedOutBooks;
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

    /**
     * Getter for the status of the visitor.
     * @return true if the visitor is in the library, false if not
     */
    public boolean getInLibrary() {
        return inLibrary;
    }

    /**
     * Changes the in library status of a visitor.
     * @param status: a boolean of the status of a visitor
     */
    void switchInLibrary(boolean status) {
        inLibrary = status;
    }

    /**
     * Determines the fines the visitor owes.
     * @return the amount of fines due
     */
    public double getFines() {
        double fines = 0;
        for(Long l: checkedOutBooks.keySet()) {
            fines += checkedOutBooks.get(l).getFine();
        }
        this.fines = fines;
        return fines;
    }

    /**
     * Makes a payment to the library.
     * @param amount: the amount of fines to pay
     */
    public void payFines(double amount) {
        fines -= amount;
    }
}
