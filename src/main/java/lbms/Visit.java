package lbms;

import java.util.Calendar;

/**
 * Created by Chris on 2/23/17.
 */
public class Visit {
    private int visitorID;
    private Calendar date;
    private int timeOfArrival, timeOfDeparture;     // PLACEHOLDER time type

    public Visit(int visitorID, Calendar date, int timeOfArrival) {
        this.visitorID = visitorID;
        this.date = date;
        this.timeOfArrival = timeOfArrival;
    }

    public void depart() {
        this.timeOfDeparture = 0;        // PLACEHOLDER current time
    }
}
