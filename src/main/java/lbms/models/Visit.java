package lbms.models;

import java.time.*;
import java.io.Serializable;

/**
 * Class for a Visit Object, used in the library book management system.
 * @author Team B
 */
public class Visit implements Serializable {
    // TODO remove unused methods

    private Visitor visitor;
    private LocalDateTime dateTime;
    private LocalTime timeOfDeparture;
    private Duration duration;

    /**
     * Constructor for a Visit object.
     * @param visitor: the ID of the visitor who is at the library
     */
    public Visit(Visitor visitor) {
        this.visitor = visitor;
        this.dateTime = SystemDateTime.getInstance().getDateTime();
        this.timeOfDeparture = null;
        this.duration = null;
        this.visitor.switchInLibrary(true);
    }

    /**
     * Departs the visitor from the library.
     */
    public void depart() {
        this.timeOfDeparture = SystemDateTime.getInstance().getTime();
        this.duration = Duration.between(dateTime.toLocalTime(), timeOfDeparture);
        this.visitor.switchInLibrary(false);
    }

    /**
     * Getter for the visitor
     * @return the visitor
     */
    public Visitor getVisitor() {
        return this.visitor;
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

    /**
     * Getter for the visit duration.
     * @return the duration of the visit
     */
    public Duration getDuration() {
        return this.duration;
    }
}
