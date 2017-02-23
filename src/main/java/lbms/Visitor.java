package lbms;

import java.util.ArrayList;

/**
 * Created by Chris on 2/23/17.
 */
public class Visitor {
    private String firstName, lastName;
    private String address;     //PLACEHOLDER
    private int phoneNumber;    //PLACEHOLDER
    private int visitorID;
    private ArrayList<Transaction> checkedOutBooks;

    public Visitor(String firstName, String lastName, int phoneNumber, int visitorID) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.visitorID = visitorID;
        this.checkedOutBooks = new ArrayList<Transaction>();
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

    public void checkOut(Transaction transaction) {

    }

    public void returnBook() {

    }

}
