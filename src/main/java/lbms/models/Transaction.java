package lbms.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;

/**
 * Class for a Transaction object, used in the library book management system.
 * @author Team B
 */
public class Transaction implements Serializable {
    // TODO remove unused methods

    /* Constants for overdue fines. */
    private final static double MAX_FINE = 30.00;
    private final static double WEEK_FINE = 2.00;
    private final static double INITIAL_FINE = 10.00;

    private long isbn;
    private long visitorId;
    private double finePayed = 0;
    private LocalDate date, dueDate, closeDate;

    /**
     * Constructor for a Transaction object.
     * @param isbn: the isbn of the book
     * @param visitorId: the ID of the visitor checking it out
     */
    public Transaction(long isbn, long visitorId) {
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
    public long getVisitor() {
        return visitorId;
    }

    /**
     * Getter for the fine.
     * @return the fine due on the book
     */
    double getFine() {
        int days = Period.between(dueDate, SystemDateTime.getInstance().getDate()).getDays();
        double fine = 0.0D;
        for(int i = 0; i < days; i++) {
            if(i == 0) {
                fine += 10.00D;
            }
            else {
                fine += 2.00D;
            }
        }
        return fine;
    }

    /**
     * Getter for the fine money payed
     * @return the amount of money payed for Transaction fine
     */
    public double getFinePayed() {
        return finePayed;
    }

    /**
     * Closes the transaction by setting the fine payed.
     * Note: a transaction without an associated fine does not need closing
     * @param amount the amount of fine payed
     */
    void payTransactionFine(double amount) {
        if( finePayed == 0 && amount == getFine() ) {
            finePayed += amount;
            closeTransaction();
        }
    }

    /**
     * Marks that the fine has been paid for this transaction
     */
    private void closeTransaction() {
        closeDate = SystemDateTime.getInstance().getDate();
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

    /**
     * Getter for the close date of the transaction (when the fine was paid)
     * @return the date the transaction was closed
     */
    public LocalDate getCloseDate() {
        return closeDate;
    }

    /**
     * Creates a string of the date.
     * @return the date formatted properly
     */
    public String printDate() {
        return this.getDate().format(SystemDateTime.DATE_FORMAT);
    }

    /**
     * Creates a string of the due date.
     * @return the date formatted properly
     */
    public String printDueDate() {
        return this.getDueDate().format(SystemDateTime.DATE_FORMAT);
    }
}
