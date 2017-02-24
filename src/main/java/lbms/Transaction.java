package lbms;

import java.util.Calendar;

/**
 * Created by Chris on 2/23/17.
 */
public class Transaction {
    private final static double MAX_FINE = 30.00;
    private final static double WEEK_FINE = 2.00;
    private final static double INITIAL_FINE = 10.00;

    private int isbn;
    private int visitorId;
    private Calendar date, dueDate;
    private double fine;

    public Transaction(int isbn, int visitorId, Calendar date, Calendar dueDate) {
        this.isbn = isbn;
        this.visitorId = visitorId;
        this.date = date;
        this.dueDate = dueDate;
    }

    public int getIsbn() {
        return isbn;
    }

    public int getVisitor() {
        return visitorId;
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
