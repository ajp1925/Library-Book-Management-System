package lbms;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.Serializable;

/**
 * Class for a Visit Object, used in the library book management system.
 */
public class Visit implements Serializable {

    private int visitorID;
    private LocalDateTime dateTime;
    private LocalTime timeOfDeparture;

    private final static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("E. MMM d, yyyy");
    private final static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");


    /**
     * Constructor for a Visit object.
     * @param visitorID: the ID of the visitor who is at the library
     */
    public Visit(int visitorID) {
        this.visitorID = visitorID;
        this.dateTime = SystemDateTime.instance.getDateTime();
        this.timeOfDeparture = null;
    }

    public void depart() {
        this.timeOfDeparture = SystemDateTime.instance.getTime();
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getArrivalTime() {
        return dateTime.toLocalTime();
    }

    public LocalTime getDepartureTime() {
        return timeOfDeparture;
    }

    public String printDate() {
        return this.getDate().format(dateFormatter);
    }

    public String printArrivalTime() {
        return this.getArrivalTime().format(timeFormatter);
    }

    public String printDepartureTime() {
        return this.getDepartureTime().format(timeFormatter);
    }
}
