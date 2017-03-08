package lbms.models;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;

/**
 * Class for a Transaction object, used in the library book management system.
 */
public class Transaction implements Serializable {
    private final static double MAX_FINE = 30.00;
    private final static double WEEK_FINE = 2.00;
    private final static double INITIAL_FINE = 10.00;

    private long isbn;
    private long visitorId;
    private LocalDate date, dueDate;

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
    public double getFine() {
        int days = Period.between(dueDate, SystemDateTime.getInstance().getDate()).getDays();
        double fine = 0.0D;
        for (int i = 0; i < days; i++) {
            if (i == 0) fine += 10.00D;
            else fine += 2.00D;
            i--;
        }
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
        return this.getDate().format(SystemDateTime.DATE_FORMAT);
    }

    public String printDueDate() {
        return this.getDueDate().format(SystemDateTime.DATE_FORMAT);
    }
}
