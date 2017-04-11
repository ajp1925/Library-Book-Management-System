package lbms.models;

import lbms.LBMS;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Class for a Visitor object, used in the library book management system.
 * @author Team B
 */
public class Visitor implements Serializable {

    private String firstName, lastName;
    private String username;
    private String password;
    private String address;
    private PhoneNumber phoneNumber;
    private long visitorID;
    private HashMap<ISBN, Transaction> checkedOutBooks;
    private HashMap<ISBN, Transaction> previousCheckedOutBooks;
    private final int MAX_BOOKS = 5;
    private boolean inLibrary;
    private double currentFines;
    private double totalFines;
    private double payedFines;

    /**
     * Constructor for a Visitor object.
     * @param firstName: the first name of the visitor
     * @param lastName: the last name of the visitor
     * @param address: the address of the visitor
     * @param phoneNumber: the visitor's phone number
     */
    public Visitor(String firstName, String lastName, String username, String password, String address,
                   PhoneNumber phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.visitorID = LBMS.totalAccounts() + 1;
        this.checkedOutBooks = new HashMap<>(MAX_BOOKS);
        this.previousCheckedOutBooks = new HashMap<>(MAX_BOOKS);
        this.inLibrary = false;
        this.currentFines = 0.0;
        this.totalFines = 0.0;
        this.payedFines = 0.0;
    }

    /**
     * Getter for the visitors name.
     * @return the first and last name combined
     */
    public String getName() {
        return this.firstName + " " + this.lastName;
    }

    /**
     * Getter for the username of a Visitor.
     * @return the visitors username
     */
    public String getUsername() {
        return this.username;
    }

    /**
     * Getter for the visitor's password.
     * @return the visitor's password
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Getter for the visitors address.
     * @return the visitors address
     */
    public String getAddress() {
        return this.address;
    }

    /**
     * Getter for the visitors phone number.
     * @return the visitors phone number
     */
    public PhoneNumber getPhoneNumber() {
        return this.phoneNumber;
    }

    /**
     * Getter for the visitors ID.
     * @return the visitors ID
     */
    public long getVisitorID() {
        return this.visitorID;
    }

    /**
     * Getter for the number of books the visitor has checked out.
     * @return the number of checked out books
     */
    public int getNumCheckedOut() {
        return this.checkedOutBooks.size();
    }

    /**
     * Getter for the checked out books
     * @return the checked out books
     */
    public HashMap<ISBN, Transaction> getCheckedOutBooks() {
        return this.checkedOutBooks;
    }

    /**
     * Getter for previously checked out books
     * @return previously checked out books
     */
    public HashMap<ISBN, Transaction> getPreviousCheckedOutBooks() {
        return this.previousCheckedOutBooks;
    }

    /**
     * Determines if a visitor can check out a book.
     * @return true if the number of checked out books is less than the max
     */
    public boolean canCheckOut() {
        return getNumCheckedOut() < MAX_BOOKS && !(this.totalFines + this.currentFines > this.payedFines);
    }

    /**
     * Checks out a book for a visitor.
     * @param transaction: the transaction for the checked out book
     */
    public void checkOut(Transaction transaction) {
        if (canCheckOut()) {
            this.checkedOutBooks.put(transaction.getIsbn(), transaction);
        }
    }

    /**
     * Undoes the action of a visitor checking out a book
     * @param transaction: the transaction for the checked out book
     */
    public void undoCheckOut(Transaction transaction) {
        checkedOutBooks.remove(transaction.getIsbn());
    }

    /**
     * Returns a book for a visitor.
     * @param transaction: the transaction created when the book was checked out
     */
    public void returnBook(Transaction transaction) {
        this.totalFines += transaction.getFine();
        this.checkedOutBooks.remove(transaction.getIsbn());
    }

    /**
     * Undoes the return of a book for a visitor.
     * @param transaction: the transaction created when the book was checked out
     */
    public void undoReturnBook(Transaction transaction) {
        totalFines -= transaction.getFine();
        checkedOutBooks.put(transaction.getIsbn(), transaction);
        previousCheckedOutBooks.remove(transaction.getIsbn());
    }

    /**
     * Getter for the status of the visitor.
     * @return true if the visitor is in the library, false if not
     */
    public boolean getInLibrary() {
        return this.inLibrary;
    }

    /**
     * Changes the in library status of a visitor.
     * @param status: a boolean of the status of a visitor
     */
    public void switchInLibrary(boolean status) {
        this.inLibrary = status;
    }

    /**
     * Determines the fines the visitor owes.
     * @return the amount of fines due
     */
    public double getFines() {
        double fines = 0;
        for (ISBN l: this.checkedOutBooks.keySet()) {
            fines += this.checkedOutBooks.get(l).getFine();
        }
        this.currentFines = fines;
        return this.currentFines + this.totalFines - this.payedFines;
    }

    /**
     * Makes a payment to the library.
     * @param amount: the amount of fines to pay
     */
    public void payFines(double amount) {
        this.payedFines += amount;
    }

    /**
     * Getter for payed fines.
     * @return the amount of fines this visitor has payed
     */
    public double getPayedFines() {
        return this.payedFines;
    }

    /**
     * Creates a string for a visitor.
     * @return a string representation of a visitor
     */
    @Override
    public String toString(){
        return "Firstname: " + this.firstName + "\nLastname: " + this.lastName + "\nAddress: " + this.address +
                "\nPhone number: " + this.phoneNumber + "\nVisitorID: " + this.visitorID + "\nUsername: " +
                this.username + "\nPassword: " + this.password + "\nIn-library: " + this.inLibrary + "\nAll fines: " +
                this.getFines() + "\n";
    }

    /**
     * Setter for the username and password when an account is created.
     * @param username: the account's username
     * @param password: the account's password
     */
    public void setCredentials(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
