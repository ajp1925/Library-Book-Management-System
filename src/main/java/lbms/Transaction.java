package lbms;

import java.util.Calendar;

/**
 * Class for a Transaction object, used in the library book management system.
 */
public class Transaction implements java.io.Serializable {
    private final static double MAX_FINE = 30.00;
    private final static double WEEK_FINE = 2.00;
    private final static double INITIAL_FINE = 10.00;

    private int isbn;
    private int visitorId;
    private Calendar date, dueDate;
    private double fine;

    /**
     * Constructor for a Transaction object.
     * @param isbn: the isbn of the book
     * @param visitorId: the ID of the visitor checking it out
     * @param date: the date the book was checked out
     * @param dueDate: the date the book must be returned by to avoid fines
     */
    public Transaction(int isbn, int visitorId, Calendar date, Calendar dueDate) {
        this.isbn = isbn;
        this.visitorId = visitorId;
        this.date = date;
        this.dueDate = dueDate;
    }

    /**
     * Getter for the ISBN number.
     * @return the isbn of the book checked out
     */
    public int getIsbn() {
        return isbn;
    }

    /**
     * Getter for the visitors ID.
     * @return the visitors ID
     */
    public int getVisitor() {
        return visitorId;
    }

    /**
     * Getter for the fine.
     * @return the fine due on the book
     */
    public double getFine() {
        calculateFine();
        return fine;
    }

    /**
     * Getter for the date.
     * @return the date the book was checked out
     */
    public Calendar getDate() {
        return date;
    }

    /**
     * Getter for the date the book is due.
     * @return the date the book is due
     */
    public Calendar getDueDate() {
        return dueDate;
    }

    private void calculateFine() {
        // TODO
    }
}
