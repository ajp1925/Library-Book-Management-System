package lbms;

import java.util.Calendar;

/**
 * Class for a Visit Object, used in the library book management system.
 */
public class Visit implements java.io.Serializable {

    private int visitorID;
    private Calendar date;
    private int timeOfArrival, timeOfDeparture;     // PLACEHOLDER time type

    /**
     * Constructor for a Visit object.
     * @param visitorID: the ID of the visitor who is at the library
     * @param date: the date they went to the library
     * @param timeOfArrival: the time that they arrived
     */
    public Visit(int visitorID, Calendar date, int timeOfArrival) {
        this.visitorID = visitorID;
        this.date = date;
        this.timeOfArrival = timeOfArrival;
    }

    public void depart() {
        this.timeOfDeparture = 0;        // PLACEHOLDER current time
    }
}
