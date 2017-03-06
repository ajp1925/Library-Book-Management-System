package lbms.models;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.io.Serializable;
import java.time.temporal.ChronoUnit;

/**
 * Class for a Visit Object, used in the library book management system.
 */
public class Visit implements Serializable {

    private int visitorID;
    private LocalDateTime dateTime;
    private LocalTime timeOfDeparture;
    private Duration duration;

    /**
     * Constructor for a Visit object.
     * @param visitorID: the ID of the visitor who is at the library
     */
    public Visit(int visitorID) {
        this.visitorID = visitorID;
        this.dateTime = SystemDateTime.getInstance().getDateTime();
        this.timeOfDeparture = null;
        //this.duration = null;
        // TODO switch visitor inLibrary to true
    }

    /**
     * Departs the visitor from the library.
     */
    public void depart() {
        this.timeOfDeparture = SystemDateTime.getInstance().getTime();
        //this.duration = Duration.between(dateTime.toLocalTime(), timeOfDeparture).
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
        return this.getDate().format(SystemDateTime.DATE_FORMAT);
    }

    /**
     * Creates a string of the arrival time.
     * @return string of the visit arrival time
     */
    public String printArrivalTime() {
        return this.getArrivalTime().format(SystemDateTime.TIME_FORMAT);
    }

    /**
     * Creates a string of the departure time.
     * @return string of the visit departure time
     */
    public String printDepartureTime() {
        return this.getDepartureTime().format(SystemDateTime.TIME_FORMAT);
    }
}
