package lbms.models;

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
        this.dateTime = SystemDateTime.getInstance().getDateTime();
        this.timeOfDeparture = null;
        // TODO switch visitor inLibrary to true
    }

    /**
     * Departs the visitor from the library.
     */
    public void depart() {
        this.timeOfDeparture = SystemDateTime.getInstance().getTime();
        // TODO switch visitor inLibrary to false
    }

    /**
     * Getter for the visit date.
     * @return local date of the visit
     */
    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    /**
     * Getter for the arrival time.
     * @return local time for the arrival time
     */
    public LocalTime getArrivalTime() {
        return dateTime.toLocalTime();
    }

    /**
     * Getter for the departure time.
     * @return local time for the departure time
     */
    public LocalTime getDepartureTime() {
        return timeOfDeparture;
    }

    /**
     * Creates a string of the date.
     * @return string of the visit date
     */
    public String printDate() {
        return this.getDate().format(dateFormatter);
    }

    /**
     * Creates a string of the arrival time.
     * @return string of the visit arrival time
     */
    public String printArrivalTime() {
        return this.getArrivalTime().format(timeFormatter);
    }

    /**
     * Creates a string of the departure time.
     * @return string of the visit departure time
     */
    public String printDepartureTime() {
        return this.getDepartureTime().format(timeFormatter);
    }
}
