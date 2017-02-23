package lbms;

import java.util.Calendar;

/**
 * Created by Chris on 2/23/17.
 */
public class Transaction {
    private final static double MAX_FINE = 30.00;
    private final static double WEEK_FINE = 2.00;
    private final static double INITIAL_FINE = 10.00;

    private Book book;
    private int visitorID;
    private double fine;
    private Calendar date, dueDate;

    public Transaction(Book book, int visitorID, Calendar date, Calendar dueDate) {
        this.book = book;
        this.visitorID = visitorID;
        this.date = date;
        this.dueDate = dueDate;
    }

    public Book getBook() {
        return book;
    }

    public int getVisitorID() {
        return visitorID;
    }

    public double getFine() {
        calculateFine();
        return fine;
    }

    public Calendar getDate() {
        return date;
    }

    public Calendar getDueDate() {
        return dueDate;
    }

    private void calculateFine() {

    }
}
