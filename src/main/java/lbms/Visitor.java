package lbms;

import java.util.HashMap;

/**
 * Created by Chris on 2/23/17.
 */
public class Visitor {
    private String firstName, lastName;
    private String address;     // PLACEHOLDER type address QUESTION: can we use external address and phone number class
    private int phoneNumber;    // PLACEHOLDER type phonenumber
    private int visitorID;
    private HashMap<Integer, Transaction> checkedOutBooks;

    public Visitor(String firstName, String lastName, String address, int phoneNumber, int visitorID) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.visitorID = visitorID;
        this.checkedOutBooks = new HashMap<Integer, Transaction>(5);
    }


    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public int getVisitorID() {
        return visitorID;
    }

    public int getNumCheckedOut() {
        return checkedOutBooks.size();
    }

    public boolean canCheckOut() {
        return checkedOutBooks.size() < 5;
    }

    public void checkOut(Transaction transaction) {
        if (checkedOutBooks.size() < 5) {
            checkedOutBooks.put(transaction.getIsbn(), transaction);
        }
    }

    public void returnBook(Transaction transaction) {
        checkedOutBooks.remove(transaction.getIsbn());
    }

}
