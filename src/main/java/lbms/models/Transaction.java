package lbms.models;

import lbms.models.SystemDateTime;

import java.time.LocalDate;
import java.io.Serializable;
import java.time.format.DateTimeFormatter;

/**
 * Class for a Transaction object, used in the library book management system.
 */
public class Transaction implements Serializable {
    private final static double MAX_FINE = 30.00;
    private final static double WEEK_FINE = 2.00;
    private final static double INITIAL_FINE = 10.00;

    private long isbn;
    private int visitorId;
    private LocalDate date, dueDate;
    private double fine;

    private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E. MMM d, yyyy");

    /**
     * Constructor for a Transaction object.
     * @param isbn: the isbn of the book
     * @param visitorId: the ID of the visitor checking it out
     */
    public Transaction(long isbn, int visitorId) {
        this.isbn = isbn;
        this.visitorId = visitorId;
        this.date = SystemDateTime.getInstance().getDate();
        this.dueDate = date.plusDays(7);
    }

    /**
     * Getter for the ISBN number.
     * @return the isbn of the book checked out
     */
    public long getIsbn() {
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
    public LocalDate getDate() {
        return date;
    }

    /**
     * Getter for the date the book is due.
     * @return the date the book is due
     */
    public LocalDate getDueDate() {
        return dueDate;
    }

    public String printDate() {
        return this.getDate().format(formatter);
    }

    public String printDueDate() {
        return this.getDueDate().format(formatter);
    }

    private void calculateFine() {
        // TODO
    }
}
